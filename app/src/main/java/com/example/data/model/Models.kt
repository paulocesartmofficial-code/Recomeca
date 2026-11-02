package com.example.data.model

data class UserProfile(
    val id: String = "local_user",
    val name: String = "Usuário de teste",
    val email: String = "teste@local.recomeca",
    val avatarUrl: String = "",
    val startDateTimestamp: Long = System.currentTimeMillis(), // Start counting from the actual first session
    val avgDailyBet: Double = 0.0,
    val daysPerWeekBet: Int = 0,
    val avgMonthlyBet: Double = 0.0,
    val isVip: Boolean = false,
    val xp: Int = 0,
    val level: Int = 1,
    val emergencyContactName: String = "",
    val emergencyContactPhone: String = "",
    val isDemoMode: Boolean = true,
    val isAdmin: Boolean = false
) {
    val daysClean: Long
        get() {
            val now = System.currentTimeMillis()
            val diff = now - startDateTimestamp
            return if (diff > 0) diff / (24 * 60 * 60 * 1000) else 0L
        }

    val estimatedMoneySaved: Double
        get() {
            val days = daysClean.toDouble()
            val dailyEquivalent = if (avgDailyBet > 0) {
                (avgDailyBet * daysPerWeekBet) / 7.0
            } else {
                avgMonthlyBet / 30.0
            }
            return days * dailyEquivalent
        }
}

data class DailyCheckIn(
    val id: Long = 0,
    val dateString: String, // YYYY-MM-DD
    val timestamp: Long = System.currentTimeMillis(),
    val didBet: Boolean,
    val mood: Int, // 1: Muito mal, 2: Difícil, 3: Estável, 4: Bem, 5: Excelente
    val urgeLevel: Int, // 1: Nenhuma, 2: Baixa, 3: Média, 4: Alta, 5: Extrema
    val note: String = ""
)

data class JournalEntry(
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val mood: String, // "Esperançoso", "Tranquilo", "Ansioso", "Vitorioso", "Reflexivo"
    val hadUrge: Boolean,
    val actionTaken: String,
    val smallVictory: String,
    val notes: String,
    val tag: String = "Geral"
)

data class FinancialGoal(
    val id: Long = 0,
    val title: String,
    val category: String, // "Dívidas", "Reserva", "Conquista", "Família"
    val targetAmount: Double,
    val currentAmount: Double = 0.0,
    val deadlineDate: String = "",
    val isCompleted: Boolean = false
)

data class GoalContribution(
    val id: Long = 0,
    val goalId: Long,
    val amount: Double,
    val dateTimestamp: Long = System.currentTimeMillis(),
    val note: String = ""
)

data class FinancialSimulation(
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val dailyBet: Double,
    val weeklyDays: Int,
    val monthlyEstimate: Double,
    val annualEstimate: Double,
    val threeYearEstimate: Double,
    val fiveYearEstimate: Double,
    val targetObjective: String
)

data class GameScore(
    val id: Long = 0,
    val gameId: String,
    val gameName: String,
    val score: Int,
    val moves: Int = 0,
    val durationSeconds: Int = 0,
    val difficulty: String = "Normal",
    val timestamp: Long = System.currentTimeMillis()
)

data class EducationalArticle(
    val id: String,
    val category: String,
    val title: String,
    val readTimeMinutes: Int,
    val summary: String,
    val introduction: String,
    val mainText: String,
    val realExample: String,
    val conclusion: String,
    val recommendedAction: String,
    val isVipOnly: Boolean = false
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val requiredDaysClean: Long = 0,
    val requiredXp: Int = 0,
    val isUnlocked: Boolean = false,
    val unlockedAt: String = ""
)
