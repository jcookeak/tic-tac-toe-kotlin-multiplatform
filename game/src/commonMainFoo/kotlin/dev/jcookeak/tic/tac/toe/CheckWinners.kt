package dev.jcookeak.tic.tac.toe

private val winningLines = listOf(
    listOf(0, 1, 2),
    listOf(3, 4, 5),
    listOf(6, 7, 8),
    listOf(0, 3, 6),
    listOf(1, 4, 7),
    listOf(2, 5, 8),
    listOf(0, 4, 8),
    listOf(2, 4, 6),
)

fun Array<PlayerMove>.checkWinner(): Player? {
    val squares = this.associateBy { it.move }

    return winningLines.firstOrNull { line ->
        val (a, b, c) = line

        squares[a]?.player != null
                && squares[a]?.player == squares[b]?.player
                && squares[a]?.player == squares[c]?.player
    }?.let {
        squares[it.first()]?.player
    }
}