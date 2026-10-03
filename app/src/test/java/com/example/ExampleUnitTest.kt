package com.example

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testMolarMassCalculation() {
    val result = com.example.util.ChemistryCalculatorHelper.calculateMolarMass("H2O")
    assertTrue(result.resultFormatted.contains("18.01"))
  }

  @Test
  fun testRewardedAdUnitId() {
    assertEquals(
      "ca-app-pub-3940256099942544/5224354917",
      com.example.util.RewardedAdManager.TEST_AD_UNIT_ID
    )
  }
}
