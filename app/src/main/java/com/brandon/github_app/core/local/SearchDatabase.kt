package com.brandon.github_app.core.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SearchEntity::class],
    version = 1
)
abstract class SearchDatabase : RoomDatabase() {
    abstract val dao: SearchDao
}