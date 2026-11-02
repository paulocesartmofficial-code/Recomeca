package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.data.model.JournalEntry
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun JournalScreen(
    journalEntries: List<JournalEntry>,
    onOpenMenu: () -> Unit,
    onAddEntry: (mood: String, hadUrge: Boolean, action: String, victory: String, notes: String, tag: String) -> Unit,
    onDeleteEntry: (Long) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showAddDialog by remember { mutableStateOf(false) }

    // Dialog state
    var selectedMood by remember { mutableStateOf("Vitorioso") }
    var hadUrge by remember { mutableStateOf(false) }
    var actionTaken by remember { mutableStateOf("") }
    var smallVictory by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("Geral") }

    val filteredList = remember(journalEntries, searchQuery) {
        if (searchQuery.isBlank()) {
            journalEntries
        } else {
            journalEntries.filter {
                it.notes.contains(searchQuery, ignoreCase = true) ||
                it.smallVictory.contains(searchQuery, ignoreCase = true) ||
                it.mood.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val dateFormat = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Diário Pessoal",
            onMenuClick = onOpenMenu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Search bar & Add Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar reflexões...", fontSize = 13.sp) },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = TextMuted)
                    },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("journal_search_input")
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = { showAddDialog = true },
                    containerColor = PrimaryGreen,
                    contentColor = Color.White,
                    modifier = Modifier.size(50.dp).testTag("journal_add_fab")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Nova Entrada")
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Book,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Nenhuma reflexão registrada ainda",
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Toque no botão + acima para escrever como você se sentiu hoje. Seus registros são 100% privados.",
                            color = TextSecondary,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(filteredList, key = { it.id }) { entry ->
                        RecomecaCard(
                            borderColor = DarkBorder,
                            backgroundColor = DarkCard
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = DarkGreenAccent
                                    ) {
                                        Text(
                                            text = entry.mood,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = LightMintGreen,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                        )
                                    }
                                    if (entry.hadUrge) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            shape = RoundedCornerShape(8.dp),
                                            color = AccentRed.copy(alpha = 0.2f)
                                        ) {
                                            Text(
                                                text = "Teve Vontade (Superada)",
                                                fontSize = 10.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = AccentRed,
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                            )
                                        }
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = dateFormat.format(Date(entry.timestamp)),
                                        fontSize = 11.sp,
                                        color = TextMuted
                                    )
                                    IconButton(
                                        onClick = { onDeleteEntry(entry.id) },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.DeleteOutline,
                                            contentDescription = "Excluir",
                                            tint = TextMuted,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                }
                            }

                            if (entry.smallVictory.isNotBlank()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "🏆 Pequena Vitória: ${entry.smallVictory}",
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp,
                                    color = LightMintGreen
                                )
                            }

                            if (entry.actionTaken.isNotBlank()) {
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "🛡️ O que fiz: ${entry.actionTaken}",
                                    fontSize = 12.sp,
                                    color = TextSecondary
                                )
                            }

                            if (entry.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = entry.notes,
                                    fontSize = 13.sp,
                                    color = TextPrimary,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // New Entry Modal Dialog
    if (showAddDialog) {
        AlertDialog(
            onDismissRequest = { showAddDialog = false },
            containerColor = DarkSecondaryBg,
            title = { Text("Nova Reflexão no Diário", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Como estou me sentindo?", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("Vitorioso", "Sereno", "Ansioso", "Focado").forEach { m ->
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (selectedMood == m) DarkGreenAccent else DarkCard)
                                    .border(1.dp, if (selectedMood == m) LightMintGreen else DarkBorder, RoundedCornerShape(8.dp))
                                    .clickable { selectedMood = m }
                                    .padding(horizontal = 8.dp, vertical = 6.dp)
                            ) {
                                Text(m, fontSize = 11.sp, color = if (selectedMood == m) LightMintGreen else TextSecondary)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = hadUrge,
                            onCheckedChange = { hadUrge = it },
                            colors = CheckboxDefaults.colors(checkedColor = PrimaryGreen)
                        )
                        Text("Tive vontade de apostar hoje", fontSize = 12.sp, color = TextPrimary)
                    }

                    if (hadUrge) {
                        OutlinedTextField(
                            value = actionTaken,
                            onValueChange = { actionTaken = it },
                            label = { Text("O que fiz para lidar com isso?") },
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
                    }

                    OutlinedTextField(
                        value = smallVictory,
                        onValueChange = { smallVictory = it },
                        label = { Text("Qual foi minha pequena vitória hoje?") },
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
                        value = notes,
                        onValueChange = { notes = it },
                        label = { Text("Reflexão livre...") },
                        maxLines = 4,
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
                        onAddEntry(selectedMood, hadUrge, actionTaken, smallVictory, notes, selectedTag)
                        showAddDialog = false
                        actionTaken = ""
                        smallVictory = ""
                        notes = ""
                        hadUrge = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Salvar", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showAddDialog = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        )
    }
}
