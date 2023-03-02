@file:OptIn(ExperimentalJsExport::class)
package dev.jcookeak.tic.tac.toe

@JsExport
sealed interface GameError: TicTacToe {
    val message: String
}

@JsExport
data class DuplicateMove(
    val previousState : GameState
): GameError {
    override val message: String = "cell already populated"
}

@JsExport
data class MoveOutOfBounds(
    val previousState : GameState
): GameError {
    override val message: String = "move must be within range 0-8"
}