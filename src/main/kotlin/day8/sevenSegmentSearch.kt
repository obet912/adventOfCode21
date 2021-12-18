package day8

import util.Utils


fun main(args: Array<String>) {
  val input: List<String> = Utils.readInputByLine("day8/input")

  val answer = countSimpleNumbers(input)
  println("1, 4, 7, or 8 appear $answer times in answers")

  val listOfResults = decodeResults(input)
  println("All output values added ${listOfResults.sum()}")
}

fun decodeResults(input: List<String>): List<Int> {
  return input
    .map { it.split(" | ").let { list -> list.first() to list.last() } }
    .map { (notes, output) ->
      val coding = decode(notes.split(' '))
      // println(coding)
      output
        .split(' ')
        .map { outputString -> outputString.toCharArray().map { requireNotNull(coding[it]) }.toSet() }
        .map {
          // println("$it to ${mappings[it]}")
          requireNotNull(mappings[it])
        }
        .joinToString(separator = "")
        .toInt()
    }
}

private fun countSimpleNumbers(input: List<String>): Int = input
  .map { it.split(" | ")[1] }
  .flatMap { it.split(' ') }
  .filter { it.isNotEmpty() }
  .filter { it.length == 2 || it.length == 3 || it.length == 4 || it.length == 7 }
  .size


fun decode(notes: List<String>): Map<Char, Char> {
  val coding = mutableMapOf<Char, Char>()
  val one = notes.find { it.length == 2 }.orEmpty().asSequence().toSet()
  val seven = notes.find { it.length == 3 }.orEmpty().asSequence().toSet()
  val four = notes.find { it.length == 4 }.orEmpty().asSequence().toSet()
  val eight = notes.find { it.length == 7 }.orEmpty().asSequence().toSet()
  require(one.isNotEmpty() && seven.isNotEmpty() && four.isNotEmpty() && eight.isNotEmpty()) { "should not be empty" }

  coding[seven.minus(one).first()] = 'a'

  // 2, 3, 5 = 5 length
  val fivers = notes.filter { it.length == 5 }.map { it.asSequence().toSet() }
  // 0, 6, 9 = 6 length
  val sixers = notes.filter { it.length == 6 }.map { it.asSequence().toSet() }

  coding[requireNotNull(sixers.map { one.intersect(eight.minus(it)) }.find { it.size == 1 }?.first())] = 'c'
  val five = requireNotNull(fivers.find { fiver -> fiver.all { coding[it] != 'c' } })
  val six = requireNotNull(sixers.find { sixer -> coding[eight.minus(sixer).first()] == 'c' })
  coding[six.minus(five).first()] = 'e'
  val nine = requireNotNull(sixers.find { sixer -> coding[eight.minus(sixer).first()] == 'e' })
  val zero = sixers.minus(setOf(six, nine)).first()
  coding[eight.minus(zero).first()] = 'd'
  // println(coding)
  coding[requireNotNull(four.minus(one).find { coding[it] != 'd' })] = 'b'
  coding[requireNotNull(one.find { coding[it] != 'c' })] = 'f'
  coding[requireNotNull(zero.minus(four).minus(seven).find { coding[it] != 'e' })] = 'g'
  return coding
}

val mappings = mapOf(
  setOf('a', 'b', 'c', 'e', 'f', 'g') to 0,
  setOf('c', 'f') to 1,
  setOf('a', 'c', 'd', 'e', 'g') to 2,
  setOf('a', 'c', 'd', 'f', 'g') to 3,
  setOf('b', 'c', 'd', 'f') to 4,
  setOf('a', 'b', 'd', 'f', 'g') to 5,
  setOf('a', 'b', 'd', 'e', 'f', 'g') to 6,
  setOf('a', 'c', 'f') to 7,
  setOf('a', 'b', 'c', 'd', 'e', 'f', 'g') to 8,
  setOf('a', 'b', 'c', 'd', 'f', 'g') to 9,
)