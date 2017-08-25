package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import org.json4s.jackson.Serialization.write
import org.json4s.{DefaultFormats, Formats}
import org.json4s.jackson.JsonMethods
import SerializationHelper._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{FormData, HttpEntity, HttpRequest}
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.model.ContentTypes._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

trait SampleApp extends JsonMethods {
  implicit val system = ActorSystem("akka-http-sample")
  val command =
    "hadoop jar /yarnTask-assembly-1.0.jar com.example.ApplicationMaster /tasks/task7/jars/yarnTask-assembly-1.0.jar 1"
  val baseUrl = "http://127.0.0.1:8088/ws/v1/cluster/apps/"
  implicit val jsonFormats: Formats = DefaultFormats + rename
  sys.addShutdownHook({ system.shutdown() })

  implicit val materializer = ActorMaterializer()

  val route =
    path("") {
      getFromResource("web/index.html")
    } ~
      path("job") {
        get {
          complete(startJob())
        }
      } ~
      path("manufacturers") {
        get {
          println("manufacturer")
          getFromResource("web/manufacturers.html")
        }
      } ~
      path("manufacturers" / Segment) { manufacturer =>
        get {
          println("manufacturer&segment")
          complete(requsetToHadoop(manufacturer))
        }
      } ~
      path("jobiscomplete") {
        get{
          println("easyget")
          complete("easyget")
        } ~
          (post & entity(as[String])){ smth =>
            println(smth)
            complete(smth)
          }
      } ~
      path("jobiscomplete" / Segment) { jobId =>
        (post & entity(as[String])) { smth =>
          println(smth)
          complete("Heq")
        } ~
          get {
            println("getsmth from " + jobId)
            getFromResource("web/index.html")
          }

      }

  val serverBinding =
    akka.http.scaladsl.Http(system).bindAndHandle(route, "localhost", 8081)

  def startJob(): Future[String] = {
    import system.dispatcher

    println("in startJob place")
    val urlToReserveNewApp = baseUrl + "new-application"
    val formData = FormData().toEntity
    val req = HttpRequest(POST, urlToReserveNewApp, entity = formData)

    val resp = Await.result(Http().singleRequest(req), 1.second)
    val stringRes = Await.result(Unmarshal(resp.entity).to[String], 1.second)
    val appId = stringRes.split("\"", 5)(3)
    println(appId)
    val appRequest = AppRequest(appId,
                                "appFromREST",
                                "default",
                                3,
                                Map("commands" -> Map("command" -> command)),
                                false,
                                2,
                                Map("memory" -> "1024", "vCores" -> "1"),
                                "YARN",
                                false)

    val resJson = write(appRequest)
    val fd = HttpEntity(`application/json`, resJson.getBytes)
    val req2 = HttpRequest(POST, baseUrl, entity = fd)
    val resp2 = Await.result(Http().singleRequest(req2), 1.second)

    Unmarshal(resp2.entity).to[String]
  }

  def requsetToHadoop(manufacturer: String): Future[String] = {
    println("hadoop")
    val query =
      s"select * from plane_data where manufacturer = \'${manufacturer.toUpperCase}\';"
    println(query)
    val url = "http://localhost:50111/templeton/v1/hive?user.name=raj_ops"
    val json = write(CustomQuery(query, "/tasks/task7/webhcat/pokes.pokas"))
    println(json)
    val formData = FormData(
      Map("execute" -> query,
          "statusdir" -> "/tasks/task7/webhcat/pokes.pokas",
          "callback" -> "http://localhost:8081/jobiscomplete"))
    println(formData.toString)
    val req = HttpRequest(POST, url, entity = formData.toEntity)
    val resp = Await.result(Http().singleRequest(req), 10.minutes)
    Unmarshal(resp.entity).to[String]
  }
}

case class CustomQuery(execute: String, statusdir: String)

case class ServerResponse(appId: String, smt: String)

object Main extends App with SampleApp {}
