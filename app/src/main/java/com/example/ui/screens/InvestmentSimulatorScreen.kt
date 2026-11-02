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
import com.example.data.model.UserProfile
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

@Composable
fun InvestmentSimulatorScreen(
    userProfile: UserProfile,
    onBack: () -> Unit
) {
    var monthlyDepositText by remember {
        val initialMonthly = if (userProfile.avgDailyBet > 0) {
            (userProfile.avgDailyBet * userProfile.daysPerWeekBet * 4.33).toInt().toString()
        } else "0"
        mutableStateOf(initialMonthly)
    }

    val monthlyDeposit = monthlyDepositText.toDoubleOrNull() ?: 0.0
    val annualRate = 0.1075 // Cenário hipotético fixo de 10,75% a.a.; não é taxa de mercado atual.
    val monthlyRate = (1.0 + annualRate).pow(1.0 / 12.0) - 1.0

    fun calculateFutureValue(months: Int): Double {
        var total = 0.0
        for (m in 1..months) {
            total = (total + monthlyDeposit) * (1.0 + monthlyRate)
        }
        return total
    }

    val fv1Year = calculateFutureValue(12)
    val totalInvested1 = monthlyDeposit * 12
    val interest1 = fv1Year - totalInvested1

    val fv3Years = calculateFutureValue(36)
    val totalInvested3 = monthlyDeposit * 36
    val interest3 = fv3Years - totalInvested3

    val fv5Years = calculateFutureValue(60)
    val totalInvested5 = monthlyDeposit * 60
    val interest5 = fv5Years - totalInvested5

    val monthlyPassiveIncome5Years = fv5Years * monthlyRate

    val ptBr = Locale("pt", "BR")
    val currency = NumberFormat.getCurrencyInstance(ptBr)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Simulador de Juros Compostos",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkSecondaryBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.TrendingUp, contentDescription = null, tint = LightMintGreen, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Explore um cenário hipotético de juros compostos para visualizar como aportes regulares podem evoluir. A taxa usada é fixa e ilustrativa, não representa a taxa atual do CDI ou do Tesouro Selic.",
                        fontSize = 12.sp,
                        color = TextPrimary,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Monthly input
            RecomecaCard(
                borderColor = PrimaryGreen,
                backgroundColor = DarkCard
            ) {
                Text(
                    text = "VALOR MENSAL PRESERVADO DAS APOSTAS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = monthlyDepositText,
                    onValueChange = { monthlyDepositText = it },
                    label = { Text("Aporte Mensal (R$)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("investment_input")
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 5-Year Milestone Highlight Card
            RecomecaCard(
                borderColor = AccentGold,
                backgroundColor = DarkCardElevated
            ) {
                Text(
                    text = "SIMULAÇÃO ILUSTRATIVA EM 5 ANOS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = currency.format(fv5Years),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = LightMintGreen
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Sendo ${currency.format(totalInvested5)} do seu próprio bolso poupado e ${currency.format(interest5)} de juros puros acumulados!",
                    fontSize = 13.sp,
                    color = TextPrimary,
                    lineHeight = 18.sp
                )

                HorizontalDivider(color = DarkBorder, thickness = 1.dp, modifier = Modifier.padding(vertical = 10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Renda mensal estimada no cenário:", fontSize = 11.sp, color = TextMuted)
                        Text(
                            text = "+ ${currency.format(monthlyPassiveIncome5Years)} / mês",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentGold
                        )
                    }
                    Text(
                        text = "Cenário hipotético",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Atenção: projeção matemática com taxa fixa hipotética de 10,75% ao ano. Não considera impostos, taxas, inflação ou mudanças nas taxas. Investimentos têm riscos e resultados não são garantidos. Isto não é recomendação financeira.",
                fontSize = 11.sp,
                color = TextMuted,
                lineHeight = 16.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Comparative Projections
            Text(
                text = "PROJEÇÕES INTERMEDIÁRIAS",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // 1 Year
            InvestmentPeriodCard(
                period = "Em 1 Ano (12 meses)",
                totalValue = currency.format(fv1Year),
                invested = currency.format(totalInvested1),
                interest = currency.format(interest1)
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 3 Years
            InvestmentPeriodCard(
                period = "Em 3 Anos (36 meses)",
                totalValue = currency.format(fv3Years),
                invested = currency.format(totalInvested3),
                interest = currency.format(interest3)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun InvestmentPeriodCard(
    period: String,
    totalValue: String,
    invested: String,
    interest: String
) {
    RecomecaCard(
        borderColor = DarkBorder,
        backgroundColor = DarkSecondaryBg
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = period, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = totalValue, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Aportado: $invested", fontSize = 11.sp, color = TextMuted)
                Text(text = "Juros: +$interest", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = LightMintGreen)
            }
        }
    }
}
