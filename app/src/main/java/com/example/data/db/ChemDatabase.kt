package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
  entities = [LabNotebookEntryEntity::class, UserProgressEntity::class],
  version = 1,
  exportSchema = false
)
abstract class ChemDatabase : RoomDatabase() {
  abstract fun labDao(): LabDao

  companion object {
    @Volatile
    private var INSTANCE: ChemDatabase? = null

    fun getDatabase(context: Context): ChemDatabase {
      return INSTANCE ?: synchronized(this) {
        val instance = Room.databaseBuilder(
          context.applicationContext,
          ChemDatabase::class.java,
          "chemlab_database"
        )
          .fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
        instance
      }
    }
  }
}
