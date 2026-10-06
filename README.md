# Habit Tracker

A command-line habit tracker written in Scala 3, built around the four laws of *Atomic Habits* (make it obvious, attractive, easy and satisfying).

Habits are kept in memory while the program runs. Nothing is saved to disk yet.

## Requirements

- Scala 3 (the code uses indentation syntax and `@main`)

## Run

```
scala-cli run .
```

or start `main` from your IDE. All files share the package `org.habittracker.application`.

## Menu

| # | Entry | What it does |
|---|-------|--------------|
| 1 | Habits Overview | Prints every habit |
| 2 | Create Habits | Asks for the habit's details and adds it |
| 3 | Delete Habits | Pick a habit by number, then confirm |
| 4 | Update Habits | Pick a habit, press Enter to keep a value |
| 5 | Track Habits | Record whether you did the habit today |
| 6 | Filter Habits | Show only habits matching a tag, place, time or name |
| 7 | Daily Plan | Prints one "I will..." sentence per habit |
| 8 | Habit Scorecard | Habits ranked by success rate |

## A habit

| Field | Required | Meaning |
|-------|----------|---------|
| `name` | yes | What the habit is. Names must be unique (case-insensitive) |
| `place` | yes | Where you do it |
| `when` | no | Time or moment |
| `tag` | no | A label to filter by |
| `stackedAfter` | no | The habit or event this one follows |
| `reward` | no | What you get afterwards |
| `identity` | no | Who you become by doing it |
| `miniVersion` | no | The two-minute version |
| `success`, `failure`, `reps` | automatic | Counters, updated when you track |
| `missedInRow` | automatic | Misses since the last success |

## Tracking a habit (menu 5)

1. If your last attempt was a miss, you get the warning "Never miss twice."
2. Your identity ("Vote for: ...") and reward are shown.
3. You answer "Got Executed? (Y/n)". Enter counts as yes.
4. If you answered no and the habit has a two-minute version, you are asked whether you did that instead. Doing it counts as success.
5. The counters are updated and a summary line is printed, for example `Run: 3/5 (60%)`.

## Filtering (menu 6)

Enter one of `tag`, `place`, `time` (or `when`), `name`, then the value. Matching is exact but ignores upper and lower case. Anything else asks again.

## The four laws in the code

| Law | Where | Code |
|-----|-------|------|
| 1. Make it obvious | `stackedAfter`, `when`, `place` | `intention`, `dailyPlan` |
| 2. Make it attractive | `identity`, `reward` | `attractive` |
| 3. Make it easy | `miniVersion` | `easyFallback` |
| 4. Make it satisfying | counters, `missedInRow` | `record`, `satisfying`, `successRate`, `scorecard`, `neverMissTwice` |

## Files

| File | Contents |
|------|----------|
| `Habit.scala` | The `Habit` case class |
| `HabitTracker.scala` | The habit list and create, show, delete, select, update, track and filter |
| `FourLaws.scala` | The functions for the four laws |
| `ValidateInput.scala` | Input helpers: `readOptional`, `readRequired`, `readIndex`, `confirm` |
| `main.scala` | The menu and `decision` |

### Input helpers

- `readRequired(prompt)` asks until the answer is not empty.
- `readOptional(prompt)` returns `None` for an empty answer.
- `readIndex(prompt, size)` turns a number the user types (1-based) into a list index, or `None` if invalid.
- `confirm(question)` asks Y/n. Enter means yes, and anything else asks again.

## Current limitations

- Habits are lost when the program ends (no saving).
- Only counters are stored, with no dates, so there are no streaks or history.
- The daily plan lists all habits, since habits have no schedule.
