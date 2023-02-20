package dev.jcookeak.tic.tac.toe

import dev.jcookeak.tic.tac.toe.test.Given
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CheckWinnersTest {
    @Test
    fun emptyMoves() {
        Given {
            val moves: GameMoves = emptyList()

            When {
                val winner = moves.checkWinner()

                Then {
                    winner shouldBe null
                }
            }
        }
    }

    @Test
    fun winningLineWithDifferentPlayers() {
        Given {
            val moves: GameMoves = listOf(
                PlayerMove(Player.X, 0),
                PlayerMove(Player.O, 1),
                PlayerMove(Player.X, 2),
            )

            When {
                val winner = moves.checkWinner()

                Then {
                    winner shouldBe null
                }
            }
        }
    }

    @Test
    fun winningLine() {
        Given {
            val moves: GameMoves = listOf(
                PlayerMove(Player.X, 0),
                PlayerMove(Player.O, 3),
                PlayerMove(Player.X, 1),
                PlayerMove(Player.O, 4),
                PlayerMove(Player.X, 2),
            )

            When {
                val winner = moves.checkWinner()

                Then {
                    winner shouldBe Player.X
                }
            }
        }
    }

    @Test
    fun noWinners() {
        Given {
            val moves: GameMoves = listOf(
                PlayerMove(Player.X, 0),
                PlayerMove(Player.O, 1),
                PlayerMove(Player.X, 2),
                PlayerMove(Player.O, 3),
                PlayerMove(Player.X, 4),
                PlayerMove(Player.O, 5),
                PlayerMove(Player.X, 7),
                PlayerMove(Player.O, 6),
                PlayerMove(Player.O, 8),
            )

            When {
                val winner = moves.checkWinner()

                Then {
                    winner shouldBe null
                }
            }
        }
    }
}