package hr.aiducation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection

@Composable
fun AnalysisHeader(analysisOutput: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // text left, button right
    ) {
        Text(
            text = "Rezultati analize",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        if (analysisOutput.isNotEmpty()) {
            Button(onClick = {
                val selection = StringSelection(analysisOutput)
                Toolkit.getDefaultToolkit().systemClipboard.setContents(selection, selection)
            }) {
                Text("Kopiraj")
            }
        }
    }
}
