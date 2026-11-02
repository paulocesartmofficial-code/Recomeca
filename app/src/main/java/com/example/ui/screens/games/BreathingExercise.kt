package com.example.ui.screens.games

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlinx.coroutines.delay

enum class BreathingTechnique(
    val title: String,
    val description: String,
    val inhaleTime: Int,
    val holdTime: Int,
    val exhaleTime: Int
) {
    RELAX_478("Técnica 4-7-8", "Alívio imediato da ansiedade e desaceleração do ritmo cardíaco", 4, 7, 8),
    BOX_BREATHING("Box 4-4-4", "Respiração em caixa usada para foco e clareza mental", 4, 4, 4),
    CALM_46("Suave 4-6", "Ritmo tranquilo e equilibrado para dias agitados", 4, 2, 6)
}

@Composable
fun BreathingExercise(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    var selectedTechnique by remember { mutableStateOf(BreathingTechnique.RELAX_478) }
    var sessionMinutes by remember { mutableIntStateOf(3) }
    var isRunning by remember { mutableStateOf(false) }
    var phase by remember { mutableStateOf("Inspire") }
    var secondsInPhase by remember { mutableIntStateOf(selectedTechnique.inhaleTime) }
    var totalSecondsElapsed by remember { mutableIntStateOf(0) }

    // Breathing Animation Scale
    val animatedScale by animateFloatAsState(
        targetValue = when (phase) {
            "Inspire" -> 1.38f
            "Segure" -> 1.38f
            else -> 0.85f // Expire
        },
        animationSpec = tween(
            durationMillis = when (phase) {
                "Inspire" -> selectedTechnique.inhaleTime * 1000
                "Segure" -> selectedTechnique.holdTime * 1000
                else -> selectedTechnique.exhaleTime * 1000
            },
            easing = LinearEasing
        ),
        label = "breath_scale"
    )

    LaunchedEffect(isRunning, phase, selectedTechnique) {
        if (!isRunning) return@LaunchedEffect

        when (phase) {
            "Inspire" -> {
                for (s in selectedTechnique.inhaleTime downTo 1) {
                    secondsInPhase = s
                    delay(1000)
                    totalSecondsElapsed++
                }
                phase = "Segure"
            }
            "Segure" -> {
                for (s in selectedTechnique.holdTime downTo 1) {
                    secondsInPhase = s
                    delay(1000)
                    totalSecondsElapsed++
                }
                phase = "Expire"
            }
            "Expire" -> {
                for (s in selectedTechnique.exhaleTime downTo 1) {
                    secondsInPhase = s
                    delay(1000)
                    totalSecondsElapsed++
                }
                phase = "Inspire"
            }
        }

        if (totalSecondsElapsed >= sessionMinutes * 60) {
            isRunning = false
            onGameComplete(500, 1, sessionMinutes * 60)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Technique selector chips
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            BreathingTechnique.values().forEach { tech ->
                FilterChip(
                    selected = selectedTechnique == tech,
                    onClick = {
                        if (!isRunning) {
                            selectedTechnique = tech
                            phase = "Inspire"
                            secondsInPhase = tech.inhaleTime
                        }
                    },
                    label = { Text(tech.title, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = DarkGreenAccent,
                        selectedLabelColor = LightMintGreen,
                        containerColor = DarkSecondaryBg,
                        labelColor = TextMuted
                    ),
                    modifier = Modifier.padding(horizontal = 3.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Duration selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf(1, 3, 5).forEach { min ->
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (sessionMinutes == min) DarkGreenAccent else DarkSecondaryBg,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (sessionMinutes == min) LightMintGreen else DarkBorder
                    ),
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    TextButton(
                        onClick = { if (!isRunning) sessionMinutes = min },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "$min Min",
                            color = if (sessionMinutes == min) LightMintGreen else TextSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Pulsing Circle Container
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(240.dp)
                .scale(if (isRunning) animatedScale else 1f)
                .clip(CircleShape)
                .background(
                    when (phase) {
                        "Inspire" -> PrimaryGreen.copy(alpha = 0.35f)
                        "Segure" -> AccentGold.copy(alpha = 0.35f)
                        else -> WarmSageMint.copy(alpha = 0.35f)
                    }
                )
                .border(
                    2.dp,
                    when (phase) {
                        "Inspire" -> LightMintGreen
                        "Segure" -> AccentGold
                        else -> PrimaryGreen
                    },
                    CircleShape
                )
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (isRunning) phase else "Pronto",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
                if (isRunning) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "$secondsInPhase s",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = when {
                !isRunning -> selectedTechnique.description
                phase == "Inspire" -> "Puxe o ar profundamente pelo nariz, expandindo o abdômen..."
                phase == "Segure" -> "Mantenha o ar nos pulmões com serenidade e tranquilidade..."
                else -> "Solte o ar lentamente pela boca, liberando todo o estresse acumulado..."
            },
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier.padding(horizontal = 20.dp),
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                isRunning = !isRunning
                if (isRunning) {
                    totalSecondsElapsed = 0
                    phase = "Inspire"
                    secondsInPhase = selectedTechnique.inhaleTime
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isRunning) AccentRed else PrimaryGreen
            ),
            modifier = Modifier
                .widthIn(min = 220.dp)
                .height(48.dp)
                .testTag("breathing_start_btn")
        ) {
            Text(
                text = if (isRunning) "Pausar Exercício" else "Iniciar Respiração",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}
