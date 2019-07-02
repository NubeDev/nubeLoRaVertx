package Response

import db.SqlConnector

class updateSensor (private val connector: SqlConnector) {

    @Throws(Exception::class)
    fun updateSensorFunc(sensorToAdd: Sensor): Int {

        val sql = String.format("UPDATE SENSOR1 SET nodeId = ? , "
                + "sensorKey = ?, "
                + "sensorType = ? "
                + "WHERE ID = ?")

        try {
            val conn = connector.openConnection()
            val stmt = conn!!.prepareStatement(sql)
            stmt.setString(1, sensorToAdd.nodeId)
            stmt.setString(2, sensorToAdd.sensorKey)
            stmt.setString(3, sensorToAdd.sensorType)
            stmt.setInt(4, sensorToAdd.ID)

            val result = stmt.executeUpdate()

            connector.closeConnection()

            return result

        } catch (e: Exception) {
            println("ERROR ADDING SENSOR"+e.message)
        }
        return 0


    }
}


