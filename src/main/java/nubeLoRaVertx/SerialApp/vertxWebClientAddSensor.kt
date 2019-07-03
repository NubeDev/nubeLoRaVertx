package nubeLoRaVertx.SerialApp

import io.vertx.core.Vertx
import io.vertx.ext.web.client.WebClient
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj


fun main(args: Array<String>) {


    val vertx = Vertx.vertx()
    val client = WebClient.create(vertx)

    val newSensor = json {
        obj(
                "nodeId" to "AAB296C4",
                "sensorKey" to "AAAA1111",
                "sensorType" to "THML"
        )
    }

    client.post(8080, "127.0.0.1", "/api/sql/add").sendJson(newSensor) { ar ->
        if (ar.succeeded()) {
            val response = ar.result()
            println("Got HTTP response with status ${response.body()}")
        } else {
            ar.cause().printStackTrace()
        }
    }

}
