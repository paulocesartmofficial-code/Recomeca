package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.RecomecaBottomBar
import com.example.ui.components.RecomecaNavigationSheet
import com.example.ui.screens.*
import com.example.ui.theme.RecomecaTheme
import com.example.ui.viewmodel.RecomecaViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {

    private val viewModel: RecomecaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            RecomecaTheme {
                val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
                val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
                val checkIns by viewModel.checkIns.collectAsStateWithLifecycle()
                val journalEntries by viewModel.journalEntries.collectAsStateWithLifecycle()
                val financialGoals by viewModel.financialGoals.collectAsStateWithLifecycle()
                val simulations by viewModel.simulations.collectAsStateWithLifecycle()
                val gameScores by viewModel.gameScores.collectAsStateWithLifecycle()
                val selectedArticle by viewModel.selectedArticle.collectAsStateWithLifecycle()
                val selectedGameId by viewModel.selectedGameId.collectAsStateWithLifecycle()
                val userFeedback by viewModel.userFeedback.collectAsStateWithLifecycle()

                var showDrawerSheet by remember { mutableStateOf(false) }
                val snackbarHostState = remember { SnackbarHostState() }

                // Display feedback toasts/snackbars
                LaunchedEffect(userFeedback) {
                    if (userFeedback != null) {
                        snackbarHostState.showSnackbar(userFeedback!!)
                        viewModel.clearFeedback()
                    }
                }

                // Handle system back button gracefully
                BackHandler(enabled = currentScreen != Screen.DASHBOARD && currentScreen != Screen.SPLASH) {
                    val handled = viewModel.navigateBack()
                    if (!handled) {
                        viewModel.navigateTo(Screen.DASHBOARD)
                    }
                }

                val showBottomNav = currentScreen in listOf(
                    Screen.DASHBOARD,
                    Screen.FINANCE,
                    Screen.SOS,
                    Screen.GAMES_HUB,
                    Screen.JOURNEY
                )

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    bottomBar = {
                        if (showBottomNav) {
                            RecomecaBottomBar(
                                currentScreen = currentScreen,
                                onNavigate = { viewModel.navigateTo(it) }
                            )
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (currentScreen) {
                            Screen.SPLASH -> {
                                SplashScreen(
                                    onContinue = { viewModel.navigateTo(Screen.ONBOARDING) }
                                )
                            }
                            Screen.ONBOARDING -> {
                                OnboardingScreen(
                                    onStart = { viewModel.navigateTo(Screen.AUTH) }
                                )
                            }
                            Screen.AUTH -> {
                                AuthScreen(
                                    onAuthSuccess = { name, email ->
                                        viewModel.updateProfile(
                                            name = name,
                                            email = email,
                                            avgDaily = userProfile.avgDailyBet,
                                            daysPerWeek = userProfile.daysPerWeekBet,
                                            avgMonthly = userProfile.avgMonthlyBet,
                                            contactName = userProfile.emergencyContactName,
                                            contactPhone = userProfile.emergencyContactPhone
                                        )
                                        viewModel.navigateTo(Screen.DASHBOARD)
                                    }
                                )
                            }
                            Screen.DASHBOARD -> {
                                DashboardScreen(
                                    userProfile = userProfile,
                                    checkIns = checkIns,
                                    goals = financialGoals,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onNavigate = { viewModel.navigateTo(it) },
                                    onSubmitCheckIn = { didBet, mood, urge, note ->
                                        viewModel.submitCheckIn(didBet, mood, urge, note)
                                    },
                                    onOpenGame = { gameId ->
                                        viewModel.openGame(gameId)
                                    }
                                )
                            }
                            Screen.FINANCE -> {
                                FinanceScreen(
                                    userProfile = userProfile,
                                    simulations = simulations,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onSaveSimulation = { daily, daysWeek, monthly, target ->
                                        viewModel.saveSimulation(daily, daysWeek, monthly, target)
                                    },
                                    onDeleteSimulation = { viewModel.deleteSimulation(it) }
                                )
                            }
                            Screen.JOURNEY -> {
                                JourneyScreen(
                                    userProfile = userProfile,
                                    checkIns = checkIns,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onResetQuitDate = { viewModel.resetQuitDate() }
                                )
                            }
                            Screen.SOS -> {
                                SosScreen(
                                    userProfile = userProfile,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onNavigate = { viewModel.navigateTo(it) },
                                    onOpenGame = { gameId -> viewModel.openGame(gameId) }
                                )
                            }
                            Screen.GAMES_HUB -> {
                                GamesHubScreen(
                                    userProfile = userProfile,
                                    gameScores = gameScores,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onSelectGame = { gameId -> viewModel.openGame(gameId) }
                                )
                            }
                            Screen.GAME_PLAY -> {
                                GamePlayScreen(
                                    gameId = selectedGameId,
                                    onBack = { viewModel.navigateTo(Screen.GAMES_HUB) },
                                    onRecordScore = { id, name, score, moves, duration, diff ->
                                        viewModel.recordGameScore(id, name, score, moves, duration, diff)
                                    }
                                )
                            }
                            Screen.JOURNAL -> {
                                JournalScreen(
                                    journalEntries = journalEntries,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onAddEntry = { mood, hadUrge, action, victory, notes, tag ->
                                        viewModel.addJournalEntry(mood, hadUrge, action, victory, notes, tag)
                                    },
                                    onDeleteEntry = { viewModel.deleteJournalEntry(it) }
                                )
                            }
                            Screen.GOALS -> {
                                GoalsScreen(
                                    goals = financialGoals,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onAddGoal = { title, cat, amount, deadline ->
                                        viewModel.addGoal(title, cat, amount, deadline)
                                    },
                                    onContribute = { goal, amount, note ->
                                        viewModel.contributeToGoal(goal, amount, note)
                                    },
                                    onDeleteGoal = { viewModel.deleteGoal(it) }
                                )
                            }
                            Screen.EDUCATION -> {
                                EducationScreen(
                                    articles = viewModel.articles,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onSelectArticle = { article ->
                                        viewModel.openArticle(article)
                                    }
                                )
                            }
                            Screen.ARTICLE_DETAIL -> {
                                if (selectedArticle != null) {
                                    ArticleDetailScreen(
                                        article = selectedArticle!!,
                                        onBack = { viewModel.navigateTo(Screen.EDUCATION) },
                                        onNavigate = { viewModel.navigateTo(it) },
                                        onAwardXp = { xp, reason -> viewModel.awardXp(xp, reason) }
                                    )
                                } else {
                                    viewModel.navigateTo(Screen.EDUCATION)
                                }
                            }
                            Screen.VIP -> {
                                VipScreen(
                                    userProfile = userProfile,
                                    onOpenMenu = { showDrawerSheet = true }
                                )
                            }
                            Screen.ADMIN -> {
                                AdminScreen(
                                    userProfile = userProfile,
                                    onOpenMenu = { showDrawerSheet = true }
                                )
                            }
                            Screen.SETTINGS -> {
                                SettingsScreen(
                                    userProfile = userProfile,
                                    onOpenMenu = { showDrawerSheet = true },
                                    onUpdateProfile = { name, email, daily, days, monthly, contactName, contactPhone ->
                                        viewModel.updateProfile(name, email, daily, days, monthly, contactName, contactPhone)
                                    },
                                    onExportData = { viewModel.generateLgpdExportJson() },
                                    onDeleteAccount = { viewModel.deleteAccountAndData() }
                                )
                            }
                            Screen.AMBIENT_SOUNDS -> {
                                AmbientSoundsScreen(
                                    onOpenMenu = { showDrawerSheet = true },
                                    onBack = { viewModel.navigateTo(Screen.DASHBOARD) }
                                )
                            }
                            Screen.AFFIRMATIONS -> {
                                AffirmationsScreen(
                                    onBack = { viewModel.navigateTo(Screen.DASHBOARD) },
                                    onSaveToJournal = { quote ->
                                        viewModel.addJournalEntry(
                                            mood = "Inspirado",
                                            hadUrge = false,
                                            action = "Leitura de afirmação",
                                            victory = "Mente serena",
                                            notes = quote,
                                            tag = "Afirmações"
                                        )
                                        viewModel.navigateTo(Screen.JOURNAL)
                                    }
                                )
                            }
                            Screen.CONTINGENCY_PLAN -> {
                                ContingencyPlanScreen(
                                    onBack = { viewModel.navigateTo(Screen.DASHBOARD) },
                                    onAwardXp = { xp, reason -> viewModel.awardXp(xp, reason) }
                                )
                            }
                            Screen.INVESTMENT_SIMULATOR -> {
                                InvestmentSimulatorScreen(
                                    userProfile = userProfile,
                                    onBack = { viewModel.navigateTo(Screen.FINANCE) }
                                )
                            }
                        }
                    }
                }

                // Global Navigation Drawer Sheet
                if (showDrawerSheet) {
                    RecomecaNavigationSheet(
                        userProfile = userProfile,
                        currentScreen = currentScreen,
                        onNavigate = { dest ->
                            viewModel.navigateTo(dest)
                            showDrawerSheet = false
                        },
                        onDismiss = { showDrawerSheet = false }
                    )
                }
            }
        }
    }
}
