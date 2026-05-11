package com.kalakashta.app.persist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [InvoiceRecord::class, CraftRecord::class],
    version = 1,
    exportSchema = false
)
abstract class ArtisanVault : RoomDatabase() {
    abstract fun invoices(): InvoiceAccess
    abstract fun crafts(): CraftAccess

    companion object {
        @Volatile private var instance: ArtisanVault? = null

        fun open(ctx: Context): ArtisanVault =
            instance ?: synchronized(this) {
                Room.databaseBuilder(ctx.applicationContext, ArtisanVault::class.java, "artisan_vault")
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
    }
}
