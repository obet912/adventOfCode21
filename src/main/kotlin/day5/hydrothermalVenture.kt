package day5

import util.Utils
import kotlin.math.abs
import kotlin.math.min

fun main(args: Array<String>) {
  val input: List<String> = Utils.readInputByLine("day5/input")

  val overlaps = input
    .map { Line.from(it) }
    .flatMap { it.allStraightLinePoints() }
    .groupBy { it }
    .mapValues { it.value.size }
    .filterValues { it > 1 }

  println("number of overlaps: ${overlaps.size}")

  val overlapsWithDiagonals = input
    .map { Line.from(it) }
    .flatMap { it.allLinePoints() }
    .groupBy { it }
    .mapValues { it.value.size }
    .filterValues { it > 1 }

  println("number of overlaps with diagonals: ${overlapsWithDiagonals.size}")
}

data class Point(val x: Int, val y: Int) {
  companion object {
    fun from(commaSeparatedString: String) = commaSeparatedString
      .split(',')
      .map { it.toInt() }
      .let { Point(it.first(), it.last()) }
  }
}

data class Line(val start: Point, val end: Point) {
  companion object {
    fun from(arrowSeparatedString: String) = arrowSeparatedString
      .split(" -> ")
      .map { Point.from(it) }
      .let { Line(it.first(), it.last()) }
  }

  fun allStraightLinePoints(): List<Point> {
    val points = if (start.x == end.x) {
      (1 until abs(start.y - end.y)).map { Point(start.x, start.y.coerceAtLeast(end.y) - it) }
    } else if (start.y == end.y) {
      (1 until abs(start.x - end.x)).map { Point(start.x.coerceAtLeast(end.x) - it, start.y) }
    } else {
      emptyList()
    }

    return points
      .let {
        if (it.isNotEmpty()) {
          it + listOf(start, end)
        } else {
          emptyList()
        }
      }
      .also {
        // println(it)
      }
  }

  fun allLinePoints(): List<Point> {
    val points = if (start.x == end.x) {
      (1 until abs(start.y - end.y)).map { Point(start.x, start.y.coerceAtLeast(end.y) - it) }
    } else if (start.y == end.y) {
      (1 until abs(start.x - end.x)).map { Point(start.x.coerceAtLeast(end.x) - it, start.y) }
    } else {
      (1 until abs(start.x - end.x)).map {
        if (start.x - end.x == start.y - end.y) {
          Point(start.x.coerceAtLeast(end.x) - it, start.y.coerceAtLeast(end.y) - it)
        } else if (start.x > end.x) {
          Point(start.x.coerceAtLeast(end.x) - it, min(start.y, end.y) + it)
        } else {
          Point(min(start.x, end.x) + it, start.y.coerceAtLeast(end.y) - it)
        }
      }
    }

    return (points + listOf(start, end)).also {
      // println(it) }
    }
  }
}