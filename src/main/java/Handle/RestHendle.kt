package Handle


import Response.*
import io.vertx.core.Handler
import io.vertx.core.http.HttpServerResponse
import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import java.lang.NumberFormatException
import db.connector
import io.netty.handler.codec.http.HttpResponseStatus
import java.lang.Integer.parseInt




//Handle Service sensor ID
val getUsersHandler = Handler<RoutingContext> { ctx ->
  try {
    //http parameters
    val value = parseInt(ctx.request().getParam("value"))
    //call User Class
    val get = getNodeID()
    //Pass in parameters
    val result = get.restCallUsersId(value)
    //Return json response
    ctx.response().returnJson(result)
  } catch (e: NumberFormatException) {
    ctx.response().setStatusCode(500).end(json { obj("error" to e.message).encode() })
  }
}


val getAllSensorsHandler = Handler<RoutingContext> { ctx ->
  try {
    //http parameters
    //call User Class
    val get = getAllSensors()
    //Pass in parameters
    val result = get.restCallAllSensors()
//    println(result)
    //Return json response
    ctx.response().returnJson(result)
  } catch (e: NumberFormatException) {
    ctx.response().setStatusCode(500).end(json { obj("error" to e.message).encode() })
  }
}


val handleAddSensor = Handler<RoutingContext> { rc ->
  val jsonBody = rc.bodyAsJson
  val nodeId = jsonBody.getString("nodeId")
  val sensorKey = jsonBody.getString("sensorKey")
  val sensorType = jsonBody.getString("sensorType")

  if (nodeId != null && sensorKey != null  && sensorType != null) {
    val testUser = Sensor(1, nodeId, sensorKey, sensorType)

    val add = addSensor(connector)
    val resultFromDB = add.addNewSensor(testUser)

    val returnReponse :String
    if (resultFromDB == 1) {
      returnReponse = "Sensor Added"
    } else{
      returnReponse = "Sensor Already Exists"
    }

    rc.response()
            .setStatusCode(HttpResponseStatus.CREATED.code())
            .putHeader("Content-Type","application/json")
            .end("result: $returnReponse")
  } else {
    rc.response()
            .setStatusCode(400)
            .end(json {obj (
                    "error" to "Wrong parameters specified"
            )}.toString())
  }
}



val handleUpdateSensor = Handler<RoutingContext> { rc ->
  val jsonBody = rc.bodyAsJson
  val ID = jsonBody.getInteger("ID")
  val nodeId = jsonBody.getString("nodeId")
  val sensorKey = jsonBody.getString("sensorKey")
  val sensorType = jsonBody.getString("sensorType")

  if (ID != null  &&  nodeId != null && sensorKey != null  && sensorType != null) {
    val testUser = Sensor(ID, nodeId, sensorKey, sensorType)

    val add = updateSensor(connector)
    val resultFromDB = add.updateSensorFunc(testUser)

    val returnReponse :String
    if (resultFromDB == 1) {
      returnReponse = "Sensor Updated"
    } else{
      returnReponse = "Sensor not updated"
    }

    rc.response()
            .setStatusCode(HttpResponseStatus.CREATED.code())
            .putHeader("Content-Type","application/json")
            .end("result: $returnReponse")
  } else {
    rc.response()
            .setStatusCode(400)
            .end(json {obj (
                    "error" to "Wrong parameters specified"
            )}.toString())
  }
}


// Func to return json
fun HttpServerResponse.returnJson(obj: Any) {
  this.putHeader("Content-Type", "application/json; charset=utf-8").end(Json.encodePrettily(obj))
}

