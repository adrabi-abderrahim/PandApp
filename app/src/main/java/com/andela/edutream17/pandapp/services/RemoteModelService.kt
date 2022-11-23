package com.andela.edutream17.pandapp.services

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import androidx.core.content.getSystemService
import com.andela.edutream17.pandapp.database.entities.CustomModelEntity
import com.andela.edutream17.pandapp.models.DownloadingModel
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class RemoteModelService private constructor(_context: Context) {
    private val context: Context = _context
    private val firebase = FirebaseModelDownloader.getInstance()
    private val conditions = CustomModelDownloadConditions
        .Builder()
        .requireWifi()
        .build()

    @SuppressLint("Range")
    fun getModel(
        modelName: String,
        period: Long = 300,
        onDownloading: (DownloadingModel) -> Boolean
    ) {
        //TODO: to be removed
        firebase.deleteDownloadedModel(modelName)

        firebase.getModelDownloadId(
            modelName,
            firebase.getModel(
                modelName,
                DownloadType.LOCAL_MODEL,
                conditions
            ).addOnSuccessListener {
                val modelFile = it?.file
                if (modelFile != null) {
                    runBlocking {
                        launch {
                            val customModelService = CustomModelService.build(context)
                            val modelEntity = getModelMetadata(modelName)
                            modelEntity.path = modelFile.absolutePath
                            customModelService.insert(modelEntity)
                        }
                    }
                }
            }
        ).addOnSuccessListener {
            val downloadManager: DownloadManager? = context.getSystemService()
            if (it > 0) {
                val timer = Timer()
                timer.scheduleAtFixedRate(0, period) {
                    val cursor = downloadManager?.query(DownloadManager.Query().setFilterById(it))
                    if (cursor?.moveToFirst() == true) {
                        if (!onDownloading(
                                DownloadingModel(
                                    status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)),
                                    total = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)),
                                    downloaded = cursor.getLong(
                                        cursor.getColumnIndex(
                                            DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR
                                        )
                                    )
                                )
                            )
                        ) {
                            timer.cancel()
                        }
                    }
                }
            }
        }
    }

    suspend fun getAllModelsMetadata(): List<CustomModelEntity> {
        return Firebase.firestore
            .collection("ml-custom-model")
            .get()
            .await()
            .documents.map {
                documentToModel(it)
            }
    }

    suspend fun getModelMetadata(modelName: String): CustomModelEntity {
        val document = Firebase
            .firestore
            .collection("ml-custom-model")
            .whereEqualTo("name", modelName)
            .limit(1)
            .get()
            .await()
            .first()

        return documentToModel(document)
    }

    private fun documentToModel(document: DocumentSnapshot): CustomModelEntity {
        return CustomModelEntity(
            name = document["name"] as String,
            metadata = CustomModelEntity.Metadata(
                label = document["label"] as String,
                description = document["description"] as String,
                inputSize = (document["input-size"] as Long).toInt(),
                outputSize = (document["output-size"] as Long).toInt(),
                minAcceptedProbability = document["min-accepted-probability"] as Double,
                vocabulary = document.get("vocabulary") as Map<String, Int>,
                uuid = document["uuid"] as List<String>,
                responses = document["responses"] as Map<String, String>
            )
        )
    }

    companion object {
        fun build(context: Context) = RemoteModelService(context)
    }
}