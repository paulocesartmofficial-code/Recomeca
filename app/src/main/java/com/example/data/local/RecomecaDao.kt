package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface RecomecaDao {

    // Profile
    @Query("SELECT * FROM user_profile WHERE id = :id LIMIT 1")
    fun getProfile(id: String = "local_user"): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveProfile(profile: ProfileEntity)

    // Check-ins
    @Query("SELECT * FROM daily_checkins ORDER BY timestamp DESC")
    fun getAllCheckIns(): Flow<List<CheckInEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckIn(checkIn: CheckInEntity): Long

    // Journal
    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAllJournalEntries(): Flow<List<JournalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(entry: JournalEntity): Long

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteJournalById(id: Long)

    // Goals
    @Query("SELECT * FROM financial_goals ORDER BY isCompleted ASC, id DESC")
    fun getAllGoals(): Flow<List<GoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: GoalEntity): Long

    @Update
    suspend fun updateGoal(goal: GoalEntity)

    @Query("DELETE FROM financial_goals WHERE id = :id")
    suspend fun deleteGoalById(id: Long)

    // Contributions
    @Query("SELECT * FROM goal_contributions WHERE goalId = :goalId ORDER BY dateTimestamp DESC")
    fun getContributionsForGoal(goalId: Long): Flow<List<ContributionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContribution(contrib: ContributionEntity): Long

    // Simulations
    @Query("SELECT * FROM financial_simulations ORDER BY timestamp DESC")
    fun getAllSimulations(): Flow<List<SimulationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimulation(simulation: SimulationEntity): Long

    @Query("DELETE FROM financial_simulations WHERE id = :id")
    suspend fun deleteSimulationById(id: Long)

    // Game Scores
    @Query("SELECT * FROM game_scores ORDER BY timestamp DESC")
    fun getAllScores(): Flow<List<ScoreEntity>>

    @Query("SELECT * FROM game_scores WHERE gameId = :gameId ORDER BY score DESC, moves ASC LIMIT 10")
    fun getTopScoresForGame(gameId: String): Flow<List<ScoreEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScore(score: ScoreEntity): Long

    // Clear all for LGPD deletion
    @Query("DELETE FROM journal_entries")
    suspend fun clearJournal()

    @Query("DELETE FROM daily_checkins")
    suspend fun clearCheckIns()

    @Query("DELETE FROM financial_goals")
    suspend fun clearGoals()

    @Query("DELETE FROM goal_contributions")
    suspend fun clearContributions()

    @Query("DELETE FROM financial_simulations")
    suspend fun clearSimulations()

    @Query("DELETE FROM game_scores")
    suspend fun clearScores()
}
