package com.andela.edutream17.pandapp.models

data class PandAppCustomModel(
    val name: String,
    val label: String,
    val description: String,
    val inputSize: Int,
    val outputSize: Int,
    val minAcceptedProbability: Int,
    val vocabulary: Map<String, Int>,
    val uuid: List<String>,
    val responses: Map<String, String>
)
