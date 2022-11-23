package com.andela.edutream17.pandapp.database.utils

import androidx.room.TypeConverter
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object MetadataConverter {

    @JvmStatic
    @TypeConverter
    fun fromString(json: String?): CustomModelEntity.Metadata? {
        if (json != null) {
            val type = object : TypeToken<CustomModelEntity.Metadata>() {}.type
            return Gson().fromJson(json, type)
        }
        return null
    }

    @JvmStatic
    @TypeConverter
    fun toModel(metadata: CustomModelEntity.Metadata?): String? {
        if (metadata != null) {
            return Gson().toJson(metadata)
        }
        return null
    }
}