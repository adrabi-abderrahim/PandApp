package com.andela.edutream17.pandapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.andela.edutream17.pandapp.database.utils.DateTimeConverter
import com.andela.edutream17.pandapp.database.utils.MetadataConverter
import me.adrabi.appcustomtfmodel.database.Dao.CustomModelDao

@TypeConverters(DateTimeConverter::class, MetadataConverter::class)
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