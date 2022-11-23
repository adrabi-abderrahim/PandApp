package com.andela.edutream17.pandapp.services

import android.content.Context
import com.andela.edutream17.pandapp.models.PandAppCustomModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.tensorflow.lite.Interpreter
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder


class ModelInterpreterService(private val context: Context, private val model: PandAppCustomModel) {
    private lateinit var interpreter: Interpreter

    init {
        runBlocking {
            launch {
                val modelService = CustomModelService.build(this@ModelInterpreterService.context)
                this@ModelInterpreterService.interpreter =
                    Interpreter(File(modelService.get(model.name).path))
            }
        }
    }

    fun predict(text: String): String {
        val cleanedText: String = text
            .lowercase()
            .replace("""[\p{P}\p{S}]+""".toRegex(), " ")

        val encoded = mutableListOf<Int>()
        for (s in cleanedText.split(" ")) {
            model.vocabulary[s]?.let { encoded.add(it) }
        }
        for (i in 0 until model.inputSize - encoded.size) {
            encoded.add(0, 0)
        }

        val inputBufferSize = model.inputSize * java.lang.Float.SIZE / java.lang.Byte.SIZE

        val modelInput =
            ByteBuffer.allocateDirect(inputBufferSize).order(ByteOrder.nativeOrder())

        for (c in encoded) {
            modelInput.putFloat(c.toFloat())
        }

        val outputBufferSize = model.outputSize * java.lang.Float.SIZE / java.lang.Byte.SIZE
        val modelOutput =
            ByteBuffer.allocateDirect(outputBufferSize).order(ByteOrder.nativeOrder())
        interpreter.run(modelInput, modelOutput)

        modelOutput.rewind()
        val buff = modelOutput.asFloatBuffer()
        val probabilities = mutableListOf<Float>()
        for (i in 0 until buff.capacity()) {
            probabilities.add(buff[i])
        }
        val argmax = probabilities
            .withIndex()
            .filter { it.value >= model.minAcceptedProbability }
            .maxByOrNull { it.value }?.index ?: -1

        return if (argmax >= 0)
            model.responses[model.uuid[argmax]]!!
        else
            "Sorry, but I cannot understand what do you mean."
    }
}