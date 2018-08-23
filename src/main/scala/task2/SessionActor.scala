package task

import akka.actor.Actor
import java.time.{Duration, LocalDateTime}

import task.SessionActor.SessionData

import scala.concurrent.Future

object SessionActor {
  /** limit buffer length */
  val MAX_MESSAGE_LENGTH = 10000
  /** limit buffer size (MB) */
  val MAX_DATA_SIZE      = 20l
  /** limit buffer age (minute) */
  val MAX_BUFFER_AGE     = 10l

  case class SessionData(shopToken: String) {

    def sizeInBytes: Long = 0l

  }
  case class Store(sessionData: SessionData)
  case object Flush

}

class SessionActor extends Actor with MessageSender with MessageTransformer {
  import SessionActor._

  /** data storage */
  var buffer = Seq.empty[SessionData]
  /** last time of data was stored */
  var updatedAt = LocalDateTime.now

  def receive = {
    case Store(sessionData: SessionData) =>
      isBufferLengthExceeded | isBufferSizeExceeded | isBufferExpired(LocalDateTime.now) match {
        case true => {
          sender() ! Flush
          store(sessionData)
        }
        case false => store(sessionData)
      }
    case Flush => send(buffer)
  }

  private def isBufferLengthExceeded(): Boolean =
    buffer.length > MAX_MESSAGE_LENGTH

  private def isBufferSizeExceeded(): Boolean =
    buffer.map(_.sizeInBytes).sum > MAX_DATA_SIZE * 1024l * 1024l

  private def isBufferExpired(now: LocalDateTime): Boolean =
    Duration.between(updatedAt, now).toMinutes >= MAX_BUFFER_AGE

  private def store(sessionData: SessionData): Unit = {
    buffer = buffer :+ sessionData
    transformMessage(sessionData)
    updatedAt = LocalDateTime.now
  }

}

trait MessageSender {

  /** flush all data in buffer */
  def send(messages: Seq[SessionData]): Unit = {}

}

trait MessageTransformer {

  def transformMessage(message: SessionData): Future[SessionData] = Future.successful()

}