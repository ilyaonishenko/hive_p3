package org.scalatra.example

import java.io.File
import org.json4s._
import org.json4s.jackson.Serialization.write
import org.scalatra.example.model.AppRequest
import org.scalatra.{FutureSupport, ScalatraServlet}
import org.scalatra.json.JacksonJsonSupport

import scala.concurrent.ExecutionContext
import scalaj.http._

import org.scalatra.example.SerializationHelper._

class UserController extends ScalatraServlet with JacksonJsonSupport with FutureSupport {

  protected implicit def executor: ExecutionContext = ExecutionContext.Implicits.global
  implicit val jsonFormats: Formats = DefaultFormats + rename
  val command = "/usr/local/hadoop/hadoop-2.8.1/bin/hadoop jar /home/ilia/IdeaProjects/hadoop_p2/yarn-task/target/scala-2.12/hadoop_p2-assembly-1.0.jar com.example.ApplicationMaster /user/ilia/jars/hadoop_p2-assembly-1.0.jar 1"
  val baseUrl = "http://127.0.0.1:8088/ws/v1/cluster/apps/"

  before() {
    contentType = formats("json")
  }

  get("/") {
    contentType = "text/html"
    new File(servletContext.getResource("/WEB-INF/views/home.html").getFile)
  }

  get("/startjob"){
    val urlToReserveNewApp = baseUrl+"new-application"
    val jValue: HttpResponse[JValue] = Http(urlToReserveNewApp).postData("None".getBytes).header("content-type", "application/json").execute{
      inputStream =>
        parse(inputStream)
    }
    val smt = jValue.body.extract[Map[String, Any]]
    val appId = smt("application-id").toString
    val appRequest = AppRequest(appId, "appFromREST", "default", 3, Map("commands" -> Map("command" -> command)),
      false, 2, Map("memory" -> "1024", "vCores" -> "1"), "YARN", false)
    val resJson = write(appRequest)
    val answer = Http(baseUrl).postData(resJson.getBytes).header("content-type", "application/json").asString
    answer
  }

}
