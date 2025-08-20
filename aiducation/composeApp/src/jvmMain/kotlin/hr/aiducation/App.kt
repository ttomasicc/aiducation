package hr.aiducation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hr.aiducation.commons.CSVCommons
import hr.aiducation.commons.CSVCommons.validateCsvFile
import hr.aiducation.components.AnalysisBox
import hr.aiducation.components.AnalysisHeader
import hr.aiducation.components.Header
import hr.aiducation.data.GlobalSettings
import hr.aiducation.dialogs.ErrorDialog
import hr.aiducation.dialogs.SettingsDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import kotlin.collections.iterator

@Composable
fun App() {
    val scope = rememberCoroutineScope()
    var showSettings by remember { mutableStateOf(false) }
    var csvFileName by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var isAnalysisOn by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var analysisOutput by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Header { showSettings = true }
        Spacer(modifier = Modifier.height(16.dp))
        Text("CSV datoteka mora sadržavati stupce: " + CSVCommons.expectedHeaders.joinToString() + ".")
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                val fd = FileDialog(null as Frame?, "Odaberi CSV datoteku", FileDialog.LOAD)
                fd.isVisible = true
                fd.file?.let { fileName ->
                    val path = "${fd.directory}${File.separator}$fileName"
                    if (fileName.endsWith(".csv", ignoreCase = true)) {
                        val (isValid, error) = validateCsvFile(path)
                        if (isValid) {
                            csvFileName = fileName
                            GlobalSettings.csvFilePath = path
                        } else {
                            errorMessage = error ?: "Nepoznata greška pri validaciji."
                            showError = true
                            csvFileName = ""
                            GlobalSettings.csvFilePath = ""
                        }
                    } else {
                        errorMessage = "Molimo odaberite CSV datoteku!"
                        showError = true
                    }
                }
            }) {
                Text("Učitaj CSV datoteku")
            }

            if (csvFileName.isNotEmpty()) {
                Spacer(modifier = Modifier.width(12.dp))
                Text("Odabrana datoteka: $csvFileName")
            }
        }

        if (csvFileName.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    csvFileName = ""
                    GlobalSettings.csvFilePath = ""
                    analysisOutput = ""
                }) {
                    Text("Obriši")
                }

                Button(
                    onClick = {
                        if (isAnalysisOn) {
                            return@Button
                        }
                        scope.launch {
                            isAnalysisOn = true
                            analysisOutput = ""
                            val analysis = CSVCommons.readAndGroupByStudent(GlobalSettings.csvFilePath)
                            for (record in analysis) {
                                val response = withContext(Dispatchers.IO) {
                                    GeminiClient.generateAnalysis(record.value) + """
                                        | 
                                        | 
                                        | 
                                    """.trimMargin()
                                }
                                analysisOutput = analysisOutput.dropLast(3)
                                analysisOutput += response
                            }
                            isAnalysisOn = false
                        }
                    },
                    enabled = GlobalSettings.apiKey.isNotEmpty()
                ) {
                    Text("Pokreni analizu")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        AnalysisHeader(analysisOutput)
        if (isAnalysisOn) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 0.dp,
                        bottom = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // text left, button right
            ) {
                Text("\uD83D\uDD0E Analiza pokrenuta...")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        AnalysisBox(analysisOutput = analysisOutput)
    }

    if (showSettings) {
        SettingsDialog(onClose = { showSettings = false })
    }

    if (showError) {
        ErrorDialog(message = errorMessage, onClose = { showError = false })
    }
}
