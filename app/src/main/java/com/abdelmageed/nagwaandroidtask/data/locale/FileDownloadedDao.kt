package com.abdelmageed.nagwaandroidtask.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FileDownloadedDao {

    @Query("SELECT * FROM cats ORDER BY block_name ASC")
    suspend fun getCats(): List<FilesDownloadedDatabase>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(catsDatabase: FilesDownloadedDatabase)

    @Query("DELETE FROM cats")
    suspend fun deleteAll()
}