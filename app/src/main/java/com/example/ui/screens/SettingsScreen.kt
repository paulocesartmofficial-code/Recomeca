package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.components.PrimaryGradientButton
import com.example.ui.components.RecomecaCard
import com.example.ui.components.RecomecaTopBar
import com.example.ui.theme.*

@Composable
fun SettingsScreen(
    userProfile: UserProfile,
    onOpenMenu: () -> Unit,
    onUpdateProfile: (name: String, email: String, avgDaily: Double, daysWeek: Int, avgMonthly: Double, contactName: String, contactPhone: String) -> Unit,
    onExportData: () -> String,
    onDeleteAccount: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(userProfile.name) }
    var email by remember { mutableStateOf(userProfile.email) }
    var avgDailyText by remember { mutableStateOf(userProfile.avgDailyBet.toInt().toString()) }
    var daysWeek by remember { mutableIntStateOf(userProfile.daysPerWeekBet) }
    var emergencyName by remember { mutableStateOf(userProfile.emergencyContactName) }
    var emergencyPhone by remember { mutableStateOf(userProfile.emergencyContactPhone) }

    var showExportDialog by remember { mutableStateOf(false) }
    var exportJsonContent by remember { mutableStateOf("") }
    var showDeleteConfirmDialog by remember { mutableStateOf(false) }
    var showPrivacyPolicyDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        RecomecaTopBar(
            title = "Configurações & LGPD",
            onMenuClick = onOpenMenu
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Profile Form Card
            RecomecaCard(
                borderColor = PrimaryGreen,
                backgroundColor = DarkCard
            ) {
                Text(
                    text = "SEU PERFIL DE RECUPERAÇÃO",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Spacer(modifier = Modifier.height(12.dp))

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
                    modifier = Modifier.fillMaxWidth().testTag("settings_name_input")
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-mail") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = avgDailyText,
                    onValueChange = { avgDailyText = it },
                    label = { Text("Valor Médio Apostado Anteriormente (R$/dia)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "CONTATO DE CONFIANÇA (BOTÃO SOS)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = AccentGold
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = emergencyName,
                    onValueChange = { emergencyName = it },
                    label = { Text("Nome da Pessoa de Confiança") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = emergencyPhone,
                    onValueChange = { emergencyPhone = it },
                    label = { Text("Telefone com DDD (ex: 11999999999)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = DarkBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                PrimaryGradientButton(
                    text = "Salvar Alterações do Perfil",
                    onClick = {
                        val daily = avgDailyText.toDoubleOrNull() ?: 0.0
                        onUpdateProfile(name, email, daily, daysWeek, daily * 20, emergencyName, emergencyPhone)
                    },
                    icon = Icons.Default.Save,
                    testTag = "settings_save_btn"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // LGPD and Data Sovereignty Card
            RecomecaCard(
                borderColor = DarkBorder,
                backgroundColor = DarkSecondaryBg
            ) {
                Text(
                    text = "PRIVACIDADE E DIREITOS LGPD (LEI 13.709/2018)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = LightMintGreen
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Seus registros de diário, humor e finanças são estritamente privados. Não vendemos dados nem compartilhamos informações com plataformas de publicidade ou apostas.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = {
                        exportJsonContent = onExportData()
                        showExportDialog = true
                    },
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryGreen),
                    modifier = Modifier.fillMaxWidth().testTag("settings_export_btn")
                ) {
                    Icon(imageVector = Icons.Default.Download, contentDescription = null, tint = LightMintGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Exportar Meus Dados (JSON)", color = LightMintGreen, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedButton(
                    onClick = { showPrivacyPolicyDialog = true },
                    shape = RoundedCornerShape(10.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Default.Policy, contentDescription = null, tint = TextMuted)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Política de Privacidade e Termos de Uso", color = TextPrimary, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { showDeleteConfirmDialog = true },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed.copy(alpha = 0.2f)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AccentRed),
                    modifier = Modifier.fillMaxWidth().testTag("settings_delete_account_btn")
                ) {
                    Icon(imageVector = Icons.Default.DeleteForever, contentDescription = null, tint = AccentRed)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Excluir Minha Conta e Dados (Direito ao Esquecimento)", color = AccentRed, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    // Export Dialog
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            containerColor = DarkSecondaryBg,
            title = { Text("Exportação de Dados LGPD", fontWeight = FontWeight.Bold, color = TextPrimary) },
            text = {
                Column {
                    Text(
                        text = "Abaixo está a exportação estruturada dos seus dados locais:",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = DarkCard,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = exportJsonContent,
                            fontSize = 11.sp,
                            color = LightMintGreen,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        clipboard.setPrimaryClip(ClipData.newPlainText("Recomeca Dados", exportJsonContent))
                        showExportDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
                ) {
                    Text("Copiar JSON", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("Fechar", color = TextSecondary)
                }
            }
        )
    }

    // Delete confirmation
    if (showDeleteConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmDialog = false },
            containerColor = DarkSecondaryBg,
            title = { Text("Excluir Todos os Dados?", fontWeight = FontWeight.Bold, color = AccentRed) },
            text = {
                Text(
                    text = "Esta ação apagará permanentemente todos os seus diários, check-ins, metas e pontuações do banco de dados local Room, restaurando o aplicativo para o estado inicial.",
                    fontSize = 13.sp,
                    color = TextPrimary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteAccount()
                        showDeleteConfirmDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AccentRed)
                ) {
                    Text("Sim, Excluir Tudo", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmDialog = false }) {
                    Text("Cancelar", color = TextSecondary)
                }
            }
        )
    }

    // Privacy policy dialog
    if (showPrivacyPolicyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyPolicyDialog = false },
            containerColor = DarkSecondaryBg,
            title = { Text("Política de Privacidade", color = TextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "1. Coleta Mínima: O aplicativo armazena apenas dados estritamente necessários para o seu acompanhamento (check-ins, metas, reflexões de diário).\n\n" +
                               "2. Finalidade Exclusiva: As informações são utilizadas exclusivamente para exibir suas métricas e fornecer apoio emocional.\n\n" +
                               "3. Não Compartilhamento: Jamais vendemos, repassamos ou utilizamos seus dados para publicidade direcionada de jogos ou apostas.\n\n" +
                               "4. Direitos do Titular: Conforme a LGPD, você possui direito total de acessar, exportar e excluir todos os seus registros a qualquer momento.",
                        fontSize = 12.sp,
                        color = TextSecondary,
                        lineHeight = 17.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyPolicyDialog = false }) {
                    Text("Fechar", color = LightMintGreen)
                }
            }
        )
    }
}
