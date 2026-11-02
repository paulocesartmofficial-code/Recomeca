package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.EducationalArticle
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import com.example.ui.viewmodel.Screen

@Composable
fun ArticleDetailScreen(
    article: EducationalArticle,
    onBack: () -> Unit,
    onNavigate: (Screen) -> Unit,
    onAwardXp: (Int, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Leitura Educativa",
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
                shape = RoundedCornerShape(8.dp),
                color = DarkGreenAccent
            ) {
                Text(
                    text = article.category.uppercase(),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = article.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Tempo de leitura estimado: ${article.readTimeMinutes} min",
                    fontSize = 12.sp,
                    color = TextMuted
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Introduction Card
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkSecondaryBg
            ) {
                Text(
                    text = "INTRODUÇÃO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.introduction,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Main Text Card
            RecomecaCard(
                borderColor = PrimaryGreen,
                backgroundColor = DarkCard
            ) {
                Text(
                    text = "FUNDAMENTAÇÃO E ANÁLISE",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.mainText,
                    fontSize = 14.sp,
                    color = TextPrimary,
                    lineHeight = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Real Example Card
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkSecondaryBg
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Lightbulb,
                        contentDescription = null,
                        tint = AccentGold,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "EXEMPLO REAL DE APLICAÇÃO",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = AccentGold
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.realExample,
                    fontSize = 13.sp,
                    color = TextSecondary,
                    lineHeight = 19.sp
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Conclusion
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkCard
            ) {
                Text(
                    text = "CONCLUSÃO PRÁTICA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = article.conclusion,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary,
                    lineHeight = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Recommended Action Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = DarkGreenAccent.copy(alpha = 0.4f),
                border = androidx.compose.foundation.BorderStroke(1.dp, LightMintGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "PRÓXIMA AÇÃO RECOMENDADA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = article.recommendedAction,
                        fontSize = 13.sp,
                        color = TextPrimary,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            PrimaryGradientButton(
                text = "Concluir Leitura (+30 XP)",
                onClick = {
                    onAwardXp(30, "Artigo '${article.title}' lido com sucesso")
                    onBack()
                },
                icon = Icons.Default.Check,
                testTag = "article_complete_btn"
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
