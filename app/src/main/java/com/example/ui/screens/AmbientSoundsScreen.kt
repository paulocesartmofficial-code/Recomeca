package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*
import kotlinx.coroutines.delay

data class AmbientSoundTrack(
    val id: String,
    val title: String,
    val subtitle: String,
    val emoji: String,
    val durationHint: String
)

@Composable
fun AmbientSoundsScreen(
    onOpenMenu: () -> Unit,
    onBack: () -> Unit
) {
    val tracks = remember {
        listOf(
            AmbientSoundTrack("rain", "Chuva Serena na Janela", "Gotas suaves para acalmar pensamentos acelerados", "🌧️", "Loop contínuo"),
            AmbientSoundTrack("fire", "Lareira Crepitante e Aconchegante", "Calor suave e estalidos reconfortantes de lenha", "🔥", "Loop contínuo"),
            AmbientSoundTrack("waves", "Ondas do Mar ao Entardecer", "Balanço rítmico do oceano para relaxamento profundo", "🌊", "Loop contínuo"),
            AmbientSoundTrack("forest", "Brisa na Floresta e Pássaros", "Natureza viva e ar puro para clarear a mente", "🌲", "Loop contínuo"),
            AmbientSoundTrack("piano", "Piano Minimalista & 432 Hz", "Frequência de cura para diminuir a frequência cardíaca", "🎵", "Loop contínuo"),
            AmbientSoundTrack("cafe", "Café Acolhedor em Dia Frio", "Murmúrios distantes e conforto de uma cafeteria serena", "☕", "Loop contínuo")
        )
    }

    var selectedTrackId by remember { mutableStateOf("rain") }
    var isPlaying by remember { mutableStateOf(false) }
    var volume by remember { mutableFloatStateOf(0.75f) }
    var timerMinutes by remember { mutableIntStateOf(30) }
    var secondsRemaining by remember { mutableIntStateOf(30 * 60) }

    // Pulsing visualizer animation
    val infiniteTransition = rememberInfiniteTransition(label = "sound_wave")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    LaunchedEffect(isPlaying) {
        while (isPlaying && secondsRemaining > 0) {
            delay(1000)
            secondsRemaining--
        }
        if (secondsRemaining <= 0) {
            isPlaying = false
        }
    }

    val selectedTrack = tracks.first { it.id == selectedTrackId }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Santuário de Sons & Calma",
            onBackClick = onBack
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Cozy Sanctuary Hero Banner
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, DarkBorder, RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.cozy_sanctuary),
                    contentDescription = "Ambiente acolhedor e aconchegante",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark subtle gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, DarkBackground.copy(alpha = 0.85f))
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(14.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = DarkGreenAccent.copy(alpha = 0.9f)
                    ) {
                        Text(
                            text = "DESCOMPRESSÃO IMEDIATA",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Refúgio de Sons da Natureza",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "Use fones de ouvido para desacelerar o ritmo cardíaco.",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Active Track Player Card
            RecomecaCard(
                borderColor = if (isPlaying) LightMintGreen else DarkBorder,
                backgroundColor = DarkCard
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(56.dp)
                            .scale(if (isPlaying) pulseScale else 1f)
                            .clip(CircleShape)
                            .background(if (isPlaying) DarkGreenAccent else DarkSecondaryBg)
                            .border(1.dp, if (isPlaying) LightMintGreen else DarkBorder, CircleShape)
                    ) {
                        Text(text = selectedTrack.emoji, fontSize = 28.sp)
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = selectedTrack.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = TextPrimary
                        )
                        Text(
                            text = selectedTrack.subtitle,
                            fontSize = 12.sp,
                            color = TextSecondary,
                            lineHeight = 16.sp
                        )
                    }

                    IconButton(
                        onClick = { isPlaying = !isPlaying },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(if (isPlaying) PrimaryGreen else DarkSecondaryBg)
                            .testTag("ambient_play_btn")
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pausar" else "Tocar",
                            tint = if (isPlaying) Color.White else LightMintGreen,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Timer & Volume Controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Timer, contentDescription = null, tint = AccentGold, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        val mins = secondsRemaining / 60
                        val secs = secondsRemaining % 60
                        Text(
                            text = "Timer: %02d:%02d".format(mins, secs),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = AccentGold
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        listOf(15, 30, 60).forEach { m ->
                            FilterChip(
                                selected = timerMinutes == m,
                                onClick = {
                                    timerMinutes = m
                                    secondsRemaining = m * 60
                                },
                                label = { Text("${m}m", fontSize = 10.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = DarkGreenAccent,
                                    selectedLabelColor = LightMintGreen,
                                    containerColor = DarkSecondaryBg,
                                    labelColor = TextMuted
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Volume slider
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.VolumeDown, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                    Slider(
                        value = volume,
                        onValueChange = { volume = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        colors = SliderDefaults.colors(
                            thumbColor = LightMintGreen,
                            activeTrackColor = PrimaryGreen,
                            inactiveTrackColor = DarkBorder
                        )
                    )
                    Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = TextMuted, modifier = Modifier.size(18.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sound Tracks Selection List
            Text(
                text = "ESCOLHA SUA PAISAGEM SONORA",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            tracks.forEach { track ->
                val isSelected = track.id == selectedTrackId
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (isSelected) DarkCardElevated else DarkSecondaryBg,
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSelected) LightMintGreen else DarkBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                        .clickable {
                            selectedTrackId = track.id
                            isPlaying = true
                        }
                        .testTag("track_${track.id}")
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = track.emoji, fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = track.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isSelected) LightMintGreen else TextPrimary
                            )
                            Text(
                                text = track.subtitle,
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                        }
                        if (isSelected && isPlaying) {
                            Icon(
                                imageVector = Icons.Default.GraphicEq,
                                contentDescription = "Tocando",
                                tint = LightMintGreen,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
