package kotlinstuff.nubeLoRaVertx.SerialApp

import java.util.LinkedList


fun main(args: Array<String>) {




    val sensorList = listOf<String>("111", "2")
    val msgNodeID1 = "111"
    val msgNodeID2 = "222"

    fun vaildNodeId(sensorList :List<String>, msgNodeID :String ): String {
        if (sensorList.contains(msgNodeID)) {
            return msgNodeID
        }else
            return "not a valid sensor"
    }


    val aaa = vaildNodeId(sensorList, msgNodeID2)
    println(aaa)



    fun readString2(a :Int, b: Int): Int {

        val out = a+b

        try {

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return out
    }

    println(readString2(22,22))






}




