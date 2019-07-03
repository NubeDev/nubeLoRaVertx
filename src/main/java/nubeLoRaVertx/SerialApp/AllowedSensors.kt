package nubeLoRaVertx.SerialApp


import Response.getAllSensors.sensorQueryAll
import db.connector


import org.json.JSONArray
import org.json.JSONObject


import io.vertx.core.Vertx
import io.vertx.core.json.JsonArray
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj


fun main(args: Array<String>) {


    /*


   Steps of adding a new sensor See the example vertxWebClientAddSensor.kt
   1) A user would add a new sensor from the bs-api-dashboard. this would be stored in the SQLLite DB


   Steps of sending data to bs-api
   1) Stream in serail Data from an arduino
   2) deycyped the data and see if the nodeId is save in the DB
   3) If data is save in the DB then post the sensor data to bs-api


   */

    // this data would come from the serial and an example of decrypting
//    val kotlinHash = kotlinHash()
//    val app = kotlinHash()
//    val sensor1 = app.run("AAB296C4E5094228BA0000EC0000009A2D64", "sensor1234512345")


    //Example Serial Data
    val serialDataExample = "AAB296C4E5094228BA0000EC0000009A2D64"
    val serialDataExample2 = "ABB296C4E5094228BA0000EC0000009A2D64"
//    val nodeId = serialDataExample.substring(0, 8)


    val dataIn = serialDataExample
    val nodeId = dataIn.substring(0, 8) //AAB296C4



    // Call DB for sensor that are added to the DB
    val manager = sensorQueryAll(connector)
    val getSensors = manager.selectAll()
    // convert to jsonObj
    val jsArray = JSONArray(getSensors)


    // filter out nodeIds added to the DB
    val filterNodeIds = jsArray.map {
        it as JSONObject
    }.filter {
        it.getString("sensorType") == "THML"
    }.map {
        it.getString("nodeId")
    }



    //check if nodeId is in the DB
    fun vaildNodeId(sensorList :List<String>, msgNodeID :String, rawMsg :String ): JSONArray {
        if (sensorList.contains(msgNodeID)) {

            val tempRaw = subTwo(rawMsg, 10,12, 8, 10) // 25.33
            val temp = div(tempRaw, 100)

            val pressureRaw = subTwo(rawMsg, 14, 16, 12, 14) //1030.6
            val pressure = div(pressureRaw, 10)

            val humidityRaw = subOne(rawMsg, 16, 18) //58
            val humidity = humidityRaw.toLong(16).toFloat() % 128

            val movementRaw = subOne(rawMsg, 16, 18) //1 (true)
            val movementBool = movementRaw.toLong(16).toFloat() > 127
            val movement = if (movementBool) 1 else 0 //convert from bool to int

            val lux = subTwo(rawMsg, 20, 22, 18, 20) //0

            val voltageRaw = subOne(rawMsg, 22, 24) //4.72
            val voltage = voltageRaw.toLong(16).toFloat() / 50

            val checksum = subOne(rawMsg, 30, 32) //0x9A

            val rssiRaw = subOne(rawMsg, 32, 34) //-45
            val rssi = rssiRaw.toLong(16).toFloat() * -1

            val snrRaw = subOne(rawMsg, 34, 36) //10
            val snr = snrRaw.toLong(16).toFloat() / 10


            val tempPayload = mutableMapOf("id" to "${nodeId}_TEMP", "value" to temp, "priority" to 16)
            val humidityPayload = mutableMapOf("id" to "${nodeId}_HUMIDITY", "value" to humidity, "priority" to 16)
            val movementPayload  = mutableMapOf("id" to "${nodeId}_MOVEMENT", "value" to movement, "priority" to 16)
            val luxPayload  = mutableMapOf("id" to "${nodeId}_LUX", "value" to lux, "priority" to 16)
            val voltagePayload  = mutableMapOf("id" to "${nodeId}_VOLTAGE", "value" to voltage, "priority" to 16)
            val rssiPayload  = mutableMapOf("id" to "${nodeId}_RSSI", "value" to rssi, "priority" to 16)


            val sensorPayload = JSONArray()
            sensorPayload.put(tempPayload)
            sensorPayload.put(humidityPayload)
            sensorPayload.put(movementPayload)
            sensorPayload.put(luxPayload)
            sensorPayload.put(voltagePayload)
            sensorPayload.put(rssiPayload)

            val post = sensorPayload
            return post

//
        }else  {
            val sensorPayload = JSONArray()
            sensorPayload.put("not a vaild sensor")
            return sensorPayload

        }

    }





    // send data to bs-api via a vertx web-cleint http post
    val postBsAPI = vaildNodeId(filterNodeIds, nodeId, dataIn)
    postBsAPI.forEach({a -> println(a)})












    //pos data to bs-api
//    val vertx = Vertx.vertx()
//    val client = WebClient.create(vertx)
//
//    client.post(4000, "127.0.0.1", "/points").sendJson(post) { ar ->
//        if (ar.succeeded()) {
//            val response = ar.result()
//            println("Got HTTP response with status ${response.body()}")
//        } else {
//            ar.cause().printStackTrace()
//        }
//    }


}

