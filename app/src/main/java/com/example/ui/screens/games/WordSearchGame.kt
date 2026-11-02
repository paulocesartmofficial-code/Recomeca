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

data class WordSearchTheme(
    val title: String,
    val words: List<String>,
    val grid: List<List<Char>>
)

@Composable
fun WordSearchGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    val theme1 = WordSearchTheme(
        "Superação",
        listOf("FOCO", "SAUDE", "TEMPO", "VALOR", "VIDA", "FUTURO"),
        listOf(
            listOf('F', 'O', 'C', 'O', 'A', 'B', 'P', 'Z'),
            listOf('S', 'A', 'U', 'D', 'E', 'K', 'A', 'X'),
            listOf('T', 'E', 'M', 'P', 'O', 'M', 'Z', 'C'),
            listOf('V', 'A', 'L', 'O', 'R', 'R', 'F', 'B'),
            listOf('V', 'I', 'D', 'A', 'Q', 'W', 'U', 'N'),
            listOf('L', 'I', 'B', 'E', 'R', 'T', 'T', 'M'),
            listOf('G', 'U', 'E', 'R', 'R', 'A', 'U', 'O'),
            listOf('F', 'U', 'T', 'U', 'R', 'O', 'R', 'P')
        )
    )

    val theme2 = WordSearchTheme(
        "Serenidade",
        listOf("PAZ", "CALMA", "SERENO", "BRISA", "MENTE"),
        listOf(
            listOf('P', 'A', 'Z', 'X', 'Y', 'W', 'K', 'L'),
            listOf('C', 'A', 'L', 'M', 'A', 'B', 'C', 'D'),
            listOf('S', 'E', 'R', 'E', 'N', 'O', 'Z', 'P'),
            listOf('B', 'R', 'I', 'S', 'A', 'M', 'Q', 'R'),
            listOf('M', 'E', 'N', 'T', 'E', 'J', 'H', 'F'),
            listOf('A', 'L', 'I', 'V', 'I', 'O', 'S', 'T'),
            listOf('S', 'O', 'N', 'O', 'B', 'O', 'M', 'A'),
            listOf('F', 'L', 'O', 'R', 'E', 'S', 'T', 'A')
        )
    )

    var currentThemeIndex by remember { mutableIntStateOf(0) }
    val currentTheme = if (currentThemeIndex == 0) theme1 else theme2

    var foundWords by remember(currentThemeIndex) { mutableStateOf<Set<String>>(emptySet()) }
    var selectedLetters by remember(currentThemeIndex) { mutableStateOf<List<Pair<Int, Int>>>(emptyList()) }
    var currentWord by remember(currentThemeIndex) { mutableStateOf("") }

    fun toggleCell(r: Int, c: Int) {
        val coord = Pair(r, c)
        if (selectedLetters.contains(coord)) {
            selectedLetters = selectedLetters - coord
        } else {
            selectedLetters = selectedLetters + coord
        }
        currentWord = selectedLetters.map { currentTheme.grid[it.first][it.second] }.joinToString("")
    }

    fun submitWord() {
        val candidate = currentWord.uppercase()
        if (currentTheme.words.contains(candidate) && !foundWords.contains(candidate)) {
            foundWords = foundWords + candidate
            selectedLetters = emptyList()
            currentWord = ""
            if (foundWords.size == currentTheme.words.size) {
                onGameComplete(950, foundWords.size, 100)
            }
        } else {
            selectedLetters = emptyList()
            currentWord = ""
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Theme selector and controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf("Superação", "Serenidade").forEachIndexed { index, name ->
                    FilterChip(
                        selected = currentThemeIndex == index,
                        onClick = {
                            currentThemeIndex = index
                            foundWords = emptySet()
                            selectedLetters = emptyList()
                            currentWord = ""
                        },
                        label = { Text(name, fontSize = 10.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DarkGreenAccent,
                            selectedLabelColor = LightMintGreen,
                            containerColor = DarkSecondaryBg,
                            labelColor = TextMuted
                        )
                    )
                }
            }

            IconButton(
                onClick = {
                    foundWords = emptySet()
                    selectedLetters = emptyList()
                    currentWord = ""
                }
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reiniciar", tint = LightMintGreen)
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Target Words Chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            currentTheme.words.forEach { word ->
                val isFound = foundWords.contains(word)
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (isFound) DarkGreenAccent else DarkSecondaryBg,
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isFound) LightMintGreen else DarkBorder)
                ) {
                    Text(
                        text = if (isFound) "✓ $word" else word,
                        fontSize = 10.sp,
                        fontWeight = if (isFound) FontWeight.Bold else FontWeight.Normal,
                        color = if (isFound) LightMintGreen else TextMuted,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 8x8 Letter Grid
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(DarkCard)
                .border(1.dp, DarkBorder, RoundedCornerShape(10.dp))
                .padding(4.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                for (r in 0 until 8) {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    ) {
                        for (c in 0 until 8) {
                            val isSelected = selectedLetters.contains(Pair(r, c))
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) DarkGreenAccent else DarkSecondaryBg)
                                    .border(
                                        1.dp,
                                        if (isSelected) LightMintGreen else Color.Transparent,
                                        RoundedCornerShape(6.dp)
                                    )
                                    .clickable { toggleCell(r, c) }
                                    .testTag("wordsearch_cell_${r}_$c")
                            ) {
                                Text(
                                    text = "${currentTheme.grid[r][c]}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (isSelected) LightMintGreen else TextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Palavra: ${currentWord.ifEmpty { "---" }}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Button(
                onClick = { submitWord() },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                enabled = currentWord.length >= 3,
                modifier = Modifier.testTag("wordsearch_submit_btn")
            ) {
                Text("Confirmar Palavra", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}
