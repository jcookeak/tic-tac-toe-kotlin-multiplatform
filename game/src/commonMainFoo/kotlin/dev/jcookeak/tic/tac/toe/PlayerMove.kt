package dev.jcookeak.tic.tac.toe

typealias GameMoves = List<PlayerMove>

@JsExport
data class PlayerMove(val player: Player, val move: Int)
