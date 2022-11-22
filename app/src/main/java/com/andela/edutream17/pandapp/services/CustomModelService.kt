package com.andela.edutream17.pandapp.services

import android.content.Context
import com.andela.edutream17.pandapp.database.PandAppDatabase
import me.adrabi.appcustomtfmodel.database.entities.CustomModelEntity

class CustomModelService private constructor(context: Context) {
    private val db = PandAppDatabase.build(context)

    suspend fun getAll() = db.customModel().getAll()

    suspend fun get(name: String) = db.customModel().get(name)

    suspend fun insertAll(vararg customModels: CustomModelEntity) =
        db.customModel().insertAll(*customModels)

    suspend fun update(customModel: CustomModelEntity) = db.customModel().update(customModel)

    suspend fun delete(customModel: CustomModelEntity) = db.customModel().delete(customModel)

    companion object {
        fun build(context: Context) = CustomModelService(context)
    }
}