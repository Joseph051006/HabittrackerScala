package org.habittracker.application

import scala.annotation.tailrec
import scala.io.StdIn.{readInt, readLine}

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
  println("=========================")
  decision(readLine())

def decision(prompt: String): Unit=
  prompt match {
      case "1"  => readHabit(habits)
      case "2"  => habits = habits :+ createHabit(habits)
      case "3"  => selectHabit(habits).foreach(h => habits = deleteHabit(habits, h))
      case "4"  => selectHabit(habits).foreach(h => habits = updateHabit(habits, h))
      case "5"  => selectHabit(habits).foreach(h => habits = trackHabit(habits, h))
      case "6"  => readHabit(filterByValue(habits, readLine("Filter by Which?(Tag, Place, Time(When) and Name)")))
      case _    => main()
  }
  main()
