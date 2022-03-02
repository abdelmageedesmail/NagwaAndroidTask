package com.abdelmageed.nagwaandroidtask.data.locale

import android.content.Context
import androidx.room.*

@Database(
    entities = [FilesDownloadedDatabase::class],
    version = 1,
    exportSchema = false
)

public abstract class FileDownloadedRoomDataBase : RoomDatabase() {
    abstract fun catDao(): FileDownloadedDao

    companion object {
        @Volatile
        private var INSTANCE: FileDownloadedRoomDataBase? = null
        fun getDatabase(context: Context): FileDownloadedRoomDataBase {
            return (INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FileDownloadedRoomDataBase::class.java,
                    "cats"
                ).build()
                INSTANCE = instance
                instance
            })
        }
    }
}