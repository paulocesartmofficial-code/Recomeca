package com.example.ui.screens.games

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.RecomecaCard
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class ReactionState {
    IDLE,
    WAITING,
    TRIGGERED,
    TOO_EARLY,
    ROUND_FINISHED,
    ALL_FINISHED
}

@Composable
fun ReactionGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    var state by remember { mutableStateOf(ReactionState.IDLE) }
    var startTime by remember { mutableLongStateOf(0L) }
    var currentReactionTimeMs by remember { mutableLongStateOf(0L) }
    var attempts by remember { mutableStateOf<List<Long>>(emptyList()) }
    var round by remember { mutableIntStateOf(1) }

    val totalRounds = 5

    LaunchedEffect(state) {
        if (state == ReactionState.WAITING) {
            val randomDelay = Random.nextLong(1800, 4200)
            delay(randomDelay)
            if (state == ReactionState.WAITING) {
                startTime = System.currentTimeMillis()
                state = ReactionState.TRIGGERED
            }
        }
    }

    fun resetWholeGame() {
        state = ReactionState.IDLE
        attempts = emptyList()
        round = 1
        currentReactionTimeMs = 0L
    }

    fun onTap() {
        when (state) {
            ReactionState.IDLE -> {
                state = ReactionState.WAITING
            }
            ReactionState.WAITING -> {
                state = ReactionState.TOO_EARLY
            }
            ReactionState.TRIGGERED -> {
                val end = System.currentTimeMillis()
                currentReactionTimeMs = end - startTime
                attempts = attempts + currentReactionTimeMs

                if (attempts.size >= totalRounds) {
                    state = ReactionState.ALL_FINISHED
                    val avg = attempts.average()
                    val score = ((1000 - avg).coerceIn(200.0, 990.0)).toInt()
                    onGameComplete(score, totalRounds, 40)
                } else {
                    state = ReactionState.ROUND_FINISHED
                }
            }
            ReactionState.TOO_EARLY -> {
                state = ReactionState.WAITING
            }
            ReactionState.ROUND_FINISHED -> {
                round++
                state = ReactionState.WAITING
            }
            ReactionState.ALL_FINISHED -> {
                resetWholeGame()
            }
        }
    }

    val (bgColor, message, subMessage) = when (state) {
        ReactionState.IDLE -> Triple(
            DarkCardElevated,
            "Toque para Iniciar o Teste",
            "Serão 5 rodadas. Quando a tela ficar VERDE, toque o mais rápido possível!"
        )
        ReactionState.WAITING -> Triple(
            AccentRed.copy(alpha = 0.85f),
            "Aguarde o sinal verde...",
            "Não toque ainda! Mantenha a atenção plena."
        )
        ReactionState.TRIGGERED -> Triple(
            LightMintGreen,
            "TOQUE AGORA!",
            "Reflexo rápido!"
        )
        ReactionState.TOO_EARLY -> Triple(
            AccentGold.copy(alpha = 0.75f),
            "Muito cedo!",
            "Você tocou antes do verde. Toque para tentar novamente esta rodada."
        )
        ReactionState.ROUND_FINISHED -> Triple(
            DarkGreenAccent,
            "$currentReactionTimeMs ms",
            "Rodada $round/$totalRounds concluída. Toque para a próxima!"
        )
        ReactionState.ALL_FINISHED -> {
            val avg = attempts.average().toLong()
            Triple(
                DarkGreenAccent,
                "Média: $avg ms",
                when {
                    avg < 260 -> "Classificação: ⚡ Reflexo Ninja Lendário!"
                    avg < 340 -> "Classificação: 🦅 Foco de Águia Excelente!"
                    else -> "Classificação: 🧘 Mente Desperta e Atenta!"
                }
            )
        }
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
                text = "Rodada: ${attempts.size}/$totalRounds",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )

            IconButton(onClick = { resetWholeGame() }) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reiniciar", tint = LightMintGreen)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(bgColor)
                .border(2.dp, PrimaryGreen, RoundedCornerShape(16.dp))
                .clickable { onTap() }
                .padding(20.dp)
                .testTag("reaction_tap_area")
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = message,
                    fontSize = if (state == ReactionState.ROUND_FINISHED || state == ReactionState.ALL_FINISHED) 36.sp else 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (state == ReactionState.TRIGGERED) DarkBackground else TextPrimary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = subMessage,
                    fontSize = 13.sp,
                    color = if (state == ReactionState.TRIGGERED) DarkBackground else TextSecondary,
                    textAlign = TextAlign.Center
                )
            }
        }

        if (attempts.isNotEmpty()) {
            Spacer(modifier = Modifier.height(14.dp))
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkCard
            ) {
                Text(
                    text = "HISTÓRICO DAS TENTATIVAS:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    attempts.forEachIndexed { i, time ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "R${i + 1}", fontSize = 10.sp, color = TextMuted)
                            Text(text = "${time}ms", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                    }
                }
            }
        }
    }
}
