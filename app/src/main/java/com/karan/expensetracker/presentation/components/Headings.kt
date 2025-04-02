package com.karan.expensetracker.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LgHeading(modifier: Modifier = Modifier , text : String = "LgHeading") {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            fontSize = 32.sp
        ),

    )

}

@Preview
@Composable
fun LgHeadingPreview() {
    LgHeading()
}

@Composable
fun MdHeading(modifier: Modifier = Modifier) {
    // Content for MdHeading
}

@Preview
@Composable
fun MdHeadingPreview() {
    MdHeading()
}

@Composable
fun SmHeading(modifier: Modifier = Modifier) {
    // Content for SmHeading
}

@Preview
@Composable
fun SmHeadingPreview() {
    SmHeading()
}

@Composable
fun LgText(modifier: Modifier = Modifier) {
    // Content for LgText
}

@Preview
@Composable
fun LgTextPreview() {
    LgText()
}

@Composable
fun MdText(modifier: Modifier = Modifier) {
    // Content for MdText
}

@Preview
@Composable
fun MdTextPreview() {
    MdText()
}

@Composable
fun SmText(modifier: Modifier = Modifier) {
    // Content for SmText
}

@Preview
@Composable
fun SmTextPreview() {
    SmText()
}