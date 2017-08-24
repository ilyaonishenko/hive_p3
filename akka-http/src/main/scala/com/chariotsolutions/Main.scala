package com.chariotsolutions

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

trait SampleApp {
  implicit val system = ActorSystem("akka-http-sample")
  sys.addShutdownHook({ system.shutdown() })

  implicit val materializer = ActorMaterializer()

  import system.dispatcher

  val route =
    path("") {
      getFromResource("web/index.html")
    }

  val serverBinding = Http(system).bindAndHandle(route, "localhost", 8080)
}

object Main extends App with SampleApp {}
