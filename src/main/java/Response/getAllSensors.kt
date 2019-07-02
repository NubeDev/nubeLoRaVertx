package Response

import Response.Sensor



import db.SqlConnector
import db.connector
import java.sql.SQLException
import java.util.ArrayList


class getAllSensors {

    //App rest call
    fun restCallAllSensors (): ArrayList<Sensor> {
        val manager = sensorQueryAll(connector)
        val seletUsersId = manager.selectAll()
        println(seletUsersId)
        return seletUsersId
    }

    // This class will act as the intermediary between database and our objects
    class sensorQueryAll (private val connector: SqlConnector) {

        private val idCol: String = "ID"
        private val nodeIdCol: String = "nodeId"
        private val sensorKeyCol: String = "sensorKey"
        private val sensorTypeCol: String = "sensorType"

        // Returns an arraylist of User objects from the database
        // Ideally check the count of returned arrayList
        @Throws(Exception::class)
        fun selectAll(): ArrayList<Sensor> {

            val list = ArrayList<Sensor>()

            val sql = "Select * from SENSOR1"
            try {
                val conn = connector.openConnection()
                val stmt = conn!!.createStatement()
                val rs = stmt.executeQuery(sql)
                // loop through the result set
                while (rs.next()) {
                    val tempData= Sensor(rs.getInt(idCol), rs.getString(nodeIdCol), rs.getString(sensorKeyCol), rs.getString(sensorTypeCol))
                    list.add(tempData)
                }

                connector.closeConnection()

            } catch (e: SQLException) {
                println(e.message)
            }

            return list
        }
    }

}