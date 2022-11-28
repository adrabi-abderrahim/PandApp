package com.andela.edutream17.pandapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andela.edutream17.pandapp.database.entities.ChatHistoryEntity

@Dao
interface ChatHistoryDao {
    @Query("select * from virtual_peer")
    suspend fun getAll(): List<ChatHistoryEntity>

    @Query("select * from virtual_peer order by message_date desc limit 1")
    suspend fun getLastMessage(): ChatHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chatHistoryEntity: ChatHistoryEntity)

    @Query("delete from virtual_peer where model_name = :name")
    suspend fun deleteByModel(name: String)
}