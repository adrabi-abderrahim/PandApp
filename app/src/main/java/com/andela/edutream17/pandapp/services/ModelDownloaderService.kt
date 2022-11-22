package com.andela.edutream17.pandapp.services

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.util.Log
import androidx.core.content.getSystemService
import com.andela.edutream17.pandapp.models.DownloadingModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.adrabi.appcustomtfmodel.database.entities.CustomModelEntity
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class ModelDownloaderService private constructor(_context: Context) {
    private val context: Context = _context
    private val firebase = FirebaseModelDownloader.getInstance()
    private val conditions = CustomModelDownloadConditions
        .Builder()
        .requireWifi()
        .build()

    @SuppressLint("Range")
    fun getModel(model: String, period: Long = 300, onDownloading: (DownloadingModel) -> Boolean) {
        //TODO: to be removed
        firebase.deleteDownloadedModel(model)

        firebase.getModelDownloadId(
            model,
            firebase.getModel(
                model,
                DownloadType.LOCAL_MODEL,
                conditions
            ).addOnSuccessListener {
                runBlocking {
                    launch {
                        val modelFile = it?.file
                        if (modelFile != null) {
                            val customModelService = CustomModelService.build(context)
                            customModelService.insertAll(
                                CustomModelEntity(
                                    model,
                                    modelFile.absolutePath
                                )
                            )
                            Log.i("<Downloaded File>", modelFile.absolutePath)
                        }
                    }
                }
            }
        ).addOnSuccessListener {
            val downloadManager: DownloadManager? = context.getSystemService()
            if (it > 0) {
                Log.i("<DownloadModelService>", "The download ID: $it")
                Log.i("<DownloadModelService>", "Timer is created")
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
                            Log.i("<DownloadModelService>", "Timer is cancelled")
                            timer.cancel()
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun build(context: Context) = ModelDownloaderService(context)
    }
}