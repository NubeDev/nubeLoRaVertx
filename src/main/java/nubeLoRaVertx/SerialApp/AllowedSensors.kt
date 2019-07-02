package nubeLoRaVertx.SerialApp


import Response.getAllSensors.sensorQueryAll
import db.connector

import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.core.json.obj
import org.json.JSONArray
import org.json.JSONObject

fun main(args: Array<String>) {


    /*


   Steps of adding a new sensor See the example vertxWebClientAddSensor.kt
   1) A user would add a new sensor from the bs-api-dashboard. this would be stored in the SQLLite DB


   Steps of sending data to bs-api
   1) Stream in serail Data from an arduino
   2) deycyped the data and see if the nodeId is save in the DB
   3) If data is save in the DB then post the sensor data to bs-api


   */


    val kotlinHash = kotlinHash()
    val app = kotlinHash()
    val sensor = app.run("AAB296C4E5094228BA0000EC0000009A2D64", "sensor1234512345")




    // call DB for sensor that are added to the DB
    val manager = sensorQueryAll(connector)
    val getSensors = manager.selectAll()


    // convert to jsonObj
    val jsArray = JSONArray(getSensors)



    val pp = jsArray.map {
        it as JSONObject
    }.filter {
        it.getString("sensorType") == "THML"
    }.map {
        it.getString("nodeId")
    }



    // vaild nodeId
    val msgNodeID1 = "111"
    val msgNodeID2 = "qqqq"

    fun vaildNodeId(sensorList :List<String>, msgNodeID :String ): String {
        if (sensorList.contains(msgNodeID)) {
            return msgNodeID
        }else
            return "not a valid sensor"
    }



    val aaa = vaildNodeId(pp, msgNodeID2)
    println(aaa)


}

