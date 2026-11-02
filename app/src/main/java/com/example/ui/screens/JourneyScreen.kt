package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Achievement
import com.example.data.model.DailyCheckIn
import com.example.data.model.UserProfile
import com.example.data.seed.GameQuestionsData
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import java.util.Calendar

@Composable
fun JourneyScreen(
    userProfile: UserProfile,
    checkIns: List<DailyCheckIn>,
    onOpenMenu: () -> Unit,
    onResetQuitDate: () -> Unit
) {
    var showResetDialog by remember { mutableStateOf(false) }

    val achievements = remember(userProfile.daysClean, userProfile.xp) {
        GameQuestionsData.defaultAchievements.map { ach ->
            val unlocked = userProfile.daysClean >= ach.requiredDaysClean || userProfile.xp >= ach.requiredXp
            ach.copy(isUnlocked = unlocked)
        }
    }

    // Days in current month for visual calendar
    val calendar = Calendar.getInstance()
    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Jornada e Evolução",
            onMenuClick = onOpenMenu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Compassionate Sobriety Counter
            RecomecaCard(
                borderColor = PrimaryGreen,
                backgroundColor = DarkCard
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "CICLO ATUAL",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen
                        )
                        Row(verticalAlignment = Alignment.Bottom) {
                            Text(
                                text = "${userProfile.daysClean}",
                                fontSize = 38.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextPrimary
                            )
                            Text(
                                text = " dias consecutivos",
                                fontSize = 14.sp,
                                color = LightMintGreen,
                                modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = { showResetDialog = true },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
                        border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                        modifier = Modifier.testTag("journey_reset_counter_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Recomeçar", fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Calendar Visualizer
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkSecondaryBg
            ) {
                Text(
                    text = "CALENDÁRIO DO MÊS ATUAL",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
                )
                Text(
                    text = "Verde = Dias limpos • Toque para visualizar",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Month days grid (7 columns)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("D", "S", "T", "Q", "Q", "S", "S").forEach { d ->
                        Text(
                            text = d,
                            color = TextMuted,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(32.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Render first 28-31 days in chunks of 7
                val weeks = (daysInMonth + 6) / 7
                for (w in 0 until weeks) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (d in 1..7) {
                            val dayNum = w * 7 + d
                            if (dayNum <= daysInMonth) {
                                val isClean = dayNum <= currentDay
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (isClean) DarkGreenAccent else DarkCard)
                                        .border(
                                            1.dp,
                                            if (dayNum == currentDay) LightMintGreen else Color.Transparent,
                                            CircleShape
                                        )
                                ) {
                                    Text(
                                        text = "$dayNum",
                                        fontSize = 11.sp,
                                        fontWeight = if (isClean) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isClean) LightMintGreen else TextMuted
                                    )
                                }
                            } else {
                                Spacer(modifier = Modifier.size(32.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Achievements Gallery
            Text(
                text = "CONQUISTAS E MARCOS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            achievements.forEach { ach ->
                RecomecaCard(
                    borderColor = if (ach.isUnlocked) PrimaryGreen else DarkBorder,
                    backgroundColor = if (ach.isUnlocked) DarkCard else DarkSecondaryBg,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(if (ach.isUnlocked) DarkGreenAccent else DarkBorder)
                        ) {
                            Text(text = ach.iconEmoji, fontSize = 22.sp)
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = ach.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = if (ach.isUnlocked) TextPrimary else TextMuted
                                )
                                if (ach.isUnlocked) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.CheckCircle,
                                        contentDescription = "Desbloqueado",
                                        tint = LightMintGreen,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                            Text(
                                text = ach.description,
                                fontSize = 12.sp,
                                color = if (ach.isUnlocked) TextSecondary else TextMuted
                            )
                        }

                        Text(
                            text = "${ach.requiredDaysClean}d",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (ach.isUnlocked) LightMintGreen else TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Compassionate Reset Dialog
    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            containerColor = DarkSecondaryBg,
            title = {
                Text(
                    text = "Recomeçar o Contador?",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Column {
                    Text(
                        text = "Um tropeço nunca apaga os dias em que você venceu e o dinheiro que já preservou.",
                        color = TextPrimary,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Todas as suas conquistas, medalhas e registros do diário continuarão intactos. Você está apenas iniciando um novo ciclo com mais sabedoria.",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onResetQuitDate()
                        showResetDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Sim, Novo Ciclo", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showResetDialog = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        )
    }
}
