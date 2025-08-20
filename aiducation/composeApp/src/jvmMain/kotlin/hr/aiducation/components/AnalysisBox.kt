package hr.aiducation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography

val markdownColors = DefaultMarkdownColors(
    text = Color(0xFF2E2E2E),                 // dark gray body text
    codeText = Color(0xFFD84315),             // reddish code
    inlineCodeText = Color(0xFFD84315),
    linkText = Color(0xFFFBC02D),             // golden yellow links
    codeBackground = Color(0xFFFFFDE7),       // very light yellow for code block
    inlineCodeBackground = Color(0xFFFFF9C4), // pale yellow for inline code
    dividerColor = Color(0xFFE0E0E0),         // subtle gray divider
    tableText = Color(0xFF2E2E2E),
    tableBackground = Color(0xFFFFFDE7)
)

val markdownLinkStyles = TextLinkStyles(
    style = SpanStyle(
        color = Color(0xFFFBC02D),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    focusedStyle = SpanStyle(
        color = Color(0xFFFFF176),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    hoveredStyle = SpanStyle(
        color = Color(0xFFFFD54F),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    pressedStyle = SpanStyle(
        color = Color(0xFFF57F17),
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)

val markdownTypography = DefaultMarkdownTypography(
    h1 = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6D4C41)), // brown
    h2 = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF6D4C41)),
    h3 = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, color = Color(0xFF6D4C41)),
    h4 = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Color(0xFF6D4C41)),
    h5 = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF6D4C41)),
    h6 = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color(0xFF6D4C41)),

    text = TextStyle(fontSize = 16.sp, color = Color(0xFF2E2E2E)),
    paragraph = TextStyle(fontSize = 16.sp, lineHeight = 22.sp, color = Color(0xFF2E2E2E)),
    quote = TextStyle(fontSize = 16.sp, fontStyle = FontStyle.Italic, color = Color(0xFF757575)),

    code = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily.Monospace,
        background = Color(0xFFFFFDE7),
        color = Color(0xFFD84315)
    ),
    inlineCode = TextStyle(
        fontSize = 14.sp,
        fontFamily = FontFamily.Monospace,
        background = Color(0xFFFFF9C4),
        color = Color(0xFFD84315)
    ),

    ordered = TextStyle(fontSize = 16.sp, color = Color(0xFF2E2E2E)),
    bullet = TextStyle(fontSize = 16.sp, color = Color(0xFF2E2E2E)),
    list = TextStyle(fontSize = 16.sp, color = Color(0xFF2E2E2E)),

    link = TextStyle(fontSize = 16.sp, color = Color(0xFFFBC02D), fontWeight = FontWeight.Medium),
    textLink = markdownLinkStyles,
    table = TextStyle(fontSize = 16.sp, color = Color(0xFF2E2E2E))
)

@Composable
fun AnalysisBox(analysisOutput: String) {
    if (analysisOutput.isEmpty()) {
        return
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                color = Color(0xFFFFFEF5),               // very light creamy yellow, almost white
                shape = RoundedCornerShape(12.dp)        // rounded corners
            )
            .border(
                width = 3.dp,
                color = Color(0xFFFFF9E0),               // slightly deeper, but still soft yellow border
                shape = RoundedCornerShape(12.dp)
            )
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        Markdown(
            content = analysisOutput,
            colors = markdownColors,
            typography = markdownTypography,
        )
    }
}
