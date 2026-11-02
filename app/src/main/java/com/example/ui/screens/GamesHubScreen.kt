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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.GameScore
import com.example.data.model.UserProfile
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*

data class GameMeta(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val icon: ImageVector,
    val isVip: Boolean = false
)

@Composable
fun GamesHubScreen(
    userProfile: UserProfile,
    gameScores: List<GameScore>,
    onOpenMenu: () -> Unit,
    onSelectGame: (String) -> Unit
) {
    val gamesList = listOf(
        GameMeta(
            id = "memory",
            name = "Jogo da Memória",
            category = "Memória",
            description = "Encontre os pares de símbolos de superação. Treine retenção visual e foco.",
            icon = Icons.Default.GridOn
        ),
        GameMeta(
            id = "sudoku",
            name = "Sudoku 9x9",
            category = "Raciocínio",
            description = "Grade 9x9 com validação em tempo real, modo notas e 3 dificuldades.",
            icon = Icons.Default.Dialpad
        ),
        GameMeta(
            id = "wordsearch",
            name = "Caça-Palavras da Superação",
            category = "Atenção",
            description = "Encontre termos restauradores escondidos na grade de letras.",
            icon = Icons.Default.Search
        ),
        GameMeta(
            id = "quiz",
            name = "Quiz de Conhecimentos",
            category = "Conhecimento",
            description = "Perguntas de ciência, história, cultura e tecnologia com explicações didáticas.",
            icon = Icons.Default.Quiz
        ),
        GameMeta(
            id = "logic",
            name = "Desafios de Lógica",
            category = "Raciocínio",
            description = "Sequências numéricas, enigmas e problemas de dedução lógica.",
            icon = Icons.Default.Psychology
        ),
        GameMeta(
            id = "sliding",
            name = "Quebra-Cabeça Deslizante",
            category = "Raciocínio",
            description = "Ordene as peças numeradas no menor número possível de movimentos.",
            icon = Icons.Default.ViewCompact
        ),
        GameMeta(
            id = "reaction",
            name = "Treino de Reação & Atenção",
            category = "Atenção",
            description = "Meça seus reflexos em milissegundos e canalize o foco ativo.",
            icon = Icons.Default.Speed
        ),
        GameMeta(
            id = "scramble",
            name = "Palavras Embaralhadas",
            category = "Conhecimento",
            description = "Decifre anagramas inspiradores com dicas e pistas.",
            icon = Icons.Default.Spellcheck
        ),
        GameMeta(
            id = "math",
            name = "Desafio Mental Matemático",
            category = "Raciocínio",
            description = "Cálculos mentais dinâmicos para ativar o raciocínio rápido.",
            icon = Icons.Default.Calculate
        ),
        GameMeta(
            id = "breathing",
            name = "Respiração Guiada 4-7-8",
            category = "Relaxamento",
            description = "Animação suave de pulsação para desacelerar a ansiedade e relaxar.",
            icon = Icons.Default.Air
        )
    )

    var selectedFilter by remember { mutableStateOf("Todos") }
    val categories = listOf("Todos", "Raciocínio", "Memória", "Conhecimento", "Relaxamento", "Atenção")

    val filteredGames = remember(selectedFilter) {
        if (selectedFilter == "Todos") gamesList else gamesList.filter { it.category == selectedFilter }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Central de Jogos Mentais",
            onMenuClick = onOpenMenu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(6.dp))

            // Purpose Card
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkSecondaryBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Stars,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Jogos saudáveis e autênticos. Sem mecânicas de apostas ou recompensas pagas: apenas treino cognitivo real.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Category filter chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.take(4).forEach { cat ->
                    FilterChip(
                        selected = selectedFilter == cat,
                        onClick = { selectedFilter = cat },
                        label = { Text(cat, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DarkGreenAccent,
                            selectedLabelColor = LightMintGreen,
                            containerColor = DarkCard,
                            labelColor = TextSecondary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(filteredGames, key = { it.id }) { game ->
                    RecomecaCard(
                        borderColor = DarkBorder,
                        backgroundColor = DarkCard,
                        modifier = Modifier
                            .clickable { onSelectGame(game.id) }
                            .testTag("game_item_${game.id}")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(DarkGreenAccent, RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = game.icon,
                                    contentDescription = null,
                                    tint = LightMintGreen,
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = game.name,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = TextPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = DarkSecondaryBg
                                    ) {
                                        Text(
                                            text = game.category,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = AccentGold,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = game.description,
                                    fontSize = 12.sp,
                                    color = TextSecondary,
                                    lineHeight = 16.sp
                                )
                            }

                            Button(
                                onClick = { onSelectGame(game.id) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text("Jogar", fontSize = 12.sp, color = Color.White, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}
