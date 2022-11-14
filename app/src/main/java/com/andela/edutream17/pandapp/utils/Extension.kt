package com.andela.edutream17.pandapp.utils

import java.util.*


object Extension {
    fun String.capitalized(): String {
        return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}