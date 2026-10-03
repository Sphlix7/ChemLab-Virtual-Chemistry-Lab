package com.example.data

import com.example.model.BadgeItem
import com.example.model.DailyFact

object GamificationData {
  val badges = listOf(
    BadgeItem("badge_first_exp", "First Experiment", "🧪", "Completed your first interactive chemistry experiment in ChemLab.", 20, true),
    BadgeItem("badge_explorer", "Lab Explorer", "🔬", "Explored multiple laboratory apparatus and reaction simulations.", 100, false),
    BadgeItem("badge_titration", "Titration Pro", "⚗️", "Mastered stoichiometric neutralization and endpoint detection.", 150, false),
    BadgeItem("badge_quiz_champ", "Quiz Champion", "🧠", "Demonstrated chemistry mastery with high quiz accuracy.", 200, false),
    BadgeItem("badge_safety", "Safety First", "🛡️", "Studied hazard pictograms and safe chemical handling rules.", 80, true),
    BadgeItem("badge_elements", "Element Scholar", "📖", "Investigated element atomic properties and Bohr electron shells.", 120, false),
    BadgeItem("badge_streak", "7-Day Streak", "🔥", "Maintained an active weekly chemistry learning streak.", 300, false),
    BadgeItem("badge_master", "Chemistry Master", "🏆", "Reached advanced chemist rank with over 500 total XP points.", 500, false)
  )

  val dailyFacts = listOf(
    DailyFact(
      1,
      "Petrichor & Geosmin",
      "The earthy scent of rain hitting dry soil is caused by geosmin, an organic bicyclic alcohol produced by Actinomyces bacteria. Human noses are so sensitive to geosmin that we can detect it at just 5 parts per trillion!",
      "🌧️"
    ),
    DailyFact(
      2,
      "Solar Helium Discovery",
      "Helium was discovered in the spectrum of the Sun during the 1868 solar eclipse 27 years before it was ever isolated on Earth, named after Helios, the Greek sun god.",
      "☀️"
    ),
    DailyFact(
      3,
      "Gallium Melts in Your Hand",
      "Gallium is a lustrous silvery metal with an extraordinarily low melting point of 29.76°C (85.57°F). A solid piece of gallium will liquefy into a liquid mirror simply from body heat in your palm!",
      "🌡️"
    ),
    DailyFact(
      4,
      "The Unreactive Noble Gas",
      "Argon derives from the Greek word 'argos' meaning 'lazy' or 'idle' because it stubbornly refuses to form stable chemical bonds under standard atmospheric conditions.",
      "💤"
    ),
    DailyFact(
      5,
      "Pencil Carbon in You",
      "The human body contains approximately 18.5% carbon by mass. An average adult has enough elemental carbon to manufacture approximately 900 standard graphite pencils!",
      "✏️"
    )
  )

  fun getChemistRank(xp: Int): String {
    return when {
      xp >= 500 -> "Master Chemist 🏆"
      xp >= 350 -> "Reaction Specialist ⚗️"
      xp >= 200 -> "Junior Chemist 🔬"
      xp >= 100 -> "Apprentice Alchemist 🧪"
      else -> "Lab Novice 🌱"
    }
  }

  fun getNextRankThreshold(xp: Int): Pair<String, Int> {
    return when {
      xp < 100 -> "Apprentice Alchemist" to 100
      xp < 200 -> "Junior Chemist" to 200
      xp < 350 -> "Reaction Specialist" to 350
      xp < 500 -> "Master Chemist" to 500
      else -> "Grand Alchemist" to 1000
    }
  }
}
