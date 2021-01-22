package com.joveo.client

import akka.actor.ActorSystem
import akka.http.javadsl.server.directives.RouteAdapter
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.joveo.client.api.ClientApi

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Main extends App {
  implicit val system: ActorSystem = ActorSystem("akka-http-rest-server")
  private implicit val dispatcher: ExecutionContextExecutor = system.dispatcher
  private implicit val materialize: ActorMaterializer = ActorMaterializer()
  val routes = new ClientApi

  val host = "127.0.0.1"
  val port = 8080
  val serverFuture = Http().bindAndHandle(routes.routes, host, port)
  println(s"Server is online at port = $port, PRESS ENTER TO EXIT")
  StdIn.readLine()
  serverFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
}
