package org.habittracker.application

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
