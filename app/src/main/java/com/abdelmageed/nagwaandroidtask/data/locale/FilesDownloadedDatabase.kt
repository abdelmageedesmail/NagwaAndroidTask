package com.abdelmageed.nagwaandroidtask.data.locale

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cats")
class FilesDownloadedDatabase(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    @ColumnInfo(name = "block_name") val block_name: String
)