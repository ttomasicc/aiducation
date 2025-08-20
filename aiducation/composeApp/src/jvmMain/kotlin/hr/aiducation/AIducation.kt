package hr.aiducation

import aiducation.composeapp.generated.resources.Res
import aiducation.composeapp.generated.resources.logo
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.painterResource

fun main() =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "AIducation",
            icon = painterResource(Res.drawable.logo)
        ) {
            MaterialTheme {
                App()
            }
        }
    }
