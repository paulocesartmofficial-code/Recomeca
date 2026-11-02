package com.example.ui.screens

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*

@Composable
fun VipScreen(
    userProfile: UserProfile,
    onOpenMenu: () -> Unit
) {
    var showCheckoutInfo by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Plano VIP Vitalício",
            onMenuClick = onOpenMenu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // VIP Badge Icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(AccentGold.copy(alpha = 0.15f))
                    .border(2.dp, AccentGold, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = AccentGold,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "RECOMEÇA VIP VITALÍCIO",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = AccentGold,
                letterSpacing = 1.sp
            )

            Text(
                text = "Acesso completo e ilimitado para sempre",
                fontSize = 14.sp,
                color = TextSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Price Card
            RecomecaCard(
                borderColor = AccentGold,
                backgroundColor = DarkCard
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "PAGAMENTO ÚNICO",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "R$ 19,90",
                            fontSize = 38.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextPrimary
                        )
                        Text(
                            text = " / vitalício",
                            fontSize = 14.sp,
                            color = TextSecondary,
                            modifier = Modifier.padding(bottom = 6.dp, start = 4.dp)
                        )
                    }
                    Text(
                        text = "Sem mensalidades, sem taxas recorrentes",
                        fontSize = 12.sp,
                        color = TextMuted
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Non-negotiable ethical guarantee
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = DarkSecondaryBg,
                border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryGreen),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = LightMintGreen,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Garantia Ética: O SOS, os canais de apoio à saúde e as ferramentas de emergência são e SEMPRE serão 100% gratuitos para todos.",
                        fontSize = 11.sp,
                        color = TextPrimary,
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // VIP Benefits List
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkCard
            ) {
                Text(
                    text = "O QUE ESTÁ INCLUSO NO VIP:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
                )
                Spacer(modifier = Modifier.height(10.dp))

                VipBenefitRow("Desbloqueio de todos os 10 Jogos Mentais e níveis avançados")
                VipBenefitRow("Metas Financeiras ilimitadas com múltiplos aportes")
                VipBenefitRow("Acesso integral aos 30 artigos e trilhas comportamentais")
                VipBenefitRow("Relatórios de projeção de até 5 anos na Calculadora")
                VipBenefitRow("Emblema VIP dourado exclusivo no seu perfil")
                VipBenefitRow("Acesso antecipado a novas ferramentas e exercícios")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (userProfile.isVip) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = DarkGreenAccent,
                    border = androidx.compose.foundation.BorderStroke(1.dp, LightMintGreen),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = LightMintGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Você já é um Membro VIP Vitalício!",
                            fontWeight = FontWeight.Bold,
                            color = LightMintGreen,
                            fontSize = 15.sp
                        )
                    }
                }
            } else {
                PrimaryGradientButton(
                    text = "Desbloquear VIP por R$ 19,90",
                    onClick = { showCheckoutInfo = true },
                    icon = Icons.Default.Star,
                    testTag = "vip_checkout_btn"
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }

    // Checkout real ainda não configurado: não exibir PIX fictício nem liberar VIP localmente.
    if (showCheckoutInfo) {
        AlertDialog(
            onDismissRequest = { showCheckoutInfo = false },
            containerColor = DarkSecondaryBg,
            title = {
                Text(
                    text = "Compra VIP em preparação",
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            },
            text = {
                Text(
                    text = "O pagamento online ainda não está habilitado nesta versão de testes. Nenhuma cobrança foi realizada e o acesso VIP não será liberado por este botão. Aguarde a ativação do checkout oficial.",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { showCheckoutInfo = false }) {
                    Text("Entendi", color = LightMintGreen)
                }
            }
        )
    }
}

@Composable
private fun VipBenefitRow(benefit: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = LightMintGreen,
            modifier = Modifier
                .size(18.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = benefit,
            fontSize = 13.sp,
            color = TextPrimary,
            lineHeight = 18.sp
        )
    }
}
