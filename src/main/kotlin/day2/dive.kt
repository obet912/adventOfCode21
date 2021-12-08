package day2


import util.Utils

fun main(args: Array<String>) {
  val input: List<String> = Utils.readInputByLine("day2/input")
  val course = parseCourse(input)
  val (horizontal, depth) = calcPosition(course)
  println("Position($horizontal, $depth): ${horizontal * depth}")
  val (horizontalAim, depthAim) = calcPositionAim(course)
  println("Position($horizontalAim, $depthAim) with aim: ${horizontalAim * depthAim}")
}

fun calcPosition(course: List<Instruction>): Pair<Int, Int> {
  var horizontal = 0
  var depth = 0
  course.forEach {
    when (it.direction) {
      Direction.forward -> horizontal += it.range
      Direction.down -> depth += it.range
      Direction.up -> depth -= it.range
    }
  }
  return horizontal to depth
}

fun calcPositionAim(course: List<Instruction>): Pair<Int, Int> {
  var horizontal = 0
  var depth = 0
  var aim = 0
  course.forEach {
    when (it.direction) {
      Direction.forward -> {
        horizontal += it.range
        depth += (aim * it.range)
      }
      Direction.down -> aim += it.range
      Direction.up -> aim -= it.range
    }
  }
  return horizontal to depth
}


fun parseCourse(input: List<String>): List<Instruction> = input
    .map {
      val split = it.split(" ")
      Instruction(Direction.valueOf(split.first()), range = split.last().toInt())
    }

data class Instruction(val direction: Direction, val range: Int)

enum class Direction {
  forward,
  down,
  up
}
