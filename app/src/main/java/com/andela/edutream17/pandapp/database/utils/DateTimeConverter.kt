package com.andela.edutream17.pandapp.database.utils

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

object DateTimeConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): LocalDateTime? {
        return if (timestamp == null) null else LocalDateTime
            .ofInstant(
                Instant.ofEpochMilli(timestamp),
                TimeZone.getDefault().toZoneId()
            )
    }

    @TypeConverter
    fun toTimestamp(date: LocalDateTime?): Long? {
        if (date != null) {
            val zone = ZonedDateTime.of(date, ZoneId.systemDefault())
            return zone.toInstant().toEpochMilli()
        }
        return null
    }
}