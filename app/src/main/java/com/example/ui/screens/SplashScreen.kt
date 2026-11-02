package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onContinue: () -> Unit
) {
    // Pulse animation
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    LaunchedEffect(Unit) {
        delay(2200)
        onContinue()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(DarkBackground, DarkSecondaryBg, DarkBackground)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Logo Container with glowing rim
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(130.dp)
                    .scale(scale)
                    .clip(CircleShape)
                    .background(DarkCard)
                    .border(2.dp, LightMintGreen, CircleShape)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.recomeca_logo),
                    contentDescription = "Logo Recomeça",
                    modifier = Modifier.size(90.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "RECOMEÇA",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 4.sp,
                color = TextPrimary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Sua vida vale mais que qualquer aposta.",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = LightMintGreen,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Fallback manual advance button if user clicks early
            PrimaryGradientButton(
                text = "Entrar na Plataforma",
                onClick = onContinue,
                modifier = Modifier.widthIn(max = 280.dp),
                testTag = "splash_continue_btn"
            )
        }
    }
}
