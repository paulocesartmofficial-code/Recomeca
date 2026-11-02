package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.ui.viewmodel.Screen

@Composable
fun RecomecaTopBar(
    title: String,
    onMenuClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        color = DarkBackground,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("top_bar_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = TextPrimary
                    )
                }
            } else if (onMenuClick != null) {
                IconButton(
                    onClick = onMenuClick,
                    modifier = Modifier.testTag("top_bar_menu_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = TextPrimary
                    )
                }
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = TextPrimary
                ),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = if (onBackClick != null || onMenuClick != null) 8.dp else 0.dp)
            )

            actions()
        }
    }
}

@Composable
fun RecomecaBottomBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    Surface(
        color = DarkSecondaryBg,
        tonalElevation = 8.dp,
        border = BorderStroke(1.dp, DarkBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(vertical = 6.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Home,
                label = "Início",
                isSelected = currentScreen == Screen.DASHBOARD,
                testTag = "nav_dashboard",
                onClick = { onNavigate(Screen.DASHBOARD) }
            )

            BottomNavItem(
                icon = Icons.Default.AttachMoney,
                label = "Finanças",
                isSelected = currentScreen == Screen.FINANCE,
                testTag = "nav_finance",
                onClick = { onNavigate(Screen.FINANCE) }
            )

            // SOS Center Highlight
            Surface(
                shape = CircleShape,
                color = AccentRed,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .clickable { onNavigate(Screen.SOS) }
                    .testTag("nav_sos_fab")
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        imageVector = Icons.Default.Emergency,
                        contentDescription = "SOS Emergência",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            BottomNavItem(
                icon = Icons.Default.SportsEsports,
                label = "Jogos",
                isSelected = currentScreen == Screen.GAMES_HUB || currentScreen == Screen.GAME_PLAY,
                testTag = "nav_games",
                onClick = { onNavigate(Screen.GAMES_HUB) }
            )

            BottomNavItem(
                icon = Icons.Default.Timeline,
                label = "Jornada",
                isSelected = currentScreen == Screen.JOURNEY,
                testTag = "nav_journey",
                onClick = { onNavigate(Screen.JOURNEY) }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) LightMintGreen else TextMuted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) LightMintGreen else TextMuted
        )
    }
}

@Composable
fun RecomecaCard(
    modifier: Modifier = Modifier,
    borderColor: Color = DarkBorder,
    backgroundColor: Color = DarkCard,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, borderColor, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            content = content
        )
    }
}

@Composable
fun PrimaryGradientButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    testTag: String = "primary_btn"
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(PrimaryGreen, LightMintGreen)
                )
            )
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = DarkBackground,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                color = DarkBackground,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
