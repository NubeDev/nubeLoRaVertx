package kotlinstuff

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler


import Handle.getAllSensorsHandler
import Handle.getUsersHandler
import Handle.handleAddSensor
import Handle.handleUpdateSensor

class MainVerticle : AbstractVerticle() {

        override fun start(startFuture: Future<Void>) {

            val port = 8080 //Port Server
            println("Server running on port:${port}")

            //Init Route Path
            val router = Router.router(vertx)
            router.route().handler(BodyHandler.create())

            router.get("/").handler({ req -> req.response().end("Welcome to My First RestAPI using Kotlin and Vertx!") })
            router.get("/api/sql/:value").handler(getUsersHandler)
            router.get("/api/sql").handler(getAllSensorsHandler)
            router.post("/api/sql/add").handler(handleAddSensor)
            router.post("/api/sql/update").handler(handleUpdateSensor)//update

            vertx.createHttpServer().requestHandler(router).listen(port)



        }

}



