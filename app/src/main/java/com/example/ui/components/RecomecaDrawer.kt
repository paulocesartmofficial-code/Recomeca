package com.example.ui.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.theme.*
import com.example.ui.viewmodel.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecomecaNavigationSheet(
    userProfile: UserProfile,
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = DarkSecondaryBg,
        dragHandle = { BottomSheetDefaults.DragHandle(color = DarkBorder) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Profile Summary Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(DarkGreenAccent)
                        .border(2.dp, LightMintGreen, CircleShape)
                ) {
                    Text(
                        text = userProfile.name.take(2).uppercase(),
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = userProfile.name,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary,
                            fontSize = 16.sp
                        )
                        if (userProfile.isVip) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = AccentGold,
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Text(
                                    text = "VIP",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = DarkBackground,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                )
                            }
                        }
                    }
                    Text(
                        text = "Nível ${userProfile.level} • ${userProfile.xp} XP",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }

            HorizontalDivider(color = DarkBorder, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "FERRAMENTAS ESSENCIAIS",
                color = TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            DrawerItem(
                icon = Icons.Default.Book,
                title = "Diário Pessoal",
                subtitle = "Reflexões e vitórias diárias privadas",
                isSelected = currentScreen == Screen.JOURNAL,
                onClick = { onNavigate(Screen.JOURNAL); onDismiss() }
            )

            DrawerItem(
                icon = Icons.Default.Flag,
                title = "Metas Financeiras",
                subtitle = "Objetivos, dívidas e reservas",
                isSelected = currentScreen == Screen.GOALS,
                onClick = { onNavigate(Screen.GOALS); onDismiss() }
            )

            DrawerItem(
                icon = Icons.Default.School,
                title = "Conteúdo Educativo",
                subtitle = "30 artigos e trilhas práticas",
                isSelected = currentScreen == Screen.EDUCATION,
                onClick = { onNavigate(Screen.EDUCATION); onDismiss() }
            )

            DrawerItem(
                icon = Icons.Default.Headphones,
                title = "Santuário de Sons & Calma",
                subtitle = "Chuva, lareira e ondas para relaxar",
                iconTint = WarmSageMint,
                isSelected = currentScreen == Screen.AMBIENT_SOUNDS,
                onClick = { onNavigate(Screen.AMBIENT_SOUNDS); onDismiss() }
            )

            DrawerItem(
                icon = Icons.Default.AutoAwesome,
                title = "Mural de Força & Acolhimento",
                subtitle = "Frases diárias inspiradoras",
                iconTint = WarmAmber,
                isSelected = currentScreen == Screen.AFFIRMATIONS,
                onClick = { onNavigate(Screen.AFFIRMATIONS); onDismiss() }
            )

            DrawerItem(
                icon = Icons.Default.Shield,
                title = "Plano Anti-Gatilho 15 Min",
                subtitle = "Protocolo de emergência de 4 passos",
                iconTint = LightMintGreen,
                isSelected = currentScreen == Screen.CONTINGENCY_PLAN,
                onClick = { onNavigate(Screen.CONTINGENCY_PLAN); onDismiss() }
            )

            DrawerItem(
                icon = Icons.Default.TrendingUp,
                title = "Simulador de Juros Compostos",
                subtitle = "Cenários ilustrativos com dinheiro preservado",
                iconTint = AccentGold,
                isSelected = currentScreen == Screen.INVESTMENT_SIMULATOR,
                onClick = { onNavigate(Screen.INVESTMENT_SIMULATOR); onDismiss() }
            )

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = DarkBorder, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "CONFIGURAÇÕES & PLANOS",
                color = TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 6.dp)
            )

            DrawerItem(
                icon = Icons.Default.Star,
                title = "Plano VIP Vitalício",
                subtitle = if (userProfile.isVip) "Assinatura ativa vitalícia" else "Desbloqueie todos os jogos por R$ 19,90",
                iconTint = AccentGold,
                isSelected = currentScreen == Screen.VIP,
                onClick = { onNavigate(Screen.VIP); onDismiss() }
            )

            DrawerItem(
                icon = Icons.Default.AdminPanelSettings,
                title = "Painel Administrativo",
                subtitle = "Métricas do app e simulação comercial",
                iconTint = AccentPurple,
                isSelected = currentScreen == Screen.ADMIN,
                onClick = { onNavigate(Screen.ADMIN); onDismiss() }
            )

            DrawerItem(
                icon = Icons.Default.Settings,
                title = "Configurações e LGPD",
                subtitle = "Privacidade, termos e exportação",
                isSelected = currentScreen == Screen.SETTINGS,
                onClick = { onNavigate(Screen.SETTINGS); onDismiss() }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    isSelected: Boolean,
    iconTint: Color = LightMintGreen,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (isSelected) DarkCard else Color.Transparent)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                fontSize = 14.sp
            )
            Text(
                text = subtitle,
                color = TextSecondary,
                fontSize = 12.sp
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = TextMuted,
            modifier = Modifier.size(18.dp)
        )
    }
}
