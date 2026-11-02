package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FinancialSimulation
import com.example.data.model.UserProfile
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun FinanceScreen(
    userProfile: UserProfile,
    simulations: List<FinancialSimulation>,
    onOpenMenu: () -> Unit,
    onSaveSimulation: (daily: Double, daysWeek: Int, monthly: Double, target: String) -> Unit,
    onDeleteSimulation: (Long) -> Unit
) {
    var dailyBetText by remember { mutableStateOf(userProfile.avgDailyBet.toInt().toString()) }
    var daysPerWeek by remember { mutableIntStateOf(userProfile.daysPerWeekBet) }
    var selectedGoalType by remember { mutableStateOf("Reserva de Emergência") }

    val dailyBet = dailyBetText.toDoubleOrNull() ?: 0.0
    val weeklyEstimate = dailyBet * daysPerWeek
    val monthlyEstimate = (weeklyEstimate * 52) / 12.0
    val annualEstimate = weeklyEstimate * 52
    val threeYearsEstimate = annualEstimate * 3
    val fiveYearsEstimate = annualEstimate * 5

    val ptBr = Locale("pt", "BR")
    val currency = NumberFormat.getCurrencyInstance(ptBr)

    val goalOptions = listOf(
        "Quitar Dívidas",
        "Reserva de Emergência",
        "Comprar Celular Novo",
        "Fazer uma Viagem",
        "Reformar a Casa",
        "Objetivos Pessoais"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Calculadora Financeira",
            onMenuClick = onOpenMenu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Notice
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkCard,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = LightMintGreen,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Importante: Dinheiro preservado é o recurso real que deixa de sair do seu bolso. Não confunda com promessa de lucro ou ganho garantido.",
                        fontSize = 11.sp,
                        color = TextSecondary,
                        lineHeight = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Inputs Card
            RecomecaCard(
                borderColor = PrimaryGreen,
                backgroundColor = DarkCard
            ) {
                Text(
                    text = "SEUS PARÂMETROS DE APOSTAS ANTERIORES",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = dailyBetText,
                    onValueChange = { dailyBetText = it },
                    label = { Text("Valor médio apostado por dia (R$)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("finance_daily_input")
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Dias de apostas por semana: $daysPerWeek dias",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Slider(
                    value = daysPerWeek.toFloat(),
                    onValueChange = { daysPerWeek = it.toInt() },
                    valueRange = 1f..7f,
                    steps = 5,
                    colors = SliderDefaults.colors(
                        thumbColor = LightMintGreen,
                        activeTrackColor = PrimaryGreen,
                        inactiveTrackColor = DarkBorder
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Objetivo ao Preservar este Valor:",
                    color = TextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Goal chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    goalOptions.take(3).forEach { option ->
                        FilterChip(
                            selected = selectedGoalType == option,
                            onClick = { selectedGoalType = option },
                            label = { Text(option, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = DarkGreenAccent,
                                selectedLabelColor = LightMintGreen,
                                containerColor = DarkSecondaryBg,
                                labelColor = TextSecondary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Projection Results Card
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkSecondaryBg
            ) {
                Text(
                    text = "PROJEÇÃO DE DINHEIRO PRESERVADO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ProjectionColumn(title = "Semanal", value = currency.format(weeklyEstimate))
                    ProjectionColumn(title = "Mensal", value = currency.format(monthlyEstimate))
                    ProjectionColumn(title = "Anual", value = currency.format(annualEstimate))
                }

                HorizontalDivider(color = DarkBorder, thickness = 1.dp, modifier = Modifier.padding(vertical = 12.dp))

                // Long term projections
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Em 3 Anos:", fontSize = 12.sp, color = TextSecondary)
                        Text(
                            text = currency.format(threeYearsEstimate),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(text = "Em 5 Anos:", fontSize = 12.sp, color = TextSecondary)
                        Text(
                            text = currency.format(fiveYearsEstimate),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = AccentGold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Comparison Bar Graph
                Text(
                    text = "Comparativo Visual (Anual)",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Perdido:", fontSize = 11.sp, color = AccentRed, modifier = Modifier.width(60.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(AccentRed.copy(alpha = 0.8f))
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "No Bolso:", fontSize = 11.sp, color = LightMintGreen, modifier = Modifier.width(60.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(LightMintGreen)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            PrimaryGradientButton(
                text = "Salvar Simulação",
                onClick = {
                    onSaveSimulation(dailyBet, daysPerWeek, monthlyEstimate, selectedGoalType)
                },
                icon = Icons.Default.BookmarkAdd,
                testTag = "finance_save_sim_btn"
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Saved Simulations list
            if (simulations.isNotEmpty()) {
                Text(
                    text = "SIMULAÇÕES SALVAS",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                simulations.forEach { sim ->
                    RecomecaCard(
                        borderColor = DarkBorder,
                        backgroundColor = DarkCard,
                        modifier = Modifier.padding(bottom = 10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = sim.targetObjective,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = TextPrimary
                                )
                                Text(
                                    text = "R$ ${"%.0f".format(sim.dailyBet)}/dia (${sim.weeklyDays}x na semana) • Anual: ${currency.format(sim.annualEstimate)}",
                                    fontSize = 12.sp,
                                    color = LightMintGreen
                                )
                            }
                            IconButton(onClick = { onDeleteSimulation(sim.id) }) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline,
                                    contentDescription = "Excluir",
                                    tint = TextMuted
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProjectionColumn(title: String, value: String) {
    Column {
        Text(text = title, fontSize = 11.sp, color = TextSecondary)
        Text(
            text = value,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
    }
}
