package org.habittracker.application

import scala.{:+, ::, *}
import scala.io.StdIn.{readInt, readLine}

case class Habit
  (
    name    : String,
    place   : String,
    when    : Option[String],
    success : Option[Int],
    failure : Option[Int],
    tag     : Option[String],
    reps    : Int
  )

var habits : List[Habit] = List.empty

@main
def main(): Unit=
  println("Dear User, what would you like to organize?")
  println("1) Habits Overview")
  println("2) Create Habits")
  println("WIP")
  println("WIP")
  println("WIP")
  println("=========================")
  decision(readLine())
  


def decision(prompt: String): Unit=
  prompt match {
      case "1"  => throw NotImplementedError("Under Development")
      case "2"  => habits = habits :+ createHabit(habits); main()
      case _    => throw NotImplementedError("Under Development")
  }

def createHabit(habits: List[Habit]) : Habit =
  val habit: Habit = Habit(
    name    = readLine("What Habit?"),
    place   = readLine("Where to Execute?"),
    when    = Option(readLine("When to Execute?")),
    success = Option(0),
    failure = Option(0),
    tag = Option(readLine("Which tag to append?")), // WIP
    reps = 0
  )
  habit



