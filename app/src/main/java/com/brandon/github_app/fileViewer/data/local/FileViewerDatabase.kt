package com.brandon.github_app.fileViewer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FileViewerEntity::class],
    version = 1
)
abstract class FileViewerDatabase : RoomDatabase(){
    abstract val dao: FileViewerDao
}