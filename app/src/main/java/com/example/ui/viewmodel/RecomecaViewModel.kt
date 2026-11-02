package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.*
import com.example.data.repository.RecomecaRepository
import com.example.data.seed.EducationalArticlesData
import com.example.data.seed.GameQuestionsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class Screen {
    SPLASH,
    ONBOARDING,
    AUTH,
    DASHBOARD,
    FINANCE,
    JOURNEY,
    GAMES_HUB,
    GAME_PLAY,
    SOS,
    JOURNAL,
    GOALS,
    EDUCATION,
    ARTICLE_DETAIL,
    VIP,
    ADMIN,
    SETTINGS,
    AMBIENT_SOUNDS,
    AFFIRMATIONS,
    CONTINGENCY_PLAN,
    INVESTMENT_SIMULATOR
}

class RecomecaViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecomecaRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = RecomecaRepository(db.recomecaDao())
    }

    // Screen navigation
    private val _currentScreen = MutableStateFlow(Screen.SPLASH)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _selectedArticle = MutableStateFlow<EducationalArticle?>(null)
    val selectedArticle: StateFlow<EducationalArticle?> = _selectedArticle.asStateFlow()

    private val _selectedGameId = MutableStateFlow("memory")
    val selectedGameId: StateFlow<String> = _selectedGameId.asStateFlow()

    // Navigation backstack tracking for system back button
    private val screenStack = mutableListOf<Screen>()

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenStack.isNotEmpty()) {
            val prev = screenStack.removeAt(screenStack.size - 1)
            _currentScreen.value = prev
            return true
        }
        return false
    }

    // Database flows
    val userProfile: StateFlow<UserProfile> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    val checkIns: StateFlow<List<DailyCheckIn>> = repository.checkIns
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val journalEntries: StateFlow<List<JournalEntry>> = repository.journalEntries
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val financialGoals: StateFlow<List<FinancialGoal>> = repository.financialGoals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val simulations: StateFlow<List<FinancialSimulation>> = repository.simulations
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val gameScores: StateFlow<List<GameScore>> = repository.gameScores
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val articles: List<EducationalArticle> = repository.getArticles()

    // Toast / Feedback message
    private val _userFeedback = MutableStateFlow<String?>(null)
    val userFeedback: StateFlow<String?> = _userFeedback.asStateFlow()

    fun showFeedback(message: String) {
        _userFeedback.value = message
    }

    fun clearFeedback() {
        _userFeedback.value = null
    }

    // Profile actions
    fun updateProfile(
        name: String,
        email: String,
        avgDaily: Double,
        daysPerWeek: Int,
        avgMonthly: Double,
        contactName: String,
        contactPhone: String
    ) {
        viewModelScope.launch {
            val current = userProfile.value
            val updated = current.copy(
                name = name,
                email = email,
                avgDailyBet = avgDaily,
                daysPerWeekBet = daysPerWeek,
                avgMonthlyBet = avgMonthly,
                emergencyContactName = contactName,
                emergencyContactPhone = contactPhone
            )
            repository.saveProfile(updated)
            showFeedback("Perfil atualizado com sucesso!")
        }
    }

    fun resetQuitDate() {
        viewModelScope.launch {
            val current = userProfile.value
            val updated = current.copy(
                startDateTimestamp = System.currentTimeMillis()
            )
            repository.saveProfile(updated)
            showFeedback("Contador reiniciado. Cada dia é um novo recomeço!")
        }
    }

    fun awardXp(points: Int, reason: String) {
        viewModelScope.launch {
            val current = userProfile.value
            val newXp = current.xp + points
            val newLevel = (newXp / 250) + 1
            repository.saveProfile(current.copy(xp = newXp, level = newLevel))
            showFeedback("+$points XP: $reason")
        }
    }

    // Check-in
    fun submitCheckIn(didBet: Boolean, mood: Int, urgeLevel: Int, note: String) {
        viewModelScope.launch {
            val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            repository.recordCheckIn(
                DailyCheckIn(
                    dateString = dateStr,
                    didBet = didBet,
                    mood = mood,
                    urgeLevel = urgeLevel,
                    note = note
                )
            )
            awardXp(50, "Check-in diário realizado")
            showFeedback(if (!didBet) "Parabéns por mais um dia limpo e vitorioso!" else "Respiração e calma. Recomeçar é um ato de coragem.")
        }
    }

    // Journal
    fun addJournalEntry(mood: String, hadUrge: Boolean, action: String, victory: String, notes: String, tag: String) {
        viewModelScope.launch {
            repository.addJournalEntry(
                JournalEntry(
                    mood = mood,
                    hadUrge = hadUrge,
                    actionTaken = action,
                    smallVictory = victory,
                    notes = notes,
                    tag = tag
                )
            )
            awardXp(40, "Reflexão registrada no diário")
            showFeedback("Registro salvo com privacidade no seu diário!")
        }
    }

    fun deleteJournalEntry(id: Long) {
        viewModelScope.launch {
            repository.deleteJournalEntry(id)
            showFeedback("Entrada do diário removida.")
        }
    }

    // Goals
    fun addGoal(title: String, category: String, targetAmount: Double, deadline: String) {
        viewModelScope.launch {
            repository.addGoal(
                FinancialGoal(
                    title = title,
                    category = category,
                    targetAmount = targetAmount,
                    deadlineDate = deadline
                )
            )
            awardXp(30, "Nova meta financeira criada")
            showFeedback("Meta criada com sucesso!")
        }
    }

    fun contributeToGoal(goal: FinancialGoal, amount: Double, note: String) {
        viewModelScope.launch {
            repository.contributeToGoal(goal, amount, note)
            awardXp(45, "Contribuição salva para a meta")
            showFeedback("R$ ${"%.2f".format(amount)} direcionados para sua meta!")
        }
    }

    fun deleteGoal(id: Long) {
        viewModelScope.launch {
            repository.deleteGoal(id)
            showFeedback("Meta removida.")
        }
    }

    // Financial Simulations
    fun saveSimulation(daily: Double, daysWeek: Int, monthly: Double, target: String) {
        viewModelScope.launch {
            val dailyEq = (daily * daysWeek) / 7.0
            val annual = dailyEq * 365
            val threeYears = annual * 3
            val fiveYears = annual * 5
            repository.saveSimulation(
                FinancialSimulation(
                    dailyBet = daily,
                    weeklyDays = daysWeek,
                    monthlyEstimate = monthly,
                    annualEstimate = annual,
                    threeYearEstimate = threeYears,
                    fiveYearEstimate = fiveYears,
                    targetObjective = target
                )
            )
            awardXp(25, "Simulação financeira salva")
            showFeedback("Simulação salva com sucesso!")
        }
    }

    fun deleteSimulation(id: Long) {
        viewModelScope.launch {
            repository.deleteSimulation(id)
            showFeedback("Simulação excluída.")
        }
    }

    // Game Scores
    fun recordGameScore(gameId: String, gameName: String, score: Int, moves: Int, durationSeconds: Int, difficulty: String) {
        viewModelScope.launch {
            repository.recordGameScore(
                GameScore(
                    gameId = gameId,
                    gameName = gameName,
                    score = score,
                    moves = moves,
                    durationSeconds = durationSeconds,
                    difficulty = difficulty
                )
            )
            val xpGain = (score / 10).coerceIn(20, 100)
            awardXp(xpGain, "Partida concluída em $gameName")
        }
    }

    // Article selection
    fun openArticle(article: EducationalArticle) {
        _selectedArticle.value = article
        navigateTo(Screen.ARTICLE_DETAIL)
    }

    // Game selection
    fun openGame(gameId: String) {
        _selectedGameId.value = gameId
        navigateTo(Screen.GAME_PLAY)
    }

    // LGPD Export Data
    fun generateLgpdExportJson(): String {
        val profile = userProfile.value
        val journals = journalEntries.value
        val checkInList = checkIns.value
        val goalsList = financialGoals.value

        return """
        {
          "app": "Recomeça 4.0",
          "exportTimestamp": "${System.currentTimeMillis()}",
          "profile": {
            "name": "${profile.name}",
            "email": "${profile.email}",
            "daysClean": ${profile.daysClean},
            "savedMoneyEstimate": ${profile.estimatedMoneySaved},
            "isVip": ${profile.isVip},
            "xp": ${profile.xp}
          },
          "checkInsCount": ${checkInList.size},
          "journalEntriesCount": ${journals.size},
          "financialGoalsCount": ${goalsList.size}
        }
        """.trimIndent()
    }

    fun deleteAccountAndData() {
        viewModelScope.launch {
            repository.clearAllUserData()
            showFeedback("Todos os dados locais foram apagados conforme a LGPD.")
            _currentScreen.value = Screen.AUTH
        }
    }
}
