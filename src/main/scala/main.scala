package org.habittracker.application

import scala.annotation.tailrec
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
  println("3) Delete Habits")
  println("WIP")
  println("WIP")
  println("=========================")
  decision(readLine())

def decision(prompt: String): Unit=
  prompt match {
      case "1"  => readHabit(habits)
      case "2"  => habits = habits :+ createHabit(habits)
      case "3"  => selectHabit(habits).foreach(h => habits = deleteHabit(habits, h))
      case _    => main()
  }
  main()
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

@tailrec
def readHabit(habits: List[Habit]): Unit =
  habits match
    case Nil          => ()
    case head :: tail =>
      createTable(head)
      readHabit(tail)

def createTable(habit: Habit): Unit =
  println("=" * 60)
  println(habit.productElementNames.mkString(" | "))
  println(habit.productIterator.map {
    case Some(v) => v
    case None    => "-"
    case other   => other
  }.mkString(" | "))
  println("=" * 60)

def deleteHabit(habits: List[Habit] ,habit: Habit): List[Habit] =
  habits.filterNot(_ == habit)

def selectHabit(habits: List[Habit]): Option[Habit] =
  habits.zipWithIndex.foreach { case (h, i) => println(s"${i + 1}) ${h.name}") }
  readLine("Which number? ").toIntOption.flatMap(n => habits.lift(n - 1))