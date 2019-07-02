package kotlinstuff

import io.vertx.core.Vertx


object App {
    @JvmStatic
    fun main(args: Array<String>) {


        val vertx = Vertx.vertx()

        vertx.deployVerticle(MainVerticle())





    }
}
