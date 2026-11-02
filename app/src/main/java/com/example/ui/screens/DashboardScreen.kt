package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.DailyCheckIn
import com.example.data.model.FinancialGoal
import com.example.data.model.UserProfile
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.Screen
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun DashboardScreen(
    userProfile: UserProfile,
    checkIns: List<DailyCheckIn>,
    goals: List<FinancialGoal>,
    onOpenMenu: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onSubmitCheckIn: (didBet: Boolean, mood: Int, urgeLevel: Int, note: String) -> Unit,
    onOpenGame: (String) -> Unit
) {
    var showCheckInDialog by remember { mutableStateOf(false) }
    var selectedMood by remember { mutableIntStateOf(4) }
    var selectedUrge by remember { mutableIntStateOf(1) }
    var checkInNote by remember { mutableStateOf("") }
    var checkInDidBet by remember { mutableStateOf(false) }

    val ptBr = Locale("pt", "BR")
    val currencyFormat = NumberFormat.getCurrencyInstance(ptBr)

    // Time-of-day greeting
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greetingTime = when (hour) {
        in 5..11 -> "Bom dia"
        in 12..17 -> "Boa tarde"
        else -> "Boa noite"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Recomeça",
            onMenuClick = onOpenMenu,
            actions = {
                // XP and Level pill
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = DarkCardElevated,
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Bolt,
                            contentDescription = null,
                            tint = AccentGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${userProfile.xp} XP • Nível ${userProfile.level}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Warm and Cozy Greeting Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "$greetingTime, ${userProfile.name} 🌿",
                        fontWeight = FontWeight.Bold,
                        fontSize = 21.sp,
                        color = TextPrimary
                    )
                    Text(
                        text = "Você está seguro aqui. Respire fundo e viva o momento presente.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )
                }

                if (userProfile.isVip) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = AccentGold.copy(alpha = 0.15f),
                        border = androidx.compose.foundation.BorderStroke(1.dp, AccentGold)
                    ) {
                        Text(
                            text = "MEMBRO VIP",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AccentGold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // SOS Banner Primary Action
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = AccentRed.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, AccentRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(Screen.SOS) }
                    .testTag("dashboard_sos_btn")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(AccentRed)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Emergency,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "ESTOU COM VONTADE DE APOSTAR",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Color.White
                        )
                        Text(
                            text = "Pausa de 5 min, respiração e acolhimento sem julgamento",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = AccentRed,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Cozy Sanctuary Ambient Sounds Quick Banner
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = DarkCardElevated,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigate(Screen.AMBIENT_SOUNDS) }
                    .testTag("dashboard_ambient_btn")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkGreenAccent)
                    ) {
                        Text(text = "🌧️", fontSize = 22.sp)
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Santuário de Sons & Calma",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(shape = RoundedCornerShape(4.dp), color = DarkGreenAccent) {
                                Text("NOVO", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = LightMintGreen, modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                            }
                        }
                        Text(
                            text = "Chuva suave, lareira e ondas para relaxar a mente",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.Headphones,
                        contentDescription = null,
                        tint = LightMintGreen,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Sobriety & Saved Money Card (Cozy Warm Border)
            RecomecaCard(
                borderColor = PrimaryGreen,
                backgroundColor = DarkCardElevated
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "TEMPO EM RECUPERAÇÃO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "${userProfile.daysClean}",
                                fontSize = 42.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary
                            )
                            Text(
                                text = if (userProfile.daysClean == 1L) " dia limpo" else " dias limpos",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = LightMintGreen,
                                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
                            )
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(DarkGreenAccent)
                            .border(1.dp, LightMintGreen, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = null,
                            tint = LightMintGreen,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }

                HorizontalDivider(
                    color = DarkBorder,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Dinheiro Preservado no Seu Bolso",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Text(
                            text = currencyFormat.format(userProfile.estimatedMoneySaved),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen
                        )
                    }

                    OutlinedButton(
                        onClick = { onNavigate(Screen.INVESTMENT_SIMULATOR) },
                        shape = RoundedCornerShape(10.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextPrimary),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = AccentGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ver Rendimento", fontSize = 11.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Highlight Motto Card
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = DarkSecondaryBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "\"Cada dia é uma oportunidade de recomeçar.\"",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Access Pillars: Plano Anti-Gatilho & Mural de Força
            Row(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = DarkCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigate(Screen.CONTINGENCY_PLAN) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "🛡️", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Plano 15 Min", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                            Text(text = "Anti-recaída", fontSize = 11.sp, color = TextMuted)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = DarkCard,
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onNavigate(Screen.AFFIRMATIONS) }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "✨", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = "Mural de Força", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextPrimary)
                            Text(text = "Acolhimento diário", fontSize = 11.sp, color = TextMuted)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Daily Check-in Card
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkCardElevated
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = PrimaryGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Check-in Diário de Hoje",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "Ganhe 50 XP e monitore seus gatilhos",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }

                    Button(
                        onClick = { showCheckInDialog = true },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                        modifier = Modifier.testTag("dashboard_checkin_btn")
                    ) {
                        Text("Registrar", fontSize = 13.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Desafio do Dia
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkSecondaryBg
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(DarkGreenAccent)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SelfImprovement,
                            contentDescription = null,
                            tint = LightMintGreen,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "DESAFIO DO DIA",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen
                        )
                        Text(
                            text = "Sessão de Respiração Guiada (3 min)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "Oxigene o cérebro e alivie a tensão acumulada do dia.",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = { onOpenGame("breathing") },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Iniciar Exercício →",
                                color = LightMintGreen,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Quick Access to Games
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkCard
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Central de Jogos Mentais (10 Jogos)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = "Sudoku, Memória, Caça-Palavras, Quiz e mais",
                            fontSize = 12.sp,
                            color = TextSecondary
                        )
                    }
                    IconButton(onClick = { onNavigate(Screen.GAMES_HUB) }) {
                        Icon(
                            imageVector = Icons.Default.SportsEsports,
                            contentDescription = "Jogos",
                            tint = LightMintGreen
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Daily Check-In Dialog
    if (showCheckInDialog) {
        AlertDialog(
            onDismissRequest = { showCheckInDialog = false },
            containerColor = DarkSecondaryBg,
            title = {
                Text(
                    text = "Check-in Diário",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "Você fez alguma aposta hoje?",
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedButton(
                            onClick = { checkInDidBet = false },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (!checkInDidBet) DarkGreenAccent else Color.Transparent
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (!checkInDidBet) LightMintGreen else DarkBorder
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Não apostei! (Vitória)", fontSize = 12.sp, color = TextPrimary)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedButton(
                            onClick = { checkInDidBet = true },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (checkInDidBet) AccentRed.copy(alpha = 0.2f) else Color.Transparent
                            ),
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (checkInDidBet) AccentRed else DarkBorder
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Tive um tropeço", fontSize = 12.sp, color = TextSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Como está seu humor hoje? (1 a 5)",
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary,
                        fontSize = 14.sp
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf("1 🌧️", "2 ⛅", "3 😐", "4 🙂", "5 ☀️").forEachIndexed { idx, label ->
                            val score = idx + 1
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (selectedMood == score) DarkGreenAccent else DarkCard)
                                    .border(
                                        1.dp,
                                        if (selectedMood == score) LightMintGreen else DarkBorder,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedMood = score }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(text = label, fontSize = 12.sp, color = TextPrimary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = checkInNote,
                        onValueChange = { checkInNote = it },
                        label = { Text("Nota rápida (opcional)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = DarkBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onSubmitCheckIn(checkInDidBet, selectedMood, selectedUrge, checkInNote)
                        showCheckInDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Salvar Check-in", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showCheckInDialog = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        )
    }
}
