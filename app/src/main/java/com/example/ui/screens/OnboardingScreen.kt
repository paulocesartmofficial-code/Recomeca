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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.theme.*

@Composable
fun OnboardingScreen(
    onStart: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = RoundedCornerShape(20.dp),
            color = DarkGreenAccent,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(
                text = "BEM-VINDO AO RECOMEÇA 4.0",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = LightMintGreen,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        Text(
            text = "Recupere o controle da sua vida e das suas finanças",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Uma ferramenta de apoio, sem julgamentos, para ajudar você a se afastar das apostas e reconstruir seus hábitos com mais clareza.",
            fontSize = 14.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3 Key pillars
        OnboardingPillar(
            icon = Icons.Default.Shield,
            title = "SOS e Controle de Impulsos",
            description = "Ferramenta imediata de pausa de 5 minutos, respiração guiada e apoio emocional gratuito nos momentos difíceis."
        )

        Spacer(modifier = Modifier.height(12.dp))

        OnboardingPillar(
            icon = Icons.Default.TrendingUp,
            title = "Clareza Financeira e Metas",
            description = "Calculadora precisa para ver quanto dinheiro retorna à sua vida e simulador de metas como quitar dívidas e criar reservas."
        )

        Spacer(modifier = Modifier.height(12.dp))

        OnboardingPillar(
            icon = Icons.Default.Psychology,
            title = "Central de Jogos e Mente Ativa",
            description = "10 jogos reais de raciocínio, memória e atenção para substituir o vácuo das apostas por estímulos saudáveis."
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Important ethical disclaimer
        RecomecaCard(
            backgroundColor = DarkCard,
            borderColor = DarkBorder
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = LightMintGreen,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Aviso Ético: O Recomeça é uma ferramenta de apoio pessoal, organização e educação comportamental. Não promete cura e não substitui o acompanhamento de médicos ou psicólogos especializados.",
                    fontSize = 12.sp,
                    color = TextMuted,
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        PrimaryGradientButton(
            text = "Começar Minha Jornada",
            onClick = onStart,
            icon = Icons.Default.ArrowForward,
            testTag = "onboarding_start_btn"
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun OnboardingPillar(
    icon: ImageVector,
    title: String,
    description: String
) {
    RecomecaCard(
        borderColor = DarkBorder,
        backgroundColor = DarkCard
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(44.dp)
                    .background(DarkGreenAccent, RoundedCornerShape(12.dp))
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = LightMintGreen,
                    modifier = Modifier.size(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
