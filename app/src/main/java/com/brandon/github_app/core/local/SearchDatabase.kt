package com.brandon.github_app.core.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchEntity::class],
    version = 2,
    exportSchema = false
)
abstract class SearchDatabase : RoomDatabase() {
    abstract val dao: SearchDao
}