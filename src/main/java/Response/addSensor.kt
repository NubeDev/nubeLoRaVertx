package Response


import db.SqlConnector



class addSensor(private val connector: SqlConnector) {


    @Throws(Exception::class)
    fun addNewSensor(sensorToAdd: Sensor): Int {

        val sql = String.format("Insert into SENSOR1(nodeId, sensorKey, sensorType) values (?,?,?)")
        try {
            val conn = connector.openConnection()

            val stmt = conn!!.prepareStatement(sql)

            stmt.setString(1, sensorToAdd.nodeId)
            stmt.setString(2, sensorToAdd.sensorKey)
            stmt.setString(3, sensorToAdd.sensorType)

            val result = stmt.executeUpdate()

            connector.closeConnection()

            return result

        } catch (e: Exception) {
            println("ERROR ADDING SENSOR"+e.message)
        }

        // no record added
        return 0


    }
}
//class Sensor (var ID: Int, nodeId: String, sensorKey: String , sensorType: String)


