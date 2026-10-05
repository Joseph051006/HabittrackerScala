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

def record(habit: Habit, executed: Boolean): Habit =
  if executed then
    habit.copy(
      success = habit.success + 1,
      reps = habit.reps + 1,
      missedInRow = 0
    )
  else
    habit.copy(
      failure = habit.failure + 1,
      reps = habit.reps + 1,
      missedInRow = habit.missedInRow + 1
    )

def satisfying(habit: Habit): Unit =
  println(s"${habit.name}: ${habit.success}/${habit.reps} (${successRate(habit)}%)")

def successRate(habit: Habit): Int =
  if habit.reps == 0 then 0 else habit.success * 100 / habit.reps
