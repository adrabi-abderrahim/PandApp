package me.adrabi.appcustomtfmodel.database.Dao

import androidx.room.*
import me.adrabi.appcustomtfmodel.database.entities.CustomModelEntity

@Dao
interface CustomModelDao {
    @Query("select * from custom_model")
    suspend fun getAll(): List<CustomModelEntity>

    @Query("select * from custom_model where name=:name limit 1")
    suspend fun get(name: String): CustomModelEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg customModels: CustomModelEntity)

    @Update
    suspend fun update(customModel: CustomModelEntity)

    @Delete
    suspend fun delete(customModel: CustomModelEntity)
}