package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.data.model.FinancialGoal
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import java.text.NumberFormat
import java.util.Locale

@Composable
fun GoalsScreen(
    goals: List<FinancialGoal>,
    onOpenMenu: () -> Unit,
    onAddGoal: (title: String, category: String, targetAmount: Double, deadline: String) -> Unit,
    onContribute: (goal: FinancialGoal, amount: Double, note: String) -> Unit,
    onDeleteGoal: (Long) -> Unit
) {
    var showAddGoalDialog by remember { mutableStateOf(false) }
    var selectedGoalForContribution by remember { mutableStateOf<FinancialGoal?>(null) }

    // Add Goal State
    var newGoalTitle by remember { mutableStateOf("") }
    var newGoalCategory by remember { mutableStateOf("Reserva") }
    var newGoalAmountText by remember { mutableStateOf("") }
    var newGoalDeadline by remember { mutableStateOf("") }

    // Contribution State
    var contributionAmountText by remember { mutableStateOf("") }
    var contributionNote by remember { mutableStateOf("") }

    val ptBr = Locale("pt", "BR")
    val currency = NumberFormat.getCurrencyInstance(ptBr)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Metas Financeiras",
            onMenuClick = onOpenMenu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Header summary
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
                            text = "DIRECIONAMENTO POSITIVO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen
                        )
                        Text(
                            text = "${goals.size} Metas em Andamento",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    }

                    Button(
                        onClick = { showAddGoalDialog = true },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                        modifier = Modifier.testTag("goals_add_btn")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Nova Meta", color = Color.White, fontSize = 12.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (goals.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Nenhuma meta cadastrada ainda",
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Cadastre um objetivo como quitar uma dívida ou montar sua reserva para direcionar o dinheiro que você não aposta mais.",
                            color = TextSecondary,
                            fontSize = 12.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(goals, key = { it.id }) { goal ->
                        val progress = if (goal.targetAmount > 0) {
                            (goal.currentAmount / goal.targetAmount).toFloat().coerceIn(0f, 1f)
                        } else 0f

                        RecomecaCard(
                            borderColor = if (goal.isCompleted) LightMintGreen else DarkBorder,
                            backgroundColor = DarkCard
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = DarkGreenAccent
                                    ) {
                                        Text(
                                            text = goal.category,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = LightMintGreen,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                    if (goal.isCompleted) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(6.dp),
                                            color = AccentGold.copy(alpha = 0.2f)
                                        ) {
                                            Text(
                                                text = "CONCLUÍDA 🏆",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = AccentGold,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }

                                IconButton(
                                    onClick = { onDeleteGoal(goal.id) },
                                    modifier = Modifier.size(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.DeleteOutline,
                                        contentDescription = "Excluir",
                                        tint = TextMuted,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = goal.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                color = TextPrimary
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            LinearProgressIndicator(
                                progress = { progress },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                                color = if (goal.isCompleted) AccentGold else LightMintGreen,
                                trackColor = DarkBorder
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "${currency.format(goal.currentAmount)} / ${currency.format(goal.targetAmount)}",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = LightMintGreen
                                    )
                                    if (goal.deadlineDate.isNotBlank()) {
                                        Text(
                                            text = "Prazo: ${goal.deadlineDate}",
                                            fontSize = 11.sp,
                                            color = TextMuted
                                        )
                                    }
                                }

                                if (!goal.isCompleted) {
                                    OutlinedButton(
                                        onClick = { selectedGoalForContribution = goal },
                                        shape = RoundedCornerShape(8.dp),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryGreen),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                    ) {
                                        Text("+ Aportar", fontSize = 12.sp, color = LightMintGreen)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Add Goal Dialog
    if (showAddGoalDialog) {
        AlertDialog(
            onDismissRequest = { showAddGoalDialog = false },
            containerColor = DarkSecondaryBg,
            title = { Text("Criar Nova Meta", fontWeight = FontWeight.Bold, color = TextPrimary) },
            text = {
                Column {
                    OutlinedTextField(
                        value = newGoalTitle,
                        onValueChange = { newGoalTitle = it },
                        label = { Text("Nome da Meta (ex: Quitar Cartão)") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = DarkBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newGoalAmountText,
                        onValueChange = { newGoalAmountText = it },
                        label = { Text("Valor Alvo (R$)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = DarkBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newGoalDeadline,
                        onValueChange = { newGoalDeadline = it },
                        label = { Text("Prazo Estimado (ex: Dez/2026)") },
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
                        val amount = newGoalAmountText.toDoubleOrNull() ?: 0.0
                        if (newGoalTitle.isNotBlank() && amount > 0) {
                            onAddGoal(newGoalTitle, newGoalCategory, amount, newGoalDeadline)
                            showAddGoalDialog = false
                            newGoalTitle = ""
                            newGoalAmountText = ""
                            newGoalDeadline = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Criar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddGoalDialog = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        )
    }

    // Contribution Dialog
    if (selectedGoalForContribution != null) {
        val targetGoal = selectedGoalForContribution!!
        AlertDialog(
            onDismissRequest = { selectedGoalForContribution = null },
            containerColor = DarkSecondaryBg,
            title = { Text("Aportar na Meta: ${targetGoal.title}", color = TextPrimary, fontSize = 16.sp) },
            text = {
                Column {
                    Text("Quanto do dinheiro preservado você quer destinar agora?", fontSize = 12.sp, color = TextSecondary)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = contributionAmountText,
                        onValueChange = { contributionAmountText = it },
                        label = { Text("Valor do aporte (R$)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryGreen,
                            unfocusedBorderColor = DarkBorder,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = contributionNote,
                        onValueChange = { contributionNote = it },
                        label = { Text("Origem / Nota (ex: Economia da semana)") },
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
                        val amount = contributionAmountText.toDoubleOrNull() ?: 0.0
                        if (amount > 0) {
                            onContribute(targetGoal, amount, contributionNote)
                            selectedGoalForContribution = null
                            contributionAmountText = ""
                            contributionNote = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Confirmar Aporte", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { selectedGoalForContribution = null }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        )
    }
}
