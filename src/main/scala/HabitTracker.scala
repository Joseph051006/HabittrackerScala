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
        name         = name,
        place        = readRequired("Where to Execute? "),
        when         = readOptional("When to Execute? "),
        success      = 0,
        failure      = 0,
        tag          = readOptional("Which tag to append? "),
        reps         = 0,
        stackedAfter = readOptional("After which habit or event? "),
        reward       = readOptional("Reward? "),
        identity     = readOptional("Who do you become? "),
        miniVersion  = readOptional("Two-minute version? ")
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

def removeHabit(index: Int): Unit =
  val habit = habits(index)
  if confirm(s"Delete ${habit.name}? (Y/n) ") then
    habits = deleteHabit(habits, index)
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
    name         = ask("Name", old.name),
    place        = ask("Place", old.place),
    when         = askOpt("When", old.when),
    tag          = askOpt("Tag", old.tag),
    stackedAfter = askOpt("After", old.stackedAfter),
    reward       = askOpt("Reward", old.reward),
    identity     = askOpt("Identity", old.identity),
    miniVersion  = askOpt("Two-minute version", old.miniVersion)
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
  attractive(old)
  val executed = confirm("Got Excecuted? (Y/n)")
  val updated  = record(old, executed)
  satisfying(updated)
  habits.updated(index, updated)


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
