package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "lab_notebook")
data class LabNotebookEntryEntity(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val timestamp: Long = System.currentTimeMillis(),
  val experimentTitle: String,
  val chemicalsUsed: String,
  val observations: String,
  val tempCelsius: Double = 25.0,
  val phMeasured: Double = 7.0,
  val notes: String = ""
)

@Entity(tableName = "user_progress")
data class UserProgressEntity(
  @PrimaryKey
  val id: Int = 1,
  val xp: Int = 120,
  val streakDays: Int = 3,
  val completedExperimentsJson: String = "[\"exp_1\"]",
  val viewedTopicsJson: String = "[\"note_acids\"]",
  val unlockedBadgesJson: String = "[\"badge_first_exp\"]",
  val lastActiveDate: Long = System.currentTimeMillis()
)
