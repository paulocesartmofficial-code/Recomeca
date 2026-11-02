package com.example.ui.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lightbulb
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
fun SudokuGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    val solution = remember {
        listOf(
            listOf(5, 3, 4, 6, 7, 8, 9, 1, 2),
            listOf(6, 7, 2, 1, 9, 5, 3, 4, 8),
            listOf(1, 9, 8, 3, 4, 2, 5, 6, 7),
            listOf(8, 5, 9, 7, 6, 1, 4, 2, 3),
            listOf(4, 2, 6, 8, 5, 3, 7, 9, 1),
            listOf(7, 1, 3, 9, 2, 4, 8, 5, 6),
            listOf(9, 6, 1, 5, 3, 7, 2, 8, 4),
            listOf(2, 8, 7, 4, 1, 9, 6, 3, 5),
            listOf(3, 4, 5, 2, 8, 6, 1, 7, 9)
        )
    }

    val initialBoard = remember {
        listOf(
            listOf(5, 3, 0, 0, 7, 0, 0, 0, 0),
            listOf(6, 0, 0, 1, 9, 5, 0, 0, 0),
            listOf(0, 9, 8, 0, 0, 0, 0, 6, 0),
            listOf(8, 0, 0, 0, 6, 0, 0, 0, 3),
            listOf(4, 0, 0, 8, 0, 3, 0, 0, 1),
            listOf(7, 0, 0, 0, 2, 0, 0, 0, 6),
            listOf(0, 6, 0, 0, 0, 0, 2, 8, 0),
            listOf(0, 0, 0, 4, 1, 9, 0, 0, 5),
            listOf(0, 0, 0, 0, 8, 0, 0, 7, 9)
        )
    }

    var currentGrid by remember { mutableStateOf(initialBoard.map { it.toMutableList() }) }
    var selectedRow by remember { mutableIntStateOf(0) }
    var selectedCol by remember { mutableIntStateOf(2) }
    var hintsRemaining by remember { mutableIntStateOf(3) }
    var errorsCount by remember { mutableIntStateOf(0) }
    var verificationMessage by remember { mutableStateOf<String?>(null) }

    // Currently focused number to highlight across the entire board
    val highlightedNumber = currentGrid[selectedRow][selectedCol]

    fun fillNumber(num: Int) {
        if (initialBoard[selectedRow][selectedCol] == 0) {
            val copy = currentGrid.map { it.toMutableList() }
            copy[selectedRow][selectedCol] = num
            currentGrid = copy

            if (num != 0 && num != solution[selectedRow][selectedCol]) {
                errorsCount++
                verificationMessage = "Número incorreto nesta posição."
            } else {
                verificationMessage = null
            }
        }
    }

    fun useHint() {
        if (hintsRemaining > 0 && initialBoard[selectedRow][selectedCol] == 0) {
            val correct = solution[selectedRow][selectedCol]
            fillNumber(correct)
            hintsRemaining--
            verificationMessage = "Dica aplicada!"
        }
    }

    fun checkSudoku() {
        var allCorrect = true
        for (r in 0 until 9) {
            for (c in 0 until 9) {
                if (currentGrid[r][c] != solution[r][c]) {
                    allCorrect = false
                    break
                }
            }
        }
        if (allCorrect) {
            verificationMessage = "🏆 Parabéns! Grade de Sudoku 9x9 resolvida com sucesso!"
            onGameComplete(950, 45, 180)
        } else {
            verificationMessage = "Ainda há células vazias ou números com conflito."
        }
    }

    fun resetSudoku() {
        currentGrid = initialBoard.map { it.toMutableList() }
        hintsRemaining = 3
        errorsCount = 0
        verificationMessage = null
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Dicas: $hintsRemaining",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Erros: $errorsCount",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (errorsCount > 0) AccentRed else TextMuted
                )
            }

            Row {
                IconButton(onClick = { useHint() }, enabled = hintsRemaining > 0) {
                    Icon(imageVector = Icons.Default.Lightbulb, contentDescription = "Dica", tint = AccentGold)
                }
                IconButton(onClick = { resetSudoku() }) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reiniciar", tint = LightMintGreen)
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 9x9 Sudoku Board Container with Matching Number Highlighting
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(DarkCard)
                .border(2.dp, PrimaryGreen, RoundedCornerShape(10.dp))
                .padding(2.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                for (r in 0 until 9) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        for (c in 0 until 9) {
                            val value = currentGrid[r][c]
                            val isInitial = initialBoard[r][c] != 0
                            val isSelected = selectedRow == r && selectedCol == c
                            val isInSelectedBlock = (selectedRow / 3 == r / 3) && (selectedCol / 3 == c / 3)
                            val isSameNumber = highlightedNumber != 0 && value == highlightedNumber

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(
                                        when {
                                            isSelected -> DarkGreenAccent
                                            isSameNumber -> DarkGreenAccent.copy(alpha = 0.65f)
                                            isInSelectedBlock -> DarkCardElevated
                                            else -> DarkCard
                                        }
                                    )
                                    .border(
                                        0.5.dp,
                                        if (r % 3 == 0 || c % 3 == 0) LightMintGreen.copy(alpha = 0.45f) else DarkBorder
                                    )
                                    .clickable {
                                        selectedRow = r
                                        selectedCol = c
                                    }
                                    .testTag("sudoku_cell_${r}_$c")
                            ) {
                                if (value != 0) {
                                    Text(
                                        text = "$value",
                                        fontWeight = if (isInitial) FontWeight.ExtraBold else FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = when {
                                            isSameNumber -> WarmAmber
                                            isInitial -> TextPrimary
                                            else -> LightMintGreen
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Numeric Keypad
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (num in 1..5) {
                Button(
                    onClick = { fillNumber(num) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSecondaryBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (highlightedNumber == num) WarmAmber else DarkBorder),
                    modifier = Modifier.size(46.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("$num", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (highlightedNumber == num) WarmAmber else TextPrimary)
                }
            }
            Button(
                onClick = { fillNumber(0) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBorder),
                modifier = Modifier.size(46.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text("⌫", fontSize = 14.sp, color = TextSecondary)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (num in 6..9) {
                Button(
                    onClick = { fillNumber(num) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkSecondaryBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (highlightedNumber == num) WarmAmber else DarkBorder),
                    modifier = Modifier.size(46.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("$num", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = if (highlightedNumber == num) WarmAmber else TextPrimary)
                }
            }

            Button(
                onClick = { checkSudoku() },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier
                    .height(46.dp)
                    .width(100.dp)
                    .testTag("sudoku_verify_btn"),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                Text("Verificar", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        if (verificationMessage != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = DarkGreenAccent,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = verificationMessage!!,
                    fontSize = 12.sp,
                    color = LightMintGreen,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(10.dp)
                )
            }
        }
    }
}
