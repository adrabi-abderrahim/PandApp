package me.adrabi.appcustomtfmodel.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "custom_model")
data class CustomModelEntity(
    @PrimaryKey
    val name: String,
    @ColumnInfo(name = "path")
    val path: String
)
