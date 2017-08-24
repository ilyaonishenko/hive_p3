package com.chariotsolutions

import akka.stream.ActorMaterializer
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

class AppSpec extends FlatSpec with Matchers with ScalaFutures with BeforeAndAfterAll {

  implicit val testSystem = akka.actor.ActorSystem("test-system")
  implicit val fm = ActorMaterializer()
  val server = new SampleApp {}

//  override def afterAll = testSystem.shutdown()
//
//  def sendRequest(req: HttpRequest) = Source.single(req).via(
//    Http().outgoingConnection(host = "localhost", port = 8080)
//    ).runWith(Sink.head)
//
//  "The app" should "return index.html on a GET to /" in {
//    whenReady(sendRequest(HttpRequest())) { response =>
//      whenReady(Unmarshal(response.entity).to[String]) { str =>
//        println(str)
//        str should include("Hello World!")
//      }
//    }
//  }
//  "The app" should "return 404 on a GET to /foo" in {
//    whenReady(sendRequest(HttpRequest(uri = "/foo"))) { response =>
//      response.status shouldBe StatusCodes.NotFound
//    }
//  }
}
