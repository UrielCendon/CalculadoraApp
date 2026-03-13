package com.example.myapplication.ui.calculator

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorKey(
    label: String,
    kind: ButtonKind,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    wide: Boolean = false
) {
    val containerColor = when (kind) {
        ButtonKind.Control -> IphoneLightGray
        ButtonKind.Operator -> IphoneOrange
        ButtonKind.Number -> IphoneDarkGray
    }

    val contentColor = when (kind) {
        ButtonKind.Control -> IphoneControlText
        ButtonKind.Operator -> Color.White
        ButtonKind.Number -> Color.White
    }

    val textSize = when (label) {
        "AC", "+/-" -> 18.sp
        else -> 22.sp
    }

    Button(
        onClick = onClick,
        modifier = modifier.then(
            if (wide) Modifier.height(82.dp) else Modifier.aspectRatio(1f)
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = if (wide) RoundedCornerShape(39.dp) else CircleShape,
        contentPadding = ButtonDefaults.ContentPadding
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Normal,
            fontSize = textSize,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip
        )
    }
}
