package hr.aiducation.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogWindow
import hr.aiducation.GeminiClient
import hr.aiducation.data.GlobalSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingsDialog(onClose: () -> Unit) {
    var localApiKey by remember { mutableStateOf(GlobalSettings.apiKey) }
    var showMessage by remember { mutableStateOf(false) }
    var messageText by remember { mutableStateOf("") }
    var messageColor by remember { mutableStateOf(Color.Black) }
    val scope = rememberCoroutineScope()

    fun showTemporaryMessage(text: String, color: Color = Color(0xFF2E7D32)) {
        messageText = text
        messageColor = color
        showMessage = true
        scope.launch {
            delay(3000)
            showMessage = false
        }
    }

    DialogWindow(onCloseRequest = onClose, title = "Postavke") {
        Column(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = localApiKey,
                onValueChange = { localApiKey = it },
                label = { Text("API ključ") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )

            if (showMessage) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = messageText,
                    color = messageColor
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        localApiKey = ""
                        GlobalSettings.apiKey = ""
                        showTemporaryMessage("🗑️ Uspješno obrisano")
                    },
                    enabled = GlobalSettings.apiKey.isNotEmpty()
                ) { Text("Obriši") }

                Button(
                    onClick = {
                        GlobalSettings.apiKey = localApiKey
                        showTemporaryMessage("✅ Uspješno postavljeno")
                    },
                    enabled = localApiKey.isNotEmpty() && localApiKey != GlobalSettings.apiKey
                ) { Text("Postavi") }

                Button(
                    onClick = {
                        if (GeminiClient.testApi()) {
                            showTemporaryMessage("✅ API ključ je ispravan")
                        } else {
                            showTemporaryMessage("❌ API ključ nije ispravan", Color.Red)
                        }
                    },
                    enabled = GlobalSettings.apiKey.isNotEmpty()
                ) { Text("Testiraj") }
            }
        }
    }
}
