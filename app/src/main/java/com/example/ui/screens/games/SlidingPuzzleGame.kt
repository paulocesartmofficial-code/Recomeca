package com.example.ui.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SlidingPuzzleGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    // Solvable configuration for 3x3 sliding puzzle
    val initialBoard = remember { listOf(1, 2, 3, 4, 5, 6, 0, 7, 8) }
    var board by remember { mutableStateOf(initialBoard) }
    var moves by remember { mutableIntStateOf(0) }
    var isSolved by remember { mutableStateOf(false) }

    fun checkSolved(current: List<Int>): Boolean {
        return current == listOf(1, 2, 3, 4, 5, 6, 7, 8, 0)
    }

    fun onTileClick(index: Int) {
        if (isSolved) return
        val emptyIndex = board.indexOf(0)
        val row = index / 3
        val col = index % 3
        val emptyRow = emptyIndex / 3
        val emptyCol = emptyIndex % 3

        val isAdjacent = (Math.abs(row - emptyRow) + Math.abs(col - emptyCol)) == 1
        if (isAdjacent) {
            val newBoard = board.toMutableList()
            newBoard[emptyIndex] = newBoard[index]
            newBoard[index] = 0
            board = newBoard
            moves++
            if (checkSolved(newBoard)) {
                isSolved = true
                onGameComplete(700, moves, 60)
            }
        }
    }

    fun resetPuzzle() {
        board = listOf(1, 2, 3, 4, 0, 5, 7, 8, 6)
        moves = 0
        isSolved = false
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Movimentos: $moves",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = LightMintGreen
            )

            IconButton(onClick = { resetPuzzle() }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Embaralhar", tint = TextMuted)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 3x3 Box
        Box(
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(DarkCard)
                .border(2.dp, PrimaryGreen, RoundedCornerShape(12.dp))
                .padding(6.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                for (r in 0 until 3) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        for (c in 0 until 3) {
                            val index = r * 3 + c
                            val value = board[index]
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (value == 0) DarkSecondaryBg else DarkGreenAccent
                                    )
                                    .border(
                                        1.dp,
                                        if (value == 0) DarkBorder else LightMintGreen,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable(enabled = value != 0) {
                                        onTileClick(index)
                                    }
                                    .testTag("sliding_tile_$index")
                            ) {
                                if (value != 0) {
                                    Text(
                                        text = "$value",
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 26.sp,
                                        color = TextPrimary
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (isSolved) {
            Spacer(modifier = Modifier.height(16.dp))
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkGreenAccent,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🏆 Quebra-Cabeça Resolvido!",
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "Você ordenou as peças em $moves movimentos.",
                        fontSize = 13.sp,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}
