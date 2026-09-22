package org.habittracker.application

import scala.*
import scala.io.StdIn.readLine

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

var habits : List[Habit] = List()

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
      case "1" => throw NotImplementedError("Under Development")
      case "2" => createHabit()
      case _ => throw NotImplementedError("Under Development")
  }

def createHabit(habits: List[Habit],name : String, place: String, when: Option[String], success: Option[Int], failure: Option[Int], tag: Option[String], reps: Int) =
  val habit: Habit = Habit.
  )
  habit :: habits




