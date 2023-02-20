package dev.jcookeak.tic.tac.toe

sealed interface GameError: TicTacToe {
    val message: String
}

data class DuplicateMove(
    val previousState : GameState
): GameError {
    override val message: String = "cell already populated"
}

data class MoveOutOfBounds(
    val previousState : GameState
): GameError {
    override val message: String = "move must be within range 0-8"
}