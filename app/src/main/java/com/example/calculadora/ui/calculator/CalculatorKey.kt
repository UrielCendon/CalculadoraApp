package com.example.calculadora.ui.calculator

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
        ButtonKind.Control  -> CalcControl
        ButtonKind.Operator -> CalcOperator
        ButtonKind.Number   -> CalcDarkGray
        ButtonKind.Special  -> CalcSpecial
    }

    val contentColor = when (kind) {
        ButtonKind.Control  -> CalcControlText
        ButtonKind.Operator -> CalcOperatorText
        ButtonKind.Number   -> CalcTextPrimary
        ButtonKind.Special  -> CalcSpecialText
    }

    val textSize = when {
        label in listOf("AC", "+/-", "√", "⌫") -> 16.sp
        label == "%" -> 20.sp
        else -> 22.sp
    }

    val fontWeight = when (kind) {
        ButtonKind.Operator -> FontWeight.Medium
        ButtonKind.Special  -> FontWeight.SemiBold
        else                -> FontWeight.Normal
    }

    Button(
        onClick = onClick,
        modifier = modifier.then(
            if (wide) Modifier.height(78.dp) else Modifier.aspectRatio(1f)
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = if (wide) RoundedCornerShape(39.dp) else CircleShape,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = fontWeight,
            fontSize = textSize,
            maxLines = 1,
            softWrap = false,
            overflow = TextOverflow.Clip
        )
    }
}
