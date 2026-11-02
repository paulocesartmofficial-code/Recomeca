package com.example.ui.screens.games

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
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
import com.example.ui.components.RecomecaCard
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.random.Random

data class MathProblem(
    val equation: String,
    val correctVal: Int,
    val options: List<Int>
)

@Composable
fun MathChallengeGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    fun generateProblem(): MathProblem {
        val type = Random.nextInt(3) // 0: add, 1: sub, 2: mul
        val a = Random.nextInt(12, 60)
        val b = Random.nextInt(5, 40)
        val (eq, ans) = when (type) {
            0 -> Pair("$a + $b", a + b)
            1 -> Pair("${a + b} - $b", a)
            else -> {
                val m1 = Random.nextInt(4, 12)
                val m2 = Random.nextInt(4, 12)
                Pair("$m1 × $m2", m1 * m2)
            }
        }
        val distractors = setOf(ans + 2, ans - 3, ans + 10, ans - 5).filter { it != ans }.take(3)
        val opts = (distractors + ans).shuffled()
        return MathProblem(eq, ans, opts)
    }

    var roundNumber by remember { mutableIntStateOf(1) }
    var currentProblem by remember { mutableStateOf(generateProblem()) }
    var score by remember { mutableIntStateOf(0) }
    var streak by remember { mutableIntStateOf(0) }
    var maxStreak by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }

    // Dynamic 30s session timer
    var timeLeftSeconds by remember { mutableIntStateOf(30) }
    var isTimerActive by remember { mutableStateOf(true) }

    LaunchedEffect(isTimerActive) {
        while (isTimerActive && timeLeftSeconds > 0) {
            delay(1000)
            timeLeftSeconds--
        }
        if (timeLeftSeconds <= 0) {
            isTimerActive = false
        }
    }

    val timerProgress by animateFloatAsState(
        targetValue = timeLeftSeconds / 30f,
        label = "math_timer"
    )

    if (roundNumber > 10 || timeLeftSeconds <= 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🧠 Treino Mental Relâmpago Concluído!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LightMintGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Pontuação: $score pontos • Maior Combo: x$maxStreak",
                fontSize = 15.sp,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onGameComplete(score, roundNumber, 30 - timeLeftSeconds) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Finalizar e Salvar XP", color = Color.White)
            }
        }
        return
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Cálculo $roundNumber/10 • $timeLeftSeconds s",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGold
            )

            if (streak > 1) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AccentGold.copy(alpha = 0.2f),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentGold)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Bolt, contentDescription = null, tint = AccentGold, modifier = Modifier.size(12.dp))
                        Text(text = "Combo x$streak", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AccentGold)
                    }
                }
            }

            Text(
                text = "$score pts",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = LightMintGreen
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        LinearProgressIndicator(
            progress = { timerProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = if (timeLeftSeconds > 8) LightMintGreen else AccentRed,
            trackColor = DarkSecondaryBg
        )

        Spacer(modifier = Modifier.height(14.dp))

        RecomecaCard(
            borderColor = PrimaryGreen,
            backgroundColor = DarkCardElevated
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = "${currentProblem.equation} = ?",
                    fontSize = 38.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2x2 Options Grid
        Column {
            for (row in 0 until 2) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (col in 0 until 2) {
                        val index = row * 2 + col
                        val opt = currentProblem.options[index]
                        val isCorrect = opt == currentProblem.correctVal
                        val isSelected = selectedOption == opt

                        val bgColor = when {
                            !isAnswered -> DarkSecondaryBg
                            isCorrect -> DarkGreenAccent
                            isSelected -> AccentRed.copy(alpha = 0.3f)
                            else -> DarkSecondaryBg
                        }

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = bgColor,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isAnswered && isCorrect) LightMintGreen else DarkBorder
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .height(56.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable(enabled = !isAnswered) {
                                    selectedOption = opt
                                    isAnswered = true
                                    if (isCorrect) {
                                        streak++
                                        if (streak > maxStreak) maxStreak = streak
                                        score += 15 + (streak * 5)
                                    } else {
                                        streak = 0
                                    }
                                }
                                .testTag("math_opt_$index")
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = "$opt",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = TextPrimary
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isAnswered) {
            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = {
                    isAnswered = false
                    selectedOption = null
                    roundNumber++
                    currentProblem = generateProblem()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Próximo Cálculo →", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
