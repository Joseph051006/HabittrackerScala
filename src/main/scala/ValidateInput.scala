package org.habittracker.application

import scala.annotation.tailrec
import scala.io.StdIn.readLine

def readOptional(prompt: String): Option[String] =
  Option(readLine(prompt)).map(_.trim).filter(_.nonEmpty)

@tailrec
def readRequired(prompt: String): String =
  Option(readLine(prompt)).map(_.trim) match {
    case Some(text) if text.nonEmpty => text
    case Some(_) => println("This Field is required")
      readRequired(prompt)
    case None => sys.exit(0)