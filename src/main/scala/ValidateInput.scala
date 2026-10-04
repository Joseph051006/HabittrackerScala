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
  }

def readIndex(prompt: String, size: Int): Option[Int] =
  Option(readLine(prompt))
    .flatMap(_.trim.toIntOption)
    .map(_ - 1)
    .filter(i => i >= 0 && i < size)

@tailrec
def confirm(question: String): Boolean =
  Option(readLine(question)).map(_.trim.toLowerCase()) match {
    case Some("y") | Some("") => true
    case Some("n") => false
    case Some(_) => confirm(question)
    case None => false
  }