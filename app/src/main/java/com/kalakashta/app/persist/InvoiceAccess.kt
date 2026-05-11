package com.kalakashta.app.persist

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceAccess {
    @Query("SELECT * FROM invoices ORDER BY timestamp DESC")
    fun stream(): Flow<List<InvoiceRecord>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(record: InvoiceRecord)

    @Delete
    suspend fun remove(record: InvoiceRecord)
}
