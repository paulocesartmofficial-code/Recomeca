package com.example.data.repository

import com.example.data.local.*
import com.example.data.model.*
import com.example.data.seed.EducationalArticlesData
import com.example.data.seed.GameQuestionsData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RecomecaRepository(private val dao: RecomecaDao) {

    val userProfile: Flow<UserProfile> = dao.getProfile().map { entity ->
        if (entity != null) {
            UserProfile(
                id = entity.id,
                name = entity.name,
                email = entity.email,
                avatarUrl = entity.avatarUrl,
                startDateTimestamp = entity.startDateTimestamp,
                avgDailyBet = entity.avgDailyBet,
                daysPerWeekBet = entity.daysPerWeekBet,
                avgMonthlyBet = entity.avgMonthlyBet,
                isVip = entity.isVip,
                xp = entity.xp,
                level = entity.level,
                emergencyContactName = entity.emergencyContactName,
                emergencyContactPhone = entity.emergencyContactPhone,
                isDemoMode = entity.isDemoMode,
                isAdmin = entity.isAdmin
            )
        } else {
            UserProfile()
        }
    }

    suspend fun saveProfile(profile: UserProfile) {
        dao.saveProfile(
            ProfileEntity(
                id = profile.id,
                name = profile.name,
                email = profile.email,
                avatarUrl = profile.avatarUrl,
                startDateTimestamp = profile.startDateTimestamp,
                avgDailyBet = profile.avgDailyBet,
                daysPerWeekBet = profile.daysPerWeekBet,
                avgMonthlyBet = profile.avgMonthlyBet,
                isVip = profile.isVip,
                xp = profile.xp,
                level = profile.level,
                emergencyContactName = profile.emergencyContactName,
                emergencyContactPhone = profile.emergencyContactPhone,
                isDemoMode = profile.isDemoMode,
                isAdmin = profile.isAdmin
            )
        )
    }

    suspend fun addXp(amount: Int) {
        // Will be called to increment XP
    }

    // Checkins
    val checkIns: Flow<List<DailyCheckIn>> = dao.getAllCheckIns().map { list ->
        list.map {
            DailyCheckIn(
                id = it.id,
                dateString = it.dateString,
                timestamp = it.timestamp,
                didBet = it.didBet,
                mood = it.mood,
                urgeLevel = it.urgeLevel,
                note = it.note
            )
        }
    }

    suspend fun recordCheckIn(checkIn: DailyCheckIn): Long {
        return dao.insertCheckIn(
            CheckInEntity(
                dateString = checkIn.dateString,
                timestamp = checkIn.timestamp,
                didBet = checkIn.didBet,
                mood = checkIn.mood,
                urgeLevel = checkIn.urgeLevel,
                note = checkIn.note
            )
        )
    }

    // Journal
    val journalEntries: Flow<List<JournalEntry>> = dao.getAllJournalEntries().map { list ->
        list.map {
            JournalEntry(
                id = it.id,
                timestamp = it.timestamp,
                mood = it.mood,
                hadUrge = it.hadUrge,
                actionTaken = it.actionTaken,
                smallVictory = it.smallVictory,
                notes = it.notes,
                tag = it.tag
            )
        }
    }

    suspend fun addJournalEntry(entry: JournalEntry): Long {
        return dao.insertJournal(
            JournalEntity(
                timestamp = entry.timestamp,
                mood = entry.mood,
                hadUrge = entry.hadUrge,
                actionTaken = entry.actionTaken,
                smallVictory = entry.smallVictory,
                notes = entry.notes,
                tag = entry.tag
            )
        )
    }

    suspend fun deleteJournalEntry(id: Long) {
        dao.deleteJournalById(id)
    }

    // Goals
    val financialGoals: Flow<List<FinancialGoal>> = dao.getAllGoals().map { list ->
        list.map {
            FinancialGoal(
                id = it.id,
                title = it.title,
                category = it.category,
                targetAmount = it.targetAmount,
                currentAmount = it.currentAmount,
                deadlineDate = it.deadlineDate,
                isCompleted = it.isCompleted
            )
        }
    }

    suspend fun addGoal(goal: FinancialGoal): Long {
        return dao.insertGoal(
            GoalEntity(
                title = goal.title,
                category = goal.category,
                targetAmount = goal.targetAmount,
                currentAmount = goal.currentAmount,
                deadlineDate = goal.deadlineDate,
                isCompleted = goal.isCompleted
            )
        )
    }

    suspend fun contributeToGoal(goal: FinancialGoal, amount: Double, note: String) {
        val updatedAmount = goal.currentAmount + amount
        val isCompleted = updatedAmount >= goal.targetAmount
        dao.updateGoal(
            GoalEntity(
                id = goal.id,
                title = goal.title,
                category = goal.category,
                targetAmount = goal.targetAmount,
                currentAmount = updatedAmount,
                deadlineDate = goal.deadlineDate,
                isCompleted = isCompleted
            )
        )
        dao.insertContribution(
            ContributionEntity(
                goalId = goal.id,
                amount = amount,
                dateTimestamp = System.currentTimeMillis(),
                note = note
            )
        )
    }

    suspend fun deleteGoal(id: Long) {
        dao.deleteGoalById(id)
    }

    // Simulations
    val simulations: Flow<List<FinancialSimulation>> = dao.getAllSimulations().map { list ->
        list.map {
            FinancialSimulation(
                id = it.id,
                timestamp = it.timestamp,
                dailyBet = it.dailyBet,
                weeklyDays = it.weeklyDays,
                monthlyEstimate = it.monthlyEstimate,
                annualEstimate = it.annualEstimate,
                threeYearEstimate = it.threeYearEstimate,
                fiveYearEstimate = it.fiveYearEstimate,
                targetObjective = it.targetObjective
            )
        }
    }

    suspend fun saveSimulation(simulation: FinancialSimulation): Long {
        return dao.insertSimulation(
            SimulationEntity(
                timestamp = simulation.timestamp,
                dailyBet = simulation.dailyBet,
                weeklyDays = simulation.weeklyDays,
                monthlyEstimate = simulation.monthlyEstimate,
                annualEstimate = simulation.annualEstimate,
                threeYearEstimate = simulation.threeYearEstimate,
                fiveYearEstimate = simulation.fiveYearEstimate,
                targetObjective = simulation.targetObjective
            )
        )
    }

    suspend fun deleteSimulation(id: Long) {
        dao.deleteSimulationById(id)
    }

    // Game scores
    val gameScores: Flow<List<GameScore>> = dao.getAllScores().map { list ->
        list.map {
            GameScore(
                id = it.id,
                gameId = it.gameId,
                gameName = it.gameName,
                score = it.score,
                moves = it.moves,
                durationSeconds = it.durationSeconds,
                difficulty = it.difficulty,
                timestamp = it.timestamp
            )
        }
    }

    suspend fun recordGameScore(score: GameScore): Long {
        return dao.insertScore(
            ScoreEntity(
                gameId = score.gameId,
                gameName = score.gameName,
                score = score.score,
                moves = score.moves,
                durationSeconds = score.durationSeconds,
                difficulty = score.difficulty,
                timestamp = score.timestamp
            )
        )
    }

    // LGPD: Data export and delete
    suspend fun clearAllUserData() {
        dao.clearJournal()
        dao.clearCheckIns()
        dao.clearGoals()
        dao.clearContributions()
        dao.clearSimulations()
        dao.clearScores()
        saveProfile(
            UserProfile(
                name = "Usuário de teste",
                email = "teste@local.recomeca",
                startDateTimestamp = System.currentTimeMillis()
            )
        )
    }

    // Seed articles
    fun getArticles(): List<EducationalArticle> = EducationalArticlesData.articles

    fun getAchievements(daysClean: Long, currentXp: Int): List<Achievement> {
        return GameQuestionsData.defaultAchievements.map { ach ->
            val unlocked = daysClean >= ach.requiredDaysClean || currentXp >= ach.requiredXp
            ach.copy(isUnlocked = unlocked)
        }
    }
}
