package dev.jcookeak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.jcookeak.tic.tac.toe.ActiveGame
import dev.jcookeak.tic.tac.toe.CompletedGame
import dev.jcookeak.tic.tac.toe.GameError
import dev.jcookeak.tic.tac.toe.GameState
import dev.jcookeak.tic.tac.toe.TicTacToe
import dev.jcookeak.tic.tac.toe.gameBoard
import dev.jcookeak.tic.tac.toe.undo
import dev.jcookeak.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

fun TicTacToe.executeMove(square: Int): TicTacToe =
    when (this) {
        is ActiveGame -> executeMove(square)
        is CompletedGame -> this
        is GameError -> this.previousState
    }

@Composable
fun GameContent(
    innerPadding: PaddingValues,
    gameState: TicTacToe,
    onSquareClick: (square: Int) -> Unit,
    resetGame: () -> Unit,
    undoMove: () -> Unit,
    displayMessage: (errorState: GameError) -> Unit
) {
    LazyColumn(contentPadding = innerPadding) {
        item {
            GameHeadline(ticTacToe = gameState)
            Spacer(Modifier.size(10.dp))
        }

        // game board
        item {
            when (gameState) {
                is GameState -> GameBoard(gameState = gameState) { onSquareClick(it) }
                is GameError -> {
                    displayMessage(gameState)
                    GameBoard(gameState = gameState.previousState, onClick = {})
                }
            }
        }

        // action bar
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                val visible = !(gameState is ActiveGame && gameState.moves.isEmpty())

                val (reset, undo) = when (gameState) {
                    is GameState -> {
                        resetGame to undoMove
                    }
                    is GameError -> {
                        {} to {}
                    }
                }

                AnimatedButton(
                    visible = visible,
                    text = "Reset Game",
                    icon = Icons.Filled.Delete,
                    onClick = reset,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onErrorContainer,
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                )
                AnimatedButton(
                    visible = visible,
                    text = "Undo Last Move",
                    icon = Icons.Filled.ArrowBack,
                    onClick = undo
                )
            }
        }
    }
}

@Composable
fun RowScope.AnimatedButton(
    visible: Boolean,
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
) {
    AnimatedVisibility(visible = visible) {
        Button(
            modifier = Modifier
                .weight(1f, fill = true)
                .padding(10.dp),
            onClick = onClick,
            colors = colors
        ) {
            Icon(
                icon,
                contentDescription = text,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(text)
        }
    }
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                var gameState: TicTacToe by remember { mutableStateOf(ActiveGame.initializeGame()) }
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                Scaffold(
                    containerColor = MaterialTheme.colorScheme.background,
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = {
                                Text(
                                    "Tic-Tac-Toe",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {},
                            actions = {}
                        )
                    }
                ) { innerPadding ->
                    GameContent(
                        innerPadding = innerPadding,
                        gameState = gameState,
                        onSquareClick = { gameState = gameState.executeMove(it) },
                        resetGame = { gameState = ActiveGame.initializeGame() },
                        undoMove = {
                            when (val s = gameState) {
                                is GameState -> gameState = s.undo(1)
                                else -> {}
                            }
                        },
                        displayMessage = { errorState ->
                            scope.launch {
                                snackbarHostState.showSnackbar(errorState.message)
                            }
                            gameState = errorState.previousState
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GameBoard(
    gameState: GameState,
    onClick: (square: Int) -> Unit,
) {
    val populatedMoves = gameState.moves.gameBoard

    (0 until 9).chunked(3).forEach { rowIndexes ->
        Row {
            rowIndexes.forEach { id ->
                key(id) {
                    square(
                        id = id,
                        player = populatedMoves[id]?.player?.name,
                        onClick = onClick
                    )
                }
            }
        }
    }
}

@Composable
fun RowScope.square(
    id: Int,
    player: String?,
    onClick: (i: Int) -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .weight(1f, fill = true)
            .clickable(onClick = {
                onClick(id)
            })
            .padding(1.dp)
    ) {
        Text(
            player ?: "",
            style = MaterialTheme.typography.displayLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GameHeadline(
    ticTacToe: TicTacToe,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
) {
    when (ticTacToe) {
        is ActiveGame -> {
            Text(
                "Active Player: ${ticTacToe.activePlayer.name}",
                style = style,
                modifier = modifier
            )
        }
        is CompletedGame -> {
            ticTacToe.winner?.let {
                Text(
                    "Winner: ${it.name}",
                    style = style,
                    modifier = modifier
                )
            } ?: Text(
                "Tie Game",
                style = style,
                modifier = modifier
            )
        }
        is GameError -> Text(
            "",
            style = style,
            modifier = modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {

    }
}