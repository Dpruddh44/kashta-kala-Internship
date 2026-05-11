package com.kalakashta.app.persist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "invoices")
data class InvoiceRecord(
    @PrimaryKey(autoGenerate = true) val rid: Int = 0,
    val clientName: String,
    val itemLabel: String,
    val timber: String,
    val spec: String,
    val sqft: Double,
    val materialTotal: Double,
    val labourTotal: Double,
    val margin: Double,
    val grandTotal: Double,
    val timestamp: Long = System.currentTimeMillis()
)
