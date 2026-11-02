package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.UserProfile
import com.example.data.seed.EducationalArticlesData
import com.example.data.seed.GameQuestionsData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun `read app_name string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Recomeça", appName)
  }

  @Test
  fun `new demo profile does not invent recovery or financial history`() {
    val profile = UserProfile()
    assertTrue("A new profile should start at zero days", profile.daysClean == 0L)
    assertEquals("A new profile should not claim saved money", 0.0, profile.estimatedMoneySaved, 0.001)
    assertFalse("A new profile must not be VIP", profile.isVip)
    assertFalse("A new profile must not be admin", profile.isAdmin)
    assertTrue("A new profile must be clearly marked as demo", profile.isDemoMode)
    assertTrue("No emergency contact should be invented", profile.emergencyContactPhone.isBlank())
  }

  @Test
  fun `verify financial calculations for saved money`() {
    val profile = UserProfile(
      startDateTimestamp = System.currentTimeMillis() - (10L * 24 * 60 * 60 * 1000),
      avgDailyBet = 50.0,
      daysPerWeekBet = 7
    )
    assertTrue("Should have 10 days clean", profile.daysClean in 9L..11L)
    assertTrue("Should have approximately R$ 500 saved", profile.estimatedMoneySaved >= 450.0)
  }

  @Test
  fun `verify educational articles catalog completeness`() {
    val articles = EducationalArticlesData.articles
    assertTrue("Should have at least 25 structured educational articles", articles.size >= 25)
    articles.forEach { article ->
      assertTrue("Article title cannot be empty", article.title.isNotBlank())
      assertTrue("Article mainText cannot be empty", article.mainText.isNotBlank())
      assertTrue("Article realExample cannot be empty", article.realExample.isNotBlank())
    }
  }

  @Test
  fun `verify quiz questions repository has questions and valid answers`() {
    val questions = GameQuestionsData.quizQuestions
    assertTrue("Quiz questions should exist", questions.isNotEmpty())
    questions.forEach { q ->
      assertTrue("Question options should contain 4 items", q.options.size == 4)
      assertTrue("Correct index should be within 0..3", q.correctIndex in 0..3)
      assertTrue("Explanation should be provided", q.explanation.isNotBlank())
    }
  }
}
