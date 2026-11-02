package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class ProfileEntity(
    @PrimaryKey val id: String = "local_user",
    val name: String,
    val email: String,
    val avatarUrl: String,
    val startDateTimestamp: Long,
    val avgDailyBet: Double,
    val daysPerWeekBet: Int,
    val avgMonthlyBet: Double,
    val isVip: Boolean,
    val xp: Int,
    val level: Int,
    val emergencyContactName: String,
    val emergencyContactPhone: String,
    val isDemoMode: Boolean,
    val isAdmin: Boolean
)

@Entity(tableName = "daily_checkins")
data class CheckInEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateString: String,
    val timestamp: Long,
    val didBet: Boolean,
    val mood: Int,
    val urgeLevel: Int,
    val note: String
)

@Entity(tableName = "journal_entries")
data class JournalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val mood: String,
    val hadUrge: Boolean,
    val actionTaken: String,
    val smallVictory: String,
    val notes: String,
    val tag: String
)

@Entity(tableName = "financial_goals")
data class GoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val category: String,
    val targetAmount: Double,
    val currentAmount: Double,
    val deadlineDate: String,
    val isCompleted: Boolean
)

@Entity(tableName = "goal_contributions")
data class ContributionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val goalId: Long,
    val amount: Double,
    val dateTimestamp: Long,
    val note: String
)

@Entity(tableName = "financial_simulations")
data class SimulationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val dailyBet: Double,
    val weeklyDays: Int,
    val monthlyEstimate: Double,
    val annualEstimate: Double,
    val threeYearEstimate: Double,
    val fiveYearEstimate: Double,
    val targetObjective: String
)

@Entity(tableName = "game_scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gameId: String,
    val gameName: String,
    val score: Int,
    val moves: Int,
    val durationSeconds: Int,
    val difficulty: String,
    val timestamp: Long
)
