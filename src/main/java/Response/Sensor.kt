package Response

class Sensor {

    var ID: Int = 0
    var nodeId: String? = null
        private set
    var sensorKey: String? = null
        private set
    var sensorType: String? = null
        private set

    constructor(ID: Int?, nodeId: String, sensorKey: String, sensorType: String) {
        if (ID != null) {
            this.ID = ID
        }
        this.nodeId = nodeId
        this.sensorKey = sensorKey
        this.sensorType = sensorType
    }


}

