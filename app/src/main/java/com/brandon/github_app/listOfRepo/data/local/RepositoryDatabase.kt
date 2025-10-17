package com.brandon.github_app.listOfRepo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [RepositoryEntity::class],
    version = 1
)
abstract class RepositoryDatabase : RoomDatabase() {
    abstract val dao: RepositoryDao
}