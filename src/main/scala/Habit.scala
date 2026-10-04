package org.habittracker.application
 
case class Habit(
  name         : String,
  place        : String,
  when         : Option[String],
  success      : Int,
  failure      : Int,
  tag          : Option[String],
  reps         : Int,
  stackedAfter : Option[String] = None,
  reward       : Option[String] = None,
  identity     : Option[String] = None,
  miniVersion  : Option[String] = None,
  missedInRow  : Int            = 0
)
 