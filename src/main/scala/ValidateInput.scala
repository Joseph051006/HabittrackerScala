package org.habittracker.application

import scala.io.StdIn.readLine

def readOptional(prompt: String): Option[String] =
  Option(readLine(prompt)).map(_.trim).filter(_.nonEmpty)

