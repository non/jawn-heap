package jawnHeap

import scala.annotation.tailrec

import org.slf4j.LoggerFactory

import jawn.ast._
import jawn.ast.JParser._
import jawn.AsyncParser
import jawn.ParseException

object Parser {

  val log = LoggerFactory.getLogger(this.getClass)

  def streamConversion(chunks: Stream[String]) = {

    val p = jawn.Parser.async[JValue](mode = AsyncParser.UnwrapArray)

    @tailrec
    def loop(st: Stream[String], p: jawn.AsyncParser[JValue]): Unit =
      st match {
        case Stream.Empty ⇒
          p.finish() match {
            case Left(exception) ⇒
              log.error("An error occured at the end of the stream", exception)
            case Right(jsSeq) ⇒
              log.debug(s"End of parsing with $jsSeq")
          }
        case s #:: tail ⇒
          p.absorb(s) match {
            case Left(exception) ⇒
              log.error("An error occured in the stream", exception)
            case Right(jsSeq) ⇒
              //log.debug(s"Parsed $jsSeq and going on")
              loop(tail, p)
          }
      }

    // Business Time!
    loop(chunks, p)
  }
}