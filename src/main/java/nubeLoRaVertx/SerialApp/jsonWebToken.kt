//package nubeLoRaVertx.SerialApp
//
//import io.vertx.core.Vertx
//import io.vertx.core.buffer.Buffer
//import io.vertx.ext.web.client.WebClient
//import io.vertx.kotlin.core.json.json
//import io.vertx.kotlin.core.json.obj
//import io.vertx.ext.web.client.predicate.ResponsePredicateResult
//
//
//
//fun main(args: Array<String>): {
//
//
//    val vertx = Vertx.vertx()
//    val client = WebClient.create(vertx)
//
//    val url = "localhost"
//    val port = 4000
//
//
//    val auth = json {
//        obj(
//                "username" to "admin",
//                "password" to "api@nube"
//        )
//    }
//
//    client.post(port, url, "/edge-api/user/login")
//            .sendJson(auth) { ar ->
//                if (ar.succeeded()) {
//                    val response = ar.result()
//                    println("Got HTTP response with status ${response.body()}")
//                } else {
//                    ar.cause().printStackTrace()
//                }
//            }
//
////    val token= "JWT xxx";
////
////    fun get () {
////
////        client.get(port, url, "/edge-api/points")
////                .putHeader("Accept", "application/json")
////                .putHeader("Authorization", token)
////                .send { ar ->
////                    if (ar.succeeded()) {
////                        val response = ar.result()
//////                        println("Got HTTP response with status ${response.body()}")
////
////                    } else {
////                        ar.cause().printStackTrace()
////                    }
////                }
////
////    }
//
////    val methodsPredicate = { resp ->
////        val methods = resp.getHeader("Access-Control-Allow-Methods")
////        if (methods != null) {
////            if (methods!!.contains("POST")) {
////                return ResponsePredicateResult.success()
////            }
////        }
////        ResponsePredicateResult.failure("Does not work")
////    }
//
//}
//
//
//}