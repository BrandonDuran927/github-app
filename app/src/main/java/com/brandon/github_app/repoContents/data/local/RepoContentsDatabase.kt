package com.brandon.github_app.repoContents.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [RepoContentEntity::class],
    version = 2
)
abstract class RepoContentsDatabase: RoomDatabase() {
    abstract val dao: RepoContentsDao
}