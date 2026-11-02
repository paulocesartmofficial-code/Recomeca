package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.theme.*

@Composable
fun AuthScreen(
    onAuthSuccess: (name: String, email: String) -> Unit
) {
    var isRegisterMode by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var showSupabaseConfigDialog by remember { mutableStateOf(false) }

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

        // Demo Mode Banner
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = DarkCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryGreen),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showSupabaseConfigDialog = true }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = LightMintGreen,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "MODO DE TESTE LOCAL",
                        fontWeight = FontWeight.Bold,
                        color = LightMintGreen,
                        fontSize = 12.sp
                    )
                    Text(
                        text = "Sem contas online nesta versão. Não informe senhas ou dados sensíveis.",
                        color = TextSecondary,
                        fontSize = 11.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = if (isRegisterMode) "Crie seu perfil local" else "Experimente o Recomeça",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Versão de demonstração: seu perfil e seus registros ficam somente neste aparelho.",
            fontSize = 13.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Tabs Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(DarkSecondaryBg)
                .padding(4.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (!isRegisterMode) DarkCard else Color.Transparent)
                    .clickable { isRegisterMode = false }
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = "Acessar demo",
                    fontWeight = FontWeight.Bold,
                    color = if (!isRegisterMode) LightMintGreen else TextMuted,
                    fontSize = 14.sp
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isRegisterMode) DarkCard else Color.Transparent)
                    .clickable { isRegisterMode = true }
                    .padding(vertical = 10.dp)
            ) {
                Text(
                    text = "Criar perfil",
                    fontWeight = FontWeight.Bold,
                    color = if (isRegisterMode) LightMintGreen else TextMuted,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Form Fields
        if (isRegisterMode) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome ou Apelido") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryGreen,
                    unfocusedBorderColor = DarkBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("auth_name_input")
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail (opcional)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryGreen,
                unfocusedBorderColor = DarkBorder,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("auth_email_input")
        )

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryGradientButton(
            text = if (isRegisterMode) "Salvar perfil neste aparelho" else "Continuar no modo local",
            onClick = {
                val finalName = name.trim().ifBlank { "Usuário de teste" }
                val finalEmail = email.trim().ifBlank { "teste@local.recomeca" }
                onAuthSuccess(finalName, finalEmail)
            },
            icon = Icons.Default.Login,
            testTag = "auth_submit_btn"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Direct Guest Login button
        OutlinedButton(
            onClick = { onAuthSuccess("Usuário Demo", "demo@recomeca.app") },
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary),
            border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .testTag("auth_guest_btn")
        ) {
            Icon(
                imageVector = Icons.Default.Shield,
                contentDescription = null,
                tint = LightMintGreen,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Entrar como visitante",
                color = TextPrimary,
                fontSize = 14.sp
            )
        }
    }

    // Supabase Info Dialog
    if (showSupabaseConfigDialog) {
        AlertDialog(
            onDismissRequest = { showSupabaseConfigDialog = false },
            title = { Text("Estrutura Supabase & PostgreSQL", color = TextPrimary) },
            text = {
                Column {
                    Text(
                        text = "Esta versão ainda não possui autenticação online nem sincronização entre dispositivos.",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Os dados do perfil de demonstração são armazenados localmente no aparelho. A integração com Supabase precisa ser implementada e configurada antes do lançamento comercial.",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showSupabaseConfigDialog = false }) {
                    Text("OK", color = LightMintGreen)
                }
            },
            containerColor = DarkSecondaryBg
        )
    }
}
