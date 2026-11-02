package com.example.ui.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.data.seed.GameQuestionsData
import com.example.ui.components.RecomecaCard
import com.example.ui.theme.*

@Composable
fun ScrambleGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    val wordList = remember { GameQuestionsData.scrambleWords }
    var currentIndex by remember { mutableIntStateOf(0) }
    val currentWord = wordList.getOrNull(currentIndex)

    var assembledLetters by remember { mutableStateOf<List<Char>>(emptyList()) }
    var availableIndices by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var isWordSolved by remember { mutableStateOf(false) }

    LaunchedEffect(currentIndex) {
        if (currentWord != null) {
            assembledLetters = emptyList()
            availableIndices = currentWord.scrambled.indices.toSet()
            isWordSolved = false
        }
    }

    if (currentWord == null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "✨ Todas as Palavras Decifradas!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LightMintGreen
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onGameComplete(800, wordList.size, 90) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Finalizar e Ganhar XP", color = Color.White)
            }
        }
        return
    }

    fun onLetterClick(index: Int, char: Char) {
        if (availableIndices.contains(index)) {
            availableIndices = availableIndices - index
            assembledLetters = assembledLetters + char

            val assembledString = assembledLetters.joinToString("")
            if (assembledString == currentWord.original) {
                isWordSolved = true
            }
        }
    }

    fun onBackspace() {
        if (assembledLetters.isNotEmpty()) {
            val lastChar = assembledLetters.last()
            assembledLetters = assembledLetters.dropLast(1)
            // Restore one occurrence of lastChar to availableIndices
            val originalScrambled = currentWord.scrambled
            val restoredIndex = originalScrambled.indices.firstOrNull {
                originalScrambled[it] == lastChar && !availableIndices.contains(it)
            }
            if (restoredIndex != null) {
                availableIndices = availableIndices + restoredIndex
            }
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Palavra ${currentIndex + 1} de ${wordList.size} • Categoria: ${currentWord.category}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = AccentGold
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Clue Card
        RecomecaCard(
            borderColor = DarkBorder,
            backgroundColor = DarkCard
        ) {
            Text(text = "DICA:", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = LightMintGreen)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = currentWord.hint,
                fontSize = 14.sp,
                color = TextPrimary
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Assembled Word Display
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = DarkSecondaryBg,
            border = androidx.compose.foundation.BorderStroke(1.dp, if (isWordSolved) LightMintGreen else DarkBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (assembledLetters.isEmpty()) {
                    Text(text = "Toque nas letras abaixo...", fontSize = 13.sp, color = TextMuted)
                } else {
                    assembledLetters.forEach { c ->
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(36.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(DarkGreenAccent)
                        ) {
                            Text(
                                text = "$c",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = LightMintGreen
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Scrambled Letters Selection Bank
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            currentWord.scrambled.forEachIndexed { idx, char ->
                val isAvailable = availableIndices.contains(idx)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(3.dp)
                        .size(42.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (isAvailable) DarkCard else DarkSecondaryBg)
                        .border(1.dp, if (isAvailable) LightMintGreen else DarkBorder, RoundedCornerShape(8.dp))
                        .clickable(enabled = isAvailable && !isWordSolved) {
                            onLetterClick(idx, char)
                        }
                        .testTag("scramble_btn_$idx")
                ) {
                    Text(
                        text = "$char",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = if (isAvailable) TextPrimary else TextMuted
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { onBackspace() },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkBorder),
                enabled = assembledLetters.isNotEmpty() && !isWordSolved
            ) {
                Text("Apagar Última", fontSize = 12.sp, color = TextSecondary)
            }
        }

        if (isWordSolved) {
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
                        text = "🏆 Palavra Correta: ${currentWord.original}!",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { currentIndex++ },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Próxima Palavra →", color = Color.White)
                    }
                }
            }
        }
    }
}
