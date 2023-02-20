package dev.jcookeak.tic.tac.toe

import dev.jcookeak.tic.tac.toe.test.Given
import dev.jcookeak.tic.tac.toe.test.assertSoftly
import io.kotest.matchers.collections.shouldContainInOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import kotlin.test.Test

class GameStateTest {

    @Test
    fun executeMoveInitialState() {
        Given {
            val game = ActiveGame.initializeGame()

            When {
                val updatedGame = game.executeMove(0)

                Then {
                    updatedGame.assertSoftly {
                        shouldBeTypeOf<ActiveGame>()
                        moves.assertSoftly {
                            shouldHaveSize(1)
                            first().assertSoftly {
                                player shouldBe Player.X
                                move shouldBe 0
                            }
                        }

                        activePlayer shouldBe Player.O
                    }
                }
            }
        }
    }

    @Test
    fun executeMoveToTieGame() {
        Given {
            val game = ActiveGame(
                moves = listOf(
                    PlayerMove(Player.X, 0),
                    PlayerMove(Player.O, 1),
                    PlayerMove(Player.X, 2),
                    PlayerMove(Player.O, 4),
                    PlayerMove(Player.X, 7),
                    PlayerMove(Player.O, 5),
                    PlayerMove(Player.X, 8),
                    PlayerMove(Player.O, 6),
                )
            )

            When {
                val updatedGame = game.executeMove(3)

                Then {
                    updatedGame.assertSoftly {
                        shouldBeTypeOf<CompletedGame>()
                        moves.assertSoftly {
                            shouldHaveSize(9)

                            this shouldContainInOrder listOf(
                                PlayerMove(Player.X, 0),
                                PlayerMove(Player.O, 1),
                                PlayerMove(Player.X, 2),
                                PlayerMove(Player.O, 4),
                                PlayerMove(Player.X, 7),
                                PlayerMove(Player.O, 5),
                                PlayerMove(Player.X, 8),
                                PlayerMove(Player.O, 6),
                                PlayerMove(Player.X, 3),
                            )
                        }

                        winner shouldBe null
                    }
                }
            }
        }
    }

    @Test
    fun executeMoveToWinGame() {
        Given {
            val game = ActiveGame(
                moves = listOf(
                    PlayerMove(Player.X, 0),
                    PlayerMove(Player.O, 3),
                    PlayerMove(Player.X, 1),
                    PlayerMove(Player.O, 5),
                )
            )

            When {
                val updatedGame = game.executeMove(2)

                Then {
                    updatedGame.assertSoftly {
                        shouldBeTypeOf<CompletedGame>()
                        moves.assertSoftly {
                            shouldHaveSize(5)

                            this shouldContainInOrder listOf(
                                PlayerMove(Player.X, 0),
                                PlayerMove(Player.O, 3),
                                PlayerMove(Player.X, 1),
                                PlayerMove(Player.O, 5),
                                PlayerMove(Player.X, 2),
                            )
                        }

                        winner shouldBe Player.X
                    }
                }
            }
        }
    }

    @Test
    fun undoCompletedGame() {
        Given {
            val game = ActiveGame(
                moves = listOf(
                    PlayerMove(Player.X, 0),
                    PlayerMove(Player.O, 3),
                    PlayerMove(Player.X, 1),
                    PlayerMove(Player.O, 5),
                )
            )
                .executeMove(2)
                .shouldBeTypeOf<CompletedGame>()

            When {
                val updatedGame = game.undo()

                Then {
                    updatedGame.assertSoftly {
                        shouldBeTypeOf<ActiveGame>()
                        moves.assertSoftly {
                            shouldHaveSize(4)

                            this shouldContainInOrder listOf(
                                PlayerMove(Player.X, 0),
                                PlayerMove(Player.O, 3),
                                PlayerMove(Player.X, 1),
                                PlayerMove(Player.O, 5),
                            )
                        }

                        activePlayer shouldBe Player.X
                    }
                }
            }
        }
    }

    @Test
    fun executeOutOfBoundsMove() {
        Given {
            val game = ActiveGame()

            When {
                val updatedGame = game.executeMove(-1)

                Then {
                    updatedGame.assertSoftly {
                        shouldBeTypeOf<MoveOutOfBounds>()
                        message shouldBe "move must be within range 0-8"

                        previousState.assertSoftly {
                            shouldBeTypeOf<ActiveGame>()
                            moves shouldHaveSize 0
                        }
                    }
                }
            }
        }
    }

    @Test
    fun executeDuplicateMove() {
        Given {
            val game = ActiveGame().executeMove(1).shouldBeTypeOf<ActiveGame>()

            When {
                val updatedGame = game.executeMove(1)

                Then {
                    updatedGame.assertSoftly {
                        shouldBeTypeOf<DuplicateMove>()
                        message shouldBe "cell already populated"

                        previousState.assertSoftly {
                            shouldBeTypeOf<ActiveGame>()
                            moves.assertSoftly {
                                shouldHaveSize(1)
                                first().assertSoftly {
                                    player shouldBe Player.X
                                    move shouldBe 1
                                }
                            }

                            activePlayer shouldBe Player.O
                        }
                    }
                }
            }
        }
    }
}
