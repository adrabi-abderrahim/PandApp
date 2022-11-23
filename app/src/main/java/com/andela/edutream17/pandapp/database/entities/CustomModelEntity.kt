package com.andela.edutream17.pandapp.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "custom_model")
data class CustomModelEntity(
    @PrimaryKey
    val name: String,

    @ColumnInfo(name = "path")
    var path: String? = null,

    @ColumnInfo(name = "last_message")
    var lastMessage: String? = null,

    @ColumnInfo(name = "date_last_message")
    var dateOfLastMessage: LocalDateTime? = null,

    @ColumnInfo
    var metadata: Metadata? = null
) {
    data class Metadata(
        val label: String,
        val description: String,
        val inputSize: Int,
        val outputSize: Int,
        val minAcceptedProbability: Double,
        val vocabulary: Map<String, Int>,
        val uuid: List<String>,
        val responses: Map<String, String>
    )
}
