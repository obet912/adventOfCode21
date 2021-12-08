package day3

import util.Utils

fun main(args: Array<String>) {
  val input: List<String> = Utils.readInputByLine("day3/input")
  val size = input.first().length
  val (gamma, epsilon) = calcConsumption(size, input)
  println("Power consumption($gamma, $epsilon): ${gamma * epsilon}")

  val oxygen = calcLifeSupport(0, oxygen = true, input)
  val co2 = calcLifeSupport(0, oxygen = false, input)
  println("Power consumption($oxygen, $co2): ${oxygen * co2}")
}

fun calcConsumption(size: Int, input: List<String>): Pair<Int, Int> {
  val ones = IntArray(size).toMutableList()
  input.forEach {
    it.forEachIndexed { index, char ->
      when (char) {
        '1' -> ones[index]++
        '0' -> ones[index]--
      }
    }
  }

  return ones.getNumberFromOnes() to ones.map { it * -1 }.getNumberFromOnes()
}

fun calcLifeSupport(index: Int, oxygen: Boolean, input: List<String>): Int {
  if (input.size == 1) {
    return input.first().toInt(2)
  }
  // find most common bit
  var mostCommonBit = 0
  input.map { it[index] }.forEach {
    when (it) {
      '1' -> mostCommonBit++
      '0' -> mostCommonBit--
    }
  }
  if (!oxygen) {
    mostCommonBit *= -1
  }

  // filter startWith
  val filteredInput = input.filter {
    it[index] == (when {
      mostCommonBit > 0 -> '1'
      mostCommonBit < 0 -> '0'
      else -> if (oxygen) '1' else '0'
    })
  }

  // recursive
  return calcLifeSupport(index + 1, oxygen, filteredInput)
}

private fun List<Int>.getNumberFromOnes() =
    joinToString("") {
      if (it >= 0) {
        "1"
      } else {
        "0"
      }
    }
        .toInt(2)

