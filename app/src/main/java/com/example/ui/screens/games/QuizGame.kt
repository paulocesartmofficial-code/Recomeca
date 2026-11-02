package com.example.ui.screens.games

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Timer
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
import kotlinx.coroutines.delay

@Composable
fun QuizGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    val questions = remember { GameQuestionsData.quizQuestions.shuffled().take(10) }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var streak by remember { mutableIntStateOf(0) }
    var isAnswered by remember { mutableStateOf(false) }

    // Dynamic 15-second countdown timer per question
    var timeLeftSeconds by remember { mutableIntStateOf(15) }

    LaunchedEffect(currentIndex, isAnswered) {
        if (!isAnswered && currentIndex < questions.size) {
            timeLeftSeconds = 15
            while (timeLeftSeconds > 0 && !isAnswered) {
                delay(1000)
                timeLeftSeconds--
            }
            if (timeLeftSeconds == 0 && !isAnswered) {
                isAnswered = true
                streak = 0
            }
        }
    }

    val currentQ = questions.getOrNull(currentIndex)

    if (currentQ == null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "🏆 Quiz Concluído!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LightMintGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Sua pontuação final: $score pontos",
                fontSize = 15.sp,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onGameComplete(score, 10, 60)
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Salvar Pontuação e Voltar", color = Color.White)
            }
        }
        return
    }

    val timerProgress by animateFloatAsState(
        targetValue = timeLeftSeconds / 15f,
        label = "quiz_timer"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(6.dp),
                color = DarkGreenAccent
            ) {
                Text(
                    text = currentQ.category,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
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
                            Text(text = "Streak x$streak", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AccentGold)
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = "$score pts • ${currentIndex + 1}/${questions.size}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Timer progress bar
        LinearProgressIndicator(
            progress = { timerProgress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = if (timeLeftSeconds > 5) LightMintGreen else AccentRed,
            trackColor = DarkSecondaryBg
        )

        Spacer(modifier = Modifier.height(12.dp))

        RecomecaCard(
            borderColor = DarkBorder,
            backgroundColor = DarkCard
        ) {
            Text(
                text = currentQ.question,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = TextPrimary,
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Options
        currentQ.options.forEachIndexed { idx, optionText ->
            val isCorrect = idx == currentQ.correctIndex
            val isSelected = selectedOptionIndex == idx

            val bgColor = when {
                !isAnswered -> DarkSecondaryBg
                isCorrect -> DarkGreenAccent
                isSelected -> AccentRed.copy(alpha = 0.25f)
                else -> DarkSecondaryBg
            }

            val borderColor = when {
                !isAnswered -> DarkBorder
                isCorrect -> LightMintGreen
                isSelected -> AccentRed
                else -> DarkBorder
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = bgColor,
                border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(enabled = !isAnswered) {
                        selectedOptionIndex = idx
                        isAnswered = true
                        if (idx == currentQ.correctIndex) {
                            streak++
                            val timeBonus = timeLeftSeconds * 2
                            score += 15 + timeBonus + (streak * 5)
                        } else {
                            streak = 0
                        }
                    }
                    .testTag("quiz_option_$idx")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${('A' + idx)}. ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = if (isAnswered && isCorrect) LightMintGreen else TextMuted
                    )
                    Text(
                        text = optionText,
                        fontSize = 14.sp,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Didactic explanation
        if (isAnswered) {
            Spacer(modifier = Modifier.height(10.dp))
            RecomecaCard(
                borderColor = LightMintGreen,
                backgroundColor = DarkSecondaryBg
            ) {
                Text(
                    text = "EXPLICAÇÃO DIDÁTICA:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = currentQ.explanation,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    isAnswered = false
                    selectedOptionIndex = null
                    currentIndex++
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("quiz_next_btn")
            ) {
                Text("Próxima Pergunta →", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
