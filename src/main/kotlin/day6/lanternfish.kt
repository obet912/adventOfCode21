package day6

import util.Utils

private const val BIRTH_CYCLE_DAYS = 7
private const val MATURE_CYCLE_DAYS = 3

fun main(args: Array<String>) {
  val input: List<String> = Utils.readInputByLine("day6/input")

  val fishesInCycle = LongArray(BIRTH_CYCLE_DAYS).apply {
    input
      .first()
      .split(',')
      .map { it.toInt() }
      .groupBy { it }
      .mapValues { it.value.size.toLong() }
      .toSortedMap()
      .forEach { (day, count) -> this[day] += count }
  }
  val newFishes = LongArray(MATURE_CYCLE_DAYS)

  val numberOfSimulatedDays = 256

  for (day in 1..numberOfSimulatedDays + 1) {
    val oldFishesIndex = day % BIRTH_CYCLE_DAYS
    val birthIndex = day % MATURE_CYCLE_DAYS
    val inEightDays = (day + 2) % (MATURE_CYCLE_DAYS)

    val currentFish = fishesInCycle[oldFishesIndex]

    newFishes[inEightDays] = currentFish
    fishesInCycle[oldFishesIndex] += newFishes[birthIndex]

    println("After ${day.toString().padStart(2)} days: ${fishesInCycle.sum()}")
    fishesInCycle.print(oldFishesIndex)
  }

  println("Fishes after $numberOfSimulatedDays days: ${fishesInCycle.sum()}")
}

private fun LongArray.print(oldFishesIndex: Int) = this
  .mapIndexed { index, value ->
    "$index -> $value ${
      if (index == oldFishesIndex) {
        "*"
      } else {
        ""
      }
    }"
  }
  .sorted()
  .forEach { println(it) }

