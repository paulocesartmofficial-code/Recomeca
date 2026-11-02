package com.example.ui.screens.games

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
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
import kotlinx.coroutines.delay

data class MemoryCard(
    val id: Int,
    val symbol: String,
    var isFaceUp: Boolean = false,
    var isMatched: Boolean = false
)

@Composable
fun MemoryGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    val allSymbols = remember { listOf("🌱", "🛡️", "💎", "☀️", "🏆", "🌟", "🕊️", "⚡", "🧘", "🌸") }
    var difficulty by remember { mutableStateOf("Médio") } // Fácil (8), Médio (16), Avançado (20)

    val pairCount = when (difficulty) {
        "Fácil" -> 4
        "Avançado" -> 10
        else -> 8
    }

    fun generateCards(count: Int): List<MemoryCard> {
        val selected = allSymbols.take(count)
        return (selected + selected).shuffled().mapIndexed { index, s ->
            MemoryCard(id = index, symbol = s)
        }
    }

    var cards by remember(difficulty) { mutableStateOf(generateCards(pairCount)) }
    var flippedIndices by remember { mutableStateOf<List<Int>>(emptyList()) }
    var movesCount by remember { mutableIntStateOf(0) }
    var comboStreak by remember { mutableIntStateOf(0) }
    var maxCombo by remember { mutableIntStateOf(0) }
    var isGameWon by remember { mutableStateOf(false) }

    fun resetGame() {
        cards = generateCards(pairCount)
        flippedIndices = emptyList()
        movesCount = 0
        comboStreak = 0
        maxCombo = 0
        isGameWon = false
    }

    LaunchedEffect(flippedIndices) {
        if (flippedIndices.size == 2) {
            val idx1 = flippedIndices[0]
            val idx2 = flippedIndices[1]
            movesCount++
            delay(650)
            if (cards[idx1].symbol == cards[idx2].symbol) {
                comboStreak++
                if (comboStreak > maxCombo) maxCombo = comboStreak
                cards = cards.mapIndexed { idx, card ->
                    if (idx == idx1 || idx == idx2) card.copy(isMatched = true, isFaceUp = true) else card
                }
                if (cards.all { it.isMatched }) {
                    isGameWon = true
                    val finalScore = (1000 - movesCount * 20 + maxCombo * 50).coerceAtLeast(300)
                    onGameComplete(finalScore, movesCount, 60)
                }
            } else {
                comboStreak = 0
                cards = cards.mapIndexed { idx, card ->
                    if (idx == idx1 || idx == idx2) card.copy(isFaceUp = false) else card
                }
            }
            flippedIndices = emptyList()
        }
    }

    val columns = 4
    val rows = (cards.size + columns - 1) / columns

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Difficulty and status bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                listOf("Fácil", "Médio", "Avançado").forEach { diff ->
                    FilterChip(
                        selected = difficulty == diff,
                        onClick = {
                            difficulty = diff
                            resetGame()
                        },
                        label = { Text(diff, fontSize = 10.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DarkGreenAccent,
                            selectedLabelColor = LightMintGreen,
                            containerColor = DarkSecondaryBg,
                            labelColor = TextMuted
                        )
                    )
                }
            }

            IconButton(onClick = { resetGame() }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reiniciar", tint = LightMintGreen)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Dynamic Combo and Moves Indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Movimentos: $movesCount",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = TextPrimary
            )

            if (comboStreak > 1) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AccentGold.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentGold)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = AccentGold, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Combo x$comboStreak!",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AccentGold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Dynamic Adaptive Cards Grid
        for (r in 0 until rows) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (c in 0 until columns) {
                    val index = r * columns + c
                    if (index < cards.size) {
                        val card = cards[index]
                        val cardSize = if (cards.size > 16) 62.dp else 74.dp

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(cardSize)
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    when {
                                        card.isMatched -> DarkGreenAccent
                                        card.isFaceUp -> DarkCardElevated
                                        else -> DarkSecondaryBg
                                    }
                                )
                                .border(
                                    1.dp,
                                    when {
                                        card.isMatched -> LightMintGreen
                                        card.isFaceUp -> WarmAmber
                                        else -> DarkBorder
                                    },
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable(enabled = !card.isFaceUp && !card.isMatched && flippedIndices.size < 2) {
                                    cards = cards.mapIndexed { i, cd ->
                                        if (i == index) cd.copy(isFaceUp = true) else cd
                                    }
                                    flippedIndices = flippedIndices + index
                                }
                                .testTag("mem_card_$index")
                        ) {
                            if (card.isFaceUp || card.isMatched) {
                                Text(
                                    text = card.symbol,
                                    fontSize = if (cards.size > 16) 24.sp else 28.sp
                                )
                            } else {
                                Text(text = "🌱", fontSize = 15.sp, color = DarkBorder)
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.size(74.dp))
                    }
                }
            }
        }

        if (isGameWon) {
            Spacer(modifier = Modifier.height(14.dp))
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = DarkGreenAccent,
                border = androidx.compose.foundation.BorderStroke(1.dp, LightMintGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🏆 Vitória Extraordinária!",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen
                    )
                    Text(
                        text = "Concluído em $movesCount movimentos com Combo Máximo x$maxCombo.",
                        fontSize = 12.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = { resetGame() },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                    ) {
                        Text("Jogar Próxima Rodada", color = Color.White)
                    }
                }
            }
        }
    }
}
