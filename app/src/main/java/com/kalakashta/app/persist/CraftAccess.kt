package com.kalakashta.app.persist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CraftAccess {
    @Query("SELECT * FROM crafts ORDER BY timestamp DESC")
    fun stream(): Flow<List<CraftRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(record: CraftRecord)

    @Delete
    suspend fun remove(record: CraftRecord)
}
