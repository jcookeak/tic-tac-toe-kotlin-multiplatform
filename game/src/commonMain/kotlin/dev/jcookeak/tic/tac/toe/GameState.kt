package dev.jcookeak.tic.tac.toe

sealed interface GameState: TicTacToe {
    val moves: GameMoves
}

data class ActiveGame internal constructor(
    override val moves: GameMoves = emptyList()
): GameState {
    val activePlayer: Player = if (moves.size % 2 == 0) Player.X else Player.O

    fun executeMove(move: Int): TicTacToe = when {
        move < 0 || move >= 9 -> MoveOutOfBounds(this)
        move in moves.map(PlayerMove::move) -> DuplicateMove(this)
        else -> {
            (moves + PlayerMove(activePlayer, move)).run {
                val possibleWinner = checkWinner()
                when {
                    possibleWinner != null -> CompletedGame(this)
                    size >= 9 -> CompletedGame(this)
                    else -> ActiveGame(this)
                }
            }
        }
    }

    companion object {
        fun initializeGame(): ActiveGame = ActiveGame()
    }
}

data class CompletedGame(
    override val moves: GameMoves
): GameState {
    val winner: Player? = moves.checkWinner()
}

fun GameState.undo(numberOfMoves: Int = 1) = ActiveGame(moves.dropLast(numberOfMoves))