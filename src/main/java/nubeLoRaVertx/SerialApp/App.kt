package nubeLoRaVertx.SerialApp

import io.vertx.core.Vertx
import java.io.File
import io.vertx.core.VertxOptions
import org.json.JSONObject


fun getPort(): String {

    val os = System.getProperty("os.name")

    if (os == "Linux") {
        File("/dev").walkTopDown().forEach {

            val filename = it.toString()
            if (
                    filename == "/dev/ttyS0" ||
                    filename == "/dev/ttyAMA0" ||
                    filename == "/dev/ttyUSB0" ||
                    filename == "/dev/ttyUSB1"
            ) {
                return filename
            }
        }
    }

    return ""
}


fun div(x: String, y: Int): Float {
    return  x.toLong(16).toFloat()/y
}

fun subOne(x: String, a: Int, b: Int): String {
    return x.substring(a, b)
}

fun subTwo(x: String, a: Int, b: Int, a2: Int, b2: Int): String {
    return x.substring(a, b) + x.substring(a2, b2)
}


//nodeID

//println(nodeId) //AAB296C4


////temp
//val tempRaw = subTwo(msg, 10,12, 8, 10)
//val temp = div(tempRaw, 100)
//println(temp) //19.12
//
//
////pressure
//val pressureRaw = subTwo(msg, 14, 16, 12, 14)
//val pressure = div(pressureRaw, 10)
//println(pressure) //1028.7




fun main(args: Array<String>) {

    val vertx = Vertx.vertx(VertxOptions().setWorkerPoolSize(40))
    val getPort = getPort()

    println(getPort)
    val port = "/dev/ttyACM1"
    val baud_rate = 9600


    val serialPort = SerialComm2(port, baud_rate)


    fun out () :String {
        try {
            while (true) {


                val serialData = serialPort.readString()
                if (serialData.length > 10) {

                    val nodeId = serialData.substring(0, 8)
                    val tempRaw = subTwo(serialData, 10,12, 8, 10)
                    val temp = div(tempRaw, 100)
                    val pressureRaw = subTwo(serialData, 14, 16, 12, 14)
                    val pressure = div(pressureRaw, 10)


                    val sensor= JSONObject()
                    sensor.put("nodeId",nodeId)
                    sensor.put("temp",temp)
                    sensor.put("pressure",pressure)
                    println(sensor)

                }

                Thread.sleep(1)
            }

        } catch (e: Exception) {
            throw e
        }
    }


    out()


}