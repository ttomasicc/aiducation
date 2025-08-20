package hr.aiducation.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object GlobalSettings {
    var apiKey: String by mutableStateOf("")
    var csvFilePath: String by mutableStateOf("")
}