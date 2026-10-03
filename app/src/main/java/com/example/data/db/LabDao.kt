package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LabDao {
  @Query("SELECT * FROM lab_notebook ORDER BY timestamp DESC")
  fun getAllNotebookEntries(): Flow<List<LabNotebookEntryEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertNotebookEntry(entry: LabNotebookEntryEntity): Long

  @Query("DELETE FROM lab_notebook WHERE id = :id")
  suspend fun deleteNotebookEntry(id: Long)

  @Query("SELECT * FROM user_progress WHERE id = 1 LIMIT 1")
  fun getUserProgress(): Flow<UserProgressEntity?>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOrUpdateProgress(progress: UserProgressEntity)
}
