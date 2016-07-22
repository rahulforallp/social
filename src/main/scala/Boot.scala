import akka.actor._
import akka.http.model.HttpMethods._
import akka.http.model._
import akka.stream.scaladsl.Flow
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.Http
import akka.stream.FlowMaterializer

/**
  * Created by rahul on 21/7/16.
  */
object Boot extends App {

  // the actor system to use. Required for flowmaterializer and HTTP.
  // passed in implicit
   implicit val system = ActorSystem("Streams")
  implicit val materializer = FlowMaterializer()

  // start the server on the specified interface and port.
  val serverBinding2 = Http().bind(interface = "localhost", port = 8091)
  serverBinding2.connections.foreach { connection =>
    connection.handleWith(Flow[HttpRequest].mapAsync(asyncHandler))
  }
println("Started.")
  def asyncHandler(request: HttpRequest): Future[HttpResponse] = {
    request match {
      case HttpRequest(GET, Uri.Path("/timeline"), _, _, _) => {
        Future {
          println("\n\n\ngot Request .  \n\n\n  ")
          val responseJson = facebook.newsFeed().getString("message") + twitter.newsfeed().getString("message")
          println("Result transferred : "+responseJson)
          new HttpResponse(entity = responseJson)
        }
      }
      case _ =>
        Future {
          println("\n\n\n verification done.  \n\n\n  ")
          new HttpResponse(entity = "")
        }
    }

  }


}