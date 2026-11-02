package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.Screen
import kotlinx.coroutines.delay

@Composable
fun SosScreen(
    userProfile: UserProfile,
    onOpenMenu: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onOpenGame: (String) -> Unit
) {
    val context = LocalContext.current

    // 5-minute pause timer (300 seconds)
    var timeLeftSeconds by remember { mutableIntStateOf(300) }
    var isTimerRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isTimerRunning) {
        while (isTimerRunning && timeLeftSeconds > 0) {
            delay(1000)
            timeLeftSeconds--
        }
    }

    var selectedTrigger by remember { mutableStateOf<String?>(null) }
    var showTrustedContactDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Espaço SOS de Emergência",
            onMenuClick = onOpenMenu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Welcoming non-judgmental banner
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = AccentRed.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentRed),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = AccentRed,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Você não está sozinho. Respire fundo.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "A vontade de apostar parece insuportável no pico, mas ela é como uma onda: atinge o ápice e se dissipa em poucos minutos se você apenas esperar. Estamos com você.",
                        fontSize = 12.sp,
                        color = TextPrimary,
                        lineHeight = 17.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5-minute pause timer card
            RecomecaCard(
                borderColor = PrimaryGreen,
                backgroundColor = DarkCard
            ) {
                Text(
                    text = "PAUSA DE 5 MINUTOS PARA INTERROMPER O IMPULSO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Text(
                    text = "Dê a si mesmo 300 segundos antes de qualquer decisão.",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                val minutes = timeLeftSeconds / 60
                val seconds = timeLeftSeconds % 60
                val timeString = "%02d:%02d".format(minutes, seconds)

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = timeString,
                        fontSize = 54.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (timeLeftSeconds > 0) LightMintGreen else AccentGold
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { isTimerRunning = !isTimerRunning },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isTimerRunning) AccentRed else PrimaryGreen
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("sos_pause_timer_btn")
                    ) {
                        Text(
                            text = if (isTimerRunning) "Pausar Temporizador" else "Iniciar Pausa de 5 Min",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    OutlinedButton(
                        onClick = {
                            isTimerRunning = false
                            timeLeftSeconds = 300
                        },
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder)
                    ) {
                        Text("Reset", color = TextSecondary)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fast Immediate Distraction Buttons
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onOpenGame("breathing") },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkGreenAccent),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("sos_breathing_btn")
                ) {
                    Icon(imageVector = Icons.Default.Air, contentDescription = null, tint = LightMintGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Respiração 4-7-8", fontSize = 12.sp, color = LightMintGreen, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onOpenGame("memory") },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkCard),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("sos_game_distract_btn")
                ) {
                    Icon(imageVector = Icons.Default.SportsEsports, contentDescription = null, tint = AccentGold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Jogo Distração", fontSize = 12.sp, color = TextPrimary, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Emotional triggers selection
            Text(
                text = "O QUE VOCÊ ESTÁ SENTINDO AGORA?",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            TriggerButton(
                label = "Estou ansioso(a)",
                isSelected = selectedTrigger == "ansioso",
                onClick = { selectedTrigger = "ansioso" }
            )
            TriggerButton(
                label = "Estou com vontade de recuperar dinheiro perdido",
                isSelected = selectedTrigger == "recuperar",
                onClick = { selectedTrigger = "recuperar" }
            )
            TriggerButton(
                label = "Estou entediado(a) e com a mente vazia",
                isSelected = selectedTrigger == "tedio",
                onClick = { selectedTrigger = "tedio" }
            )
            TriggerButton(
                label = "Estou triste ou frustrado(a)",
                isSelected = selectedTrigger == "triste",
                onClick = { selectedTrigger = "triste" }
            )
            TriggerButton(
                label = "Estou com problemas financeiros urgentes",
                isSelected = selectedTrigger == "financeiro",
                onClick = { selectedTrigger = "financeiro" }
            )

            // Trigger advice card
            if (selectedTrigger != null) {
                Spacer(modifier = Modifier.height(10.dp))
                RecomecaCard(
                    borderColor = LightMintGreen,
                    backgroundColor = DarkSecondaryBg
                ) {
                    Text(
                        text = when (selectedTrigger) {
                            "ansioso" -> "Ansiedade: Seu corpo está em modo de alerta. Beba um copo de água gelada, lave o rosto e faça 5 respirações lentas soltando o ar pela boca. A aposta pode aumentar a tensão e trazer novas preocupações depois da rodada."
                            "recuperar" -> "Recuperar perdas: Aceite o custo afundado. O dinheiro que você perdeu no passado é o preço do aprendizado, mas apostar hoje só geraria uma dívida ainda maior. Cada real no seu bolso agora é uma vitória que você protege."
                            "tedio" -> "Tédio: Sua mente procura dopamina rápida. Abra agora a Central de Jogos do Recomeça e conclua um desafio de Sudoku ou Lógica para ocupar a atenção com uma atividade sem envolver apostas."
                            "triste" -> "Tristeza: Apostar funciona como uma anestesia falsa que cobra juros pesados logo depois. Ligue para alguém querido ou escreva abertamente no seu Diário Pessoal do app."
                            "financeiro" -> "Aperto financeiro: Apostar não é uma solução segura para contas e pode ampliar o prejuízo. Dívidas podem ser organizadas e renegociadas com apoio; procure orientação financeira confiável antes de assumir novos compromissos."
                            else -> ""
                        },
                        fontSize = 13.sp,
                        color = TextPrimary,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Trusted Contact & Official Resources
            Text(
                text = "REDE DE APOIO E CONTATOS HUMANOS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Trusted Contact
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkCard
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Pessoa de Confiança Cadastrada",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        Text(
                            text = if (userProfile.emergencyContactPhone.isBlank()) {
                                "Adicione uma pessoa de confiança em Configurações."
                            } else {
                                "${userProfile.emergencyContactName} • ${userProfile.emergencyContactPhone}"
                            },
                            fontSize = 12.sp,
                            color = LightMintGreen
                        )
                    }

                    if (userProfile.emergencyContactPhone.isNotBlank()) {
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:${userProfile.emergencyContactPhone}")
                                }
                                try {
                                    context.startActivity(intent)
                                } catch (_: Exception) {}
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Ligar",
                                tint = LightMintGreen
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Official Free Hotlines
            SupportResourceItem(
                name = "CVV - Centro de Valorização da Vida",
                detail = "Disque 188 • Atendimento 24h gratuito e anônimo por voz",
                actionLabel = "Ligar 188",
                onAction = {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:188"))
                    try { context.startActivity(intent) } catch (_: Exception) {}
                }
            )

            SupportResourceItem(
                name = "Jogadores Anônimos do Brasil (JA)",
                detail = "Reuniões presenciais e online gratuitas em todo o Brasil",
                actionLabel = "Site Oficial",
                onAction = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://jogadoresanonimos.com.br"))
                    try { context.startActivity(intent) } catch (_: Exception) {}
                }
            )

            SupportResourceItem(
                name = "CAPS / SUS - Saúde Pública",
                detail = "Centros de Atenção Psicossocial gratuitos com psicólogos e psiquiatras",
                actionLabel = "Informações",
                onAction = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gov.br/saude/pt-br/assuntos/saude-de-a-a-z/s/saude-mental"))
                    try { context.startActivity(intent) } catch (_: Exception) {}
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TriggerButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isSelected) DarkGreenAccent else DarkCard,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) LightMintGreen else DarkBorder
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp)
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isSelected) Icons.Default.RadioButtonChecked else Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                tint = if (isSelected) LightMintGreen else TextMuted,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = label,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = TextPrimary
            )
        }
    }
}

@Composable
private fun SupportResourceItem(
    name: String,
    detail: String,
    actionLabel: String,
    onAction: () -> Unit
) {
    RecomecaCard(
        borderColor = DarkBorder,
        backgroundColor = DarkSecondaryBg,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextPrimary
                )
                Text(
                    text = detail,
                    fontSize = 11.sp,
                    color = TextSecondary
                )
            }

            OutlinedButton(
                onClick = onAction,
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, LightMintGreen),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(text = actionLabel, fontSize = 11.sp, color = LightMintGreen)
            }
        }
    }
}
