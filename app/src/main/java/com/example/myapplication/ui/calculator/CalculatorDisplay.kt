package com.example.myapplication.ui.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text

@Composable
fun CalculatorDisplay(
    value: String,
    expression: String,
    history: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        history.takeLast(3).forEachIndexed { index, item ->
            val alpha = when (index) {
                0 -> 0.38f
                1 -> 0.56f
                else -> 0.74f
            }

            Text(
                text = item,
                color = IphoneSecondaryText.copy(alpha = alpha),
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("calculator_history_$index")
            )
        }

        Text(
            text = expression,
            color = IphoneSecondaryText,
            fontSize = 20.sp,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("calculator_expression")
        )

        Text(
            text = value,
            color = Color.White,
            fontSize = 72.sp,
            lineHeight = 72.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.End,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("calculator_display")
        )
    }
}
