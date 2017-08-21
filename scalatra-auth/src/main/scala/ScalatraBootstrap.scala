import javax.servlet.ServletContext

import org.scalatra._
import org.scalatra.example.UserController
import org.slf4j.LoggerFactory

class ScalatraBootstrap extends LifeCycle {

  val logger = LoggerFactory.getLogger(getClass)

  override def init(context: ServletContext) {
    context.mount(new UserController, "/*")
  }

  override def destroy(context: ServletContext): Unit = {
    super.destroy(context)
  }
}
