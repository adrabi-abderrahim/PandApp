package com.andela.edutream17.pandapp.models


data class DownloadingModel(
    val status: Int,
    val total: Long,
    val downloaded: Long
)