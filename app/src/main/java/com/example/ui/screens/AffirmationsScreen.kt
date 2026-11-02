package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*

data class AffirmationItem(
    val id: Int,
    val text: String,
    val author: String,
    val category: String,
    val emoji: String
)

@Composable
fun AffirmationsScreen(
    onBack: () -> Unit,
    onSaveToJournal: (quote: String) -> Unit
) {
    val context = LocalContext.current

    val affirmations = remember {
        listOf(
            AffirmationItem(
                id = 1,
                text = "Você não é definido pelos erros ou perdas do seu passado. A sua vida começa de verdade no momento em que você escolhe se cuidar.",
                author = "Princípio de Acolhimento",
                category = "Acolhimento",
                emoji = "🌱"
            ),
            AffirmationItem(
                id = 2,
                text = "A paz de colocar a cabeça no travesseiro sem mentiras nem desespero financeiro vale infinitamente mais do que qualquer aposta.",
                author = "Tranquilidade Real",
                category = "Serenidade",
                emoji = "🕊️"
            ),
            AffirmationItem(
                id = 3,
                text = "Apenas por hoje. Não se preocupe com o ano que vem. O único dia em que você precisa ser forte e proteger seu futuro é hoje.",
                author = "Filosofia de Sobriedade",
                category = "Coragem",
                emoji = "🛡️"
            ),
            AffirmationItem(
                id = 4,
                text = "Cada real que você não aposta hoje é um tijolo sólido na reconstrução da sua independência financeira e dos seus sonhos.",
                author = "Consciência Financeira",
                category = "Clareza Financeira",
                emoji = "💎"
            ),
            AffirmationItem(
                id = 5,
                text = "A vontade de apostar é apenas uma onda passageira. Ela atinge o pico, perde a força e se dissipa se você apenas respirar e esperar.",
                author = "Neurobiologia Comportamental",
                category = "Acolhimento",
                emoji = "🌊"
            ),
            AffirmationItem(
                id = 6,
                text = "Recomeçar quantas vezes forem necessárias não é sinal de fraqueza, mas da mais pura e inegável coragem de viver.",
                author = "Renascimento Pessoal",
                category = "Coragem",
                emoji = "☀️"
            )
        )
    }

    var selectedCategory by remember { mutableStateOf("Todas") }
    var favorites by remember { mutableStateOf<Set<Int>>(emptySet()) }
    var copyNotice by remember { mutableStateOf<String?>(null) }

    val categories = listOf("Todas", "Acolhimento", "Serenidade", "Coragem", "Clareza Financeira")

    val filteredList = remember(selectedCategory) {
        if (selectedCategory == "Todas") affirmations else affirmations.filter { it.category == selectedCategory }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Mural de Acolhimento & Força",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Category filter chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categories.take(3).forEach { cat ->
                    FilterChip(
                        selected = selectedCategory == cat,
                        onClick = { selectedCategory = cat },
                        label = { Text(cat, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = DarkGreenAccent,
                            selectedLabelColor = LightMintGreen,
                            containerColor = DarkCard,
                            labelColor = TextMuted
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp)
            ) {
                items(filteredList, key = { it.id }) { item ->
                    val isFavorite = favorites.contains(item.id)

                    RecomecaCard(
                        borderColor = if (isFavorite) LightMintGreen else DarkBorder,
                        backgroundColor = DarkCardElevated
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = DarkGreenAccent
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = item.emoji, fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = item.category,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = LightMintGreen
                                    )
                                }
                            }

                            Row {
                                IconButton(
                                    onClick = {
                                        favorites = if (isFavorite) favorites - item.id else favorites + item.id
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                        contentDescription = "Favoritar",
                                        tint = if (isFavorite) SoftRose else TextMuted,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }

                                IconButton(
                                    onClick = {
                                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                        clipboard.setPrimaryClip(ClipData.newPlainText("Frase Recomeça", "\"${item.text}\" — ${item.author}"))
                                        copyNotice = "Frase copiada!"
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copiar",
                                        tint = TextMuted,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "\"${item.text}\"",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "— ${item.author}",
                                fontSize = 12.sp,
                                color = WarmAmber,
                                fontWeight = FontWeight.SemiBold
                            )

                            TextButton(
                                onClick = { onSaveToJournal(item.text) },
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(imageVector = Icons.Default.Book, contentDescription = null, tint = LightMintGreen, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Refletir no Diário", fontSize = 11.sp, color = LightMintGreen)
                            }
                        }
                    }
                }
            }
        }
    }
}
