package day1


import util.Utils

fun main(args: Array<String>) {
  val input: List<String> = Utils.readInputByLine("day1/input")
  val increases = calcIncreases(input.map { it.toInt() })
  println("Num of increases: $increases")
  val increasesWindowed = calcIncreasesWindowed(input.map { it.toInt() })
  println("Num of windowed increases: $increasesWindowed")
}

fun calcIncreases(input: List<Int>): Int = input
    .zipWithNext { a, b -> a < b }
    .filterTrue()
    .size

fun calcIncreasesWindowed(input: List<Int>): Int = input
    .windowed(3)
    .map { it.sum() }
    .zipWithNext { a, b -> a < b }
    .filterTrue()
    .size

private fun List<Boolean>.filterTrue(): List<Boolean> = this.filter { it }
