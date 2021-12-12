package day4

import util.Utils

const val BOARD_SIZE = 5

fun main(args: Array<String>) {
  val input: List<String> = Utils.readInputByLine("day4/input")
  val draws = input.first().split(',').map { it.toInt() }
  val boards: List<Board> = input.drop(1).windowed(BOARD_SIZE + 1, BOARD_SIZE + 1).map { it.toBoard() }

  outer@for (called in draws) {
    println("draw $called")
    for (board in boards) {
      board.markElement(called)
      if (board.isWon()) {
        println("Won with score: ${board.winningScore(called)}")
        break@outer
      }
    }
  }

  println("----------------------")

  val wonBoards = mutableListOf<Int>()
  draws.forEach { called ->
    println("draw $called")
    boards.forEachIndexed { index, board ->
      if (!wonBoards.contains(index)) {
        board.markElement(called)
        if (board.isWon()) {
          wonBoards.add(index)
        }
        if (boards.size == wonBoards.size) {
          println("Last win with score: ${board.winningScore(called)}")
        }
      }
    }
  }
}

private fun List<String>.toBoard() = Board(this
  .filter { it.isNotEmpty() }
  .flatMap { it.split(' ') }
  .filter { it.isNotEmpty() }
  .map { it.toInt() }
)

data class Board(val values: List<Int>) {
  private val marked: MutableList<Boolean> = BooleanArray(values.size).toMutableList()

  fun markElement(number: Int) {
    val index = values.indexOf(number)
    if (index != -1) {
      marked[index] = true
    }
  }

  fun isWon(): Boolean {
    for (index in 0 until BOARD_SIZE) {
      if (isRowMarked(index) || isColumnMarked(index)) {
        // println(this)
        // println(marked)
        return true
      }
    }
    return false
  }

  private fun isRowMarked(rowIndex: Int): Boolean {
    for (i in 0 until BOARD_SIZE) {
      if (!marked[i + rowIndex * BOARD_SIZE]) {
        return false
      }
    }
    return true
  }

  private fun isColumnMarked(columnIndex: Int): Boolean {
    for (i in 0 until BOARD_SIZE) {
      if (!marked[columnIndex + i * BOARD_SIZE]) {
        return false
      }
    }
    return true
  }

  fun winningScore(lastCalled: Int): Int {
    val unmarked = marked
      .mapIndexed { index, isMarked ->
        if (!isMarked) {
          values[index]
        } else {
          0
        }
      }
    // println("unmarked $unmarked")
    val unmarkedSum = unmarked
      .sum()
    // println("unmarkedSum $unmarkedSum")
    return unmarkedSum * lastCalled
  }
}