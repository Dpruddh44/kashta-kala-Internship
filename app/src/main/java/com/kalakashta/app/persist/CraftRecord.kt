package com.kalakashta.app.persist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "crafts")
data class CraftRecord(
    @PrimaryKey(autoGenerate = true) val rid: Int = 0,
    val photoUri: String,
    val caption: String,
    val note: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
