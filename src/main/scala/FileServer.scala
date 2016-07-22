/*
import java.nio.channels.FileChannel
import java.nio.file.{Path, Paths, StandardOpenOption}
import java.nio.{ByteBuffer, MappedByteBuffer}

import akka.actor.ActorSystem
import akka.http.Http
import akka.http.model.HttpEntity.ChunkStreamPart
import akka.http.model._
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Sink, Source}
import akka.util.{ByteString, Timeout}

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Try
import scala.util.control.NonFatal

class ByteBufferIterator(buffer:ByteBuffer, chunkSize:Int) extends Iterator[ByteString] {
  require(buffer.isReadOnly)
  require(chunkSize > 0)

  override def hasNext = buffer.hasRemaining

  override def next(): ByteString = {
    val size = chunkSize min buffer.remaining()
    val temp = buffer.slice()
    temp.limit(size)
    buffer.position(buffer.position() + size)
    ByteString(temp)
  }
}

object Main extends App {

  def map(path: Path) : MappedByteBuffer = {
    val channel = FileChannel.open(path, StandardOpenOption.READ)
    val result = channel.map(FileChannel.MapMode.READ_ONLY, 0L, channel.size())
    channel.close()
    result
  }

  implicit val system = ActorSystem()

  implicit val materializer = ActorFlowMaterializer()
  implicit val askTimeout: Timeout = 500.millis

  import HttpMethods._

  val requestHandler: HttpRequest ⇒ HttpResponse = {
    case HttpRequest(GET, uri, headers, _, _) =>
      val path = Paths.get(uri.path.toString())
      val result = Try {
        val mappedByteBuffer = map(path)
        val iterator = new ByteBufferIterator(mappedByteBuffer, 4096)
        val chunks = Source(() => iterator).map { x =>
          println("Chunk of size " + x.size)
          ChunkStreamPart(x)
        }
        HttpResponse(entity = HttpEntity.Chunked(MediaTypes.`application/octet-stream`, chunks))
      } recover {
        case NonFatal(cause) =>
          HttpResponse(StatusCodes.InternalServerError, entity = cause.getMessage)
      }
      result.get
    case _: HttpRequest ⇒ HttpResponse(StatusCodes.NotFound, entity = "Unknown resource!")
  }

  val serverSource: Source[Http.IncomingConnection, Future[Http.ServerBinding]] = Http(system).bind(interface = "localhost", port = 8080)
  val bindingFuture: Future[Http.ServerBinding] = serverSource.to(Sink.foreach { connection =>
    // foreach materializes the source
    println("Accepted new connection from " + connection.remoteAddress)
    // ... and then actually handle the connection
    connection.handleWithSyncHandler(requestHandler)
  }).run()

  System.in.read()
  system.shutdown()
  system.awaitTermination()
}*/
