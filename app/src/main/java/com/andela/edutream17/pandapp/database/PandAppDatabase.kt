package com.andela.edutream17.pandapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.adrabi.appcustomtfmodel.database.Dao.CustomModelDao
import me.adrabi.appcustomtfmodel.database.entities.CustomModelEntity

@Database(entities = [CustomModelEntity::class], version = 1)
abstract class PandAppDatabase : RoomDatabase() {
    abstract fun customModel(): CustomModelDao

    companion object {
        private var INSTANCE: PandAppDatabase? = null

        fun build(context: Context): PandAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PandAppDatabase::class.java,
                    "pandapp"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}