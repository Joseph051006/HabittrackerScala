package org.habittracker.application

import scala.io.StdIn.readLine

@main
def main(): Unit=
  println("=========================")
  println("Dear User, what would you like to organize?")
  println("1) Habits Overview")
  println("2) Create Habits")
  println("3) Delete Habits")
  println("4) Update Habits")
  println("5) Track Habits")
  println("6) Filter Habits")
  println("7) Daily Plan")
  println("8) Habit Scorecard")
  println("0) Exit")
  println("=========================")
  decision(readLine())

def decision(prompt: String): Unit=
  prompt match {
      case "1"  => readHabit(habits)
      case "2"  => createHabit().foreach(h => habits = habits :+ h)
      case "3"  => selectHabit(habits).foreach(removeHabit)
      case "4"  => selectHabit(habits).foreach(h => habits = updateHabit(habits, h))
      case "5"  => selectHabit(habits).foreach(h => habits = trackHabit(habits, h))
      case "6"  => readHabit(filterByValue(habits, readLine("Filter by Which?(Tag, Place, Time(When) and Name)")))
      case "7"  => dailyPlan(habits)
      case "8"  => scorecard(habits)
      case "0"  => sys.exit(0)
      case _    => main()
  }
  main()
