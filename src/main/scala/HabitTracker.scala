package org.habittracker.application

import scala.annotation.tailrec
import scala.io.StdIn.readLine

var habits: List[Habit] = List.empty


def createHabit() : Option[Habit] =
  val name = readRequired("What Habit? ")
  if habits.exists(_.name.equalsIgnoreCase(name)) then
    println("Habit already exists")
    None
  else
    Some(
      Habit(
        name    = name,
        place   = readRequired("Where to Execute? "),
        when    = readOptional("When to Execute? "),
        success = 0,
        failure = 0,
        tag     = readOptional("Which tag to append? "),
        reps    = 0
      )
    )

@tailrec
def readHabit(habits: List[Habit]): Unit =
  habits match
    case Nil          =>
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
  if habits.isEmpty then
    println("No habits yet.")
    None
  else
    habits.zipWithIndex.foreach { case (h, i) => println(s"${i + 1}) ${h.name}") }
    val choice = readIndex("Which number? ", habits.size)
    if choice.isEmpty then println("Invalid number.")
    choice

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

@tailrec
def filterByValue(habits: List[Habit], field: String): List[Habit] =
  field.trim.toLowerCase match {
    case "tag" =>
      val tagFilter = readLine("Which Tag?: ")
      habits.filter(_.tag.exists(_.equalsIgnoreCase(tagFilter)))
    case "place" =>
      val placeFilter = readLine("Which Place?: ")
      habits.filter(_.place.equalsIgnoreCase(placeFilter))
    case "when" | "time" =>
      val timeFilter = readLine("When?: ")
      habits.filter(_.when.exists(_.equalsIgnoreCase(timeFilter)))
    case "name" =>
      val nameFilter = readLine("Which Name?: ")
      habits.filter(_.name.equalsIgnoreCase(nameFilter))
    case _ =>
      println("You can filter by Tag, Place, Time(When) and Name")
      filterByValue(habits, readLine("Filter by Which?: "))
  }
