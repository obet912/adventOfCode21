package day7

import util.Utils
import kotlin.math.abs


fun main(args: Array<String>) {
  val input: List<String> = Utils.readInputByLine("day7/input")
  val positions = input
    .first()
    .split(',')
    .map { it.toInt() }
    .sorted()


  println("Total constant fuel consumption: ${getConstantMinimum(positions)}")
  println("Total linear fuel consumption: ${getLinearMinimum(positions)}")
}

private fun getConstantMinimum(positions: List<Int>): Int {
  var minimum = Int.MAX_VALUE
  for (common in positions.first()..positions.last()) {
    val value = positions.sumOf { abs(it - common) }
    if (minimum > value) {
      minimum = value
    } else {
      break
    }
  }
  return minimum
}

private fun getLinearMinimum(positions: List<Int>): Int {
  var minimum = Int.MAX_VALUE
  for (common in positions.first()..positions.last()) {
    val value = positions.sumOf { gauss(abs(it - common)) }
    if (minimum > value) {
      minimum = value
    } else {
      break
    }
  }
  return minimum
}

fun gauss(value: Int) = (value * value + value).div(2)