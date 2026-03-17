package com.example.calculadora.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorDisplay(
    value: String,
    expression: String,
    history: List<String>,
    onClearHistory: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        if (history.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Historial",
                    color = CalcTextDim,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.sp
                )
                IconButton(
                    onClick = onClearHistory,
                    modifier = Modifier.padding(0.dp)
                ) {
                    Text(
                        text = "Limpiar",
                        color = CalcAccent.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                history.takeLast(3).forEachIndexed { index, item ->
                    val alpha = when (index) {
                        0 -> 0.28f
                        1 -> 0.50f
                        else -> 0.72f
                    }
                    Text(
                        text = item,
                        color = CalcTextSecondary.copy(alpha = alpha),
                        fontSize = 14.sp,
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("calculator_history_$index")
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 0.5.dp,
                color = CalcLightGray.copy(alpha = 0.3f)
            )
        }

        // Expresión actual
        if (expression.isNotEmpty()) {
            Text(
                text = expression,
                color = CalcTextSecondary,
                fontSize = 20.sp,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("calculator_expression")
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
        val displayFontSize = when {
            value.length > 12 -> 36.sp
            value.length > 9  -> 50.sp
            value.length > 6  -> 60.sp
            else              -> 72.sp
        }

        Text(
            text = value,
            color = if (value == "Error") CalcOperator else CalcTextPrimary,
            fontSize = displayFontSize,
            lineHeight = displayFontSize,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.End,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("calculator_display")
        )
    }
}
