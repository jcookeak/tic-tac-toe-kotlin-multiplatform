package dev.jcookeak.tic.tac.toe

sealed interface GameError: TicTacToe {
    val message: String
    val previousState: GameState
}

data class DuplicateMove(
    override val previousState : GameState
): GameError {
    override val message: String = "cell already populated"
}

data class MoveOutOfBounds(
    override val previousState : GameState
): GameError {
    override val message: String = "move must be within range 0-8"
}