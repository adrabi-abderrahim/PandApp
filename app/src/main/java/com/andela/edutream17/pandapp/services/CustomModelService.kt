package com.andela.edutream17.pandapp.services

import android.content.Context
import com.andela.edutream17.pandapp.database.PandAppDatabase
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity

class CustomModelService private constructor(context: Context) {
    private val db = PandAppDatabase.build(context)

    suspend fun getAll() = db.customModel().getAll()

    suspend fun get(name: String) = db.customModel().get(name)

    suspend fun insert(name: String, path: String) =
        db.customModel().insert(CustomModelEntity(name, path))

    suspend fun insert(modelEntity: CustomModelEntity) =
        db.customModel().insert(modelEntity)

    suspend fun update(customModel: CustomModelEntity) = db.customModel().update(customModel)

    suspend fun delete(customModel: CustomModelEntity) = db.customModel().delete(customModel)

    companion object {
        fun build(context: Context) = CustomModelService(context)
    }
}