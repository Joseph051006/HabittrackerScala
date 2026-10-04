package org.habittracker.application

import scala.annotation.tailrec
import scala.io.StdIn.{readInt, readLine}

case class Habit
  (
    name    : String,
    place   : String,
    when    : Option[String],
    success : Int,
    failure : Int,
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
  println("4) Update Habits")
  println("5) Track Habits")
  println("WIP")
  println("=========================")
  decision(readLine())

def decision(prompt: String): Unit=
  prompt match {
      case "1"  => readHabit(habits)
      case "2"  => habits = habits :+ createHabit(habits)
      case "3"  => selectHabit(habits).foreach(h => habits = deleteHabit(habits, h))
      case "4"  => selectHabit(habits).foreach(h => habits = updateHabit(habits, h))
      case "5"  => selectHabit(habits).foreach(h => habits = trackHabit(habits, h))
      case _    => main()
  }
  main()

def createHabit(habits: List[Habit]) : Habit =
  val habit: Habit = Habit(
    name    = readLine("What Habit?"),
    place   = readLine("Where to Execute?"),
    when    = Option(readLine("When to Execute?")),
    success = 0,
    failure = 0,
    tag     = Option(readLine("Which tag to append?")), // WIP
    reps    = 0
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

def deleteHabit(habits: List[Habit], index: Int): List[Habit] =
  habits.patch(index, Nil, 1)
// returns the 0-based index of the chosen habit, or None on invalid input
def selectHabit(habits: List[Habit]): Option[Int] =
  habits.zipWithIndex.foreach { case (h, i) => println(s"${i + 1}) ${h.name}") }
  readLine("Which number? ").toIntOption.map(_ - 1).filter(habits.indices.contains)

def updateHabit(habits: List[Habit], index: Int): List[Habit]  =
  val old = habits(index)
  val updated = old.copy(
    name  = ask("name:    ", old.name),
    place = ask("place:   ", old.place),
    when  = askOpt("When: ", old.when),
    tag   = askOpt("Tag:  ", old.tag)
  )
  habits.updated(index, updated)

def ask(label: String, current: String) =
  readLine(s"$label [$current]: ") match
    case ""     => current
    case input  => input

def askOpt(label: String, current: Option[String]) =
  readLine(s"$label  [${current.getOrElse("-")}]: ") match
    case ""     => current
    case input  => Some(input)

def trackHabit(habits: List[Habit], index: Int): List[Habit] =
  val old = habits(index)
  if !gotExecuted() then {
    val updated = old.copy(
      failure = old.failure + 1,
      success = old.success + 0,
      reps    = old.reps    + 1
    )
    habits.updated(index, updated)
  } else {
    val updated = old.copy(
      failure = old.failure + 0,
      success = old.success + 1,
      reps    = old.reps    + 1
    )
    habits.updated(index, updated)
  }

@tailrec
def gotExecuted(): Boolean =
  readLine("Got Executed? (Y/n)") match {
    case "Y" | "y" | ""   => true
    case "N" | "n"        => false
    case _                => gotExecuted()
  }