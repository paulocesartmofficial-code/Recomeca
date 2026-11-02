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
fun LogicPuzzleGame(
    onGameComplete: (score: Int, moves: Int, duration: Int) -> Unit
) {
    val puzzles = remember { GameQuestionsData.logicPuzzles }
    var currentIndex by remember { mutableIntStateOf(0) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var isAnswered by remember { mutableStateOf(false) }
    var correctCount by remember { mutableIntStateOf(0) }

    val current = puzzles.getOrNull(currentIndex)

    if (current == null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "⚡ Desafios de Lógica Concluídos!",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = LightMintGreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Você acertou $correctCount de ${puzzles.size} enigmas de raciocínio.",
                fontSize = 14.sp,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onGameComplete(correctCount * 150, puzzles.size, 90) },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
            ) {
                Text("Finalizar e Ganhar XP", color = Color.White)
            }
        }
        return
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Desafio ${currentIndex + 1} de ${puzzles.size}: ${current.title}",
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = AccentGold
        )

        Spacer(modifier = Modifier.height(10.dp))

        RecomecaCard(
            borderColor = PrimaryGreen,
            backgroundColor = DarkCard
        ) {
            Text(
                text = current.prompt,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                lineHeight = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        current.options.forEachIndexed { idx, opt ->
            val isCorrect = idx == current.correctIndex
            val isSelected = selectedOption == idx

            val bgColor = when {
                !isAnswered -> DarkSecondaryBg
                isCorrect -> DarkGreenAccent
                isSelected -> AccentRed.copy(alpha = 0.25f)
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
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(enabled = !isAnswered) {
                        selectedOption = idx
                        isAnswered = true
                        if (isCorrect) correctCount++
                    }
                    .testTag("logic_opt_$idx")
            ) {
                Text(
                    text = opt,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }

        if (isAnswered) {
            Spacer(modifier = Modifier.height(10.dp))
            RecomecaCard(
                borderColor = LightMintGreen,
                backgroundColor = DarkSecondaryBg
            ) {
                Text(
                    text = "LÓGICA DO ENIGMA:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = current.explanation,
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    isAnswered = false
                    selectedOption = null
                    currentIndex++
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Próximo Enigma →", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}
