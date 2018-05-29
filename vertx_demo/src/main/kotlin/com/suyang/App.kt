package com.suyang

import io.vertx.core.Vertx

fun main(args: Array<String>) {

    Vertx.vertx().createHttpServer().requestHandler { response ->
        response.response()
                .putHeader("content-type", "text/plain")
                .end("Hello from Vert.x!")
    }.listen(8081)

    println("HTTP server started on port 8081")
}