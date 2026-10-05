package org.habittracker.application

def intention(habit: Habit): String =
  val trigger = habit.stackedAfter match
    case Some(anchor) => s"After $anchor"
    case None         => s"At ${habit.when.getOrElse("any time")}"
  s"$trigger, I will ${habit.name} in ${habit.place}"

def dailyPlan(list: List[Habit]): Unit =
  if list.isEmpty then println("No habits yet.")
  else list.foreach(h => println(intention(h)))

def attractive(habit: Habit): Unit =
  habit.identity.foreach(i => println(s"Vote for: $i"))
  habit.reward.foreach(r => println(s"Reward afterwards: $r"))