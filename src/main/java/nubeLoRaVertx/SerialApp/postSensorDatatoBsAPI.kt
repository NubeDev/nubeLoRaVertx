package kotlinstuff.nubeLoRaVertx.SerialApp

import nubeLoRaVertx.SerialApp.div
import nubeLoRaVertx.SerialApp.subOne
import nubeLoRaVertx.SerialApp.subTwo
import org.json.JSONArray
import org.json.JSONObject
import java.lang.reflect.Array.setInt



fun main(args: Array<String>) {

/*
   payload: "AAB296C4E5094228BA0000EC0000009A2D64"
   checksumFlag: false
   messageVersion: 178
   nodeId: "AAB296C4"
   temp: 25.33
   pressure: 1030.6
   humidity: 58
   movement: 1
   light: 0
   voltage: 4.72
   checksum: "0x9A"
   rssi: -45
   snr: 10


 */


   val serialDataExample = "AAB296C4E5094228BA0000EC0000009A2D64"


   //test string "AAB296C4E5094228BA0000EC0000009A2D64"
   val nodeId = serialDataExample.substring(0, 8) //AAB296C4

   val tempRaw = subTwo(serialDataExample, 10,12, 8, 10) // 25.33
   val temp = div(tempRaw, 100)

   val pressureRaw = subTwo(serialDataExample, 14, 16, 12, 14) //1030.6
   val pressure = div(pressureRaw, 10)

   val humidityRaw = subOne(serialDataExample, 16, 18) //58
   val humidity = humidityRaw.toLong(16).toFloat() % 128

   val movementRaw = subOne(serialDataExample, 16, 18) //1 (true)
   val movementBool = movementRaw.toLong(16).toFloat() > 127
   val movement = if (movementBool) 1 else 0 //convert from bool to int

   val lux = subTwo(serialDataExample, 20, 22, 18, 20) //0

   val voltageRaw = subOne(serialDataExample, 22, 24) //4.72
   val voltage = voltageRaw.toLong(16).toFloat() / 50

   val checksum = subOne(serialDataExample, 30, 32) //0x9A

   val rssiRaw = subOne(serialDataExample, 32, 34) //-45
   val rssi = rssiRaw.toLong(16).toFloat() * -1

   val snrRaw = subOne(serialDataExample, 34, 36) //10
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


   val post = sensorPayload.forEach({a -> println(a)})



}

