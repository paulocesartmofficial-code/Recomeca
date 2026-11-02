package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*

data class ContingencyStep(
    val stepNumber: Int,
    val title: String,
    val description: String,
    val rationale: String,
    val emoji: String
)

@Composable
fun ContingencyPlanScreen(
    onBack: () -> Unit,
    onAwardXp: (Int, String) -> Unit
) {
    val steps = remember {
        listOf(
            ContingencyStep(
                1,
                "Quebra de Ação Imediata (60 Segundos)",
                "Coloque o celular ou feche o computador imediatamente e afaste-se por pelo menos 1 minuto.",
                "O impulso compulsivo opera em piloto automático. O simples ato de afastar a mão do aparelho devolve o controle ao córtex pré-frontal.",
                "🛑"
            ),
            ContingencyStep(
                2,
                "Choque Térmico Suave no Sistema Nervoso",
                "Vá até a cozinha, beba um copo de água gelada devagar ou lave o rosto e os pulsos com água fria.",
                "O estímulo sensorial frio aciona o reflexo de mergulho dos mamíferos, diminuindo instantaneamente a frequência cardíaca e a ansiedade.",
                "💧"
            ),
            ContingencyStep(
                3,
                "Mudança Física de Ambiente",
                "Saia do cômodo onde você estava quando sentiu o gatilho. Vá para uma janela ou varanda e respire fundo.",
                "Gatilhos estão fortemente ancorados em locais físicos (como a cama ou o sofá à noite). Mudar de cômodo quebra o ciclo de estímulo-resposta.",
                "🚶"
            ),
            ContingencyStep(
                4,
                "Conexão com a Razão ou com Alguém",
                "Abra o SOS do app para 5 minutos de respiração ou envie uma mensagem rápida para sua pessoa de confiança.",
                "Falar a verdade ou admitir a vulnerabilidade destrói o poder do segredo que a compulsão precisa para crescer.",
                "🤝"
            )
        )
    }

    var completedSteps by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var isProtocolFinished by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Plano Anti-Gatilho 15 Minutos",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkGreenAccent,
                border = androidx.compose.foundation.BorderStroke(1.dp, LightMintGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = LightMintGreen, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Protocolo de Emergência: Execute os 4 passos em ordem antes de tomar qualquer atitude impulsiva.",
                        fontSize = 12.sp,
                        color = TextPrimary,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            steps.forEach { step ->
                val isDone = completedSteps.contains(step.stepNumber)

                RecomecaCard(
                    borderColor = if (isDone) LightMintGreen else DarkBorder,
                    backgroundColor = if (isDone) DarkCardElevated else DarkCard,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .clickable {
                            completedSteps = if (isDone) completedSteps - step.stepNumber else completedSteps + step.stepNumber
                            if (completedSteps.size == steps.size) {
                                isProtocolFinished = true
                                onAwardXp(60, "Protocolo de emergência concluído!")
                            }
                        }
                        .testTag("step_${step.stepNumber}")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(if (isDone) DarkGreenAccent else DarkSecondaryBg)
                                .border(1.dp, if (isDone) LightMintGreen else DarkBorder, CircleShape)
                        ) {
                            Text(text = if (isDone) "✓" else "${step.stepNumber}", fontWeight = FontWeight.Bold, color = if (isDone) LightMintGreen else TextMuted)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "${step.emoji} ${step.title}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = if (isDone) LightMintGreen else TextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = step.description,
                                fontSize = 13.sp,
                                color = TextPrimary,
                                lineHeight = 18.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = DarkSecondaryBg
                            ) {
                                Text(
                                    text = "Por que funciona: ${step.rationale}",
                                    fontSize = 11.sp,
                                    color = TextMuted,
                                    modifier = Modifier.padding(8.dp),
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    }
                }
            }

            if (isProtocolFinished) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = DarkGreenAccent,
                    border = androidx.compose.foundation.BorderStroke(1.dp, LightMintGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "🛡️ Protocolo Concluído com Sucesso! (+60 XP)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "A onda passou ou já perdeu quase toda a força. Você provou mais uma vez o seu autocontrole.",
                            fontSize = 13.sp,
                            color = TextPrimary,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
