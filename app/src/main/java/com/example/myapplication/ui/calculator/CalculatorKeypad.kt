package com.example.myapplication.ui.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CalculatorKeypad(
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    CalculatorButtonRow(
        buttons = listOf(
            CalculatorButtonUi("AC", ButtonKind.Control, CalculatorAction.Clear),
            CalculatorButtonUi("+/-", ButtonKind.Control, CalculatorAction.ToggleSign),
            CalculatorButtonUi("%", ButtonKind.Control, CalculatorAction.Percent),
            CalculatorButtonUi("÷", ButtonKind.Operator, CalculatorAction.Operator("/"))
        ),
        onAction = onAction,
        modifier = modifier
    )

    CalculatorButtonRow(
        buttons = listOf(
            CalculatorButtonUi("7", ButtonKind.Number, CalculatorAction.Digit("7")),
            CalculatorButtonUi("8", ButtonKind.Number, CalculatorAction.Digit("8")),
            CalculatorButtonUi("9", ButtonKind.Number, CalculatorAction.Digit("9")),
            CalculatorButtonUi("×", ButtonKind.Operator, CalculatorAction.Operator("x"))
        ),
        onAction = onAction
    )

    CalculatorButtonRow(
        buttons = listOf(
            CalculatorButtonUi("4", ButtonKind.Number, CalculatorAction.Digit("4")),
            CalculatorButtonUi("5", ButtonKind.Number, CalculatorAction.Digit("5")),
            CalculatorButtonUi("6", ButtonKind.Number, CalculatorAction.Digit("6")),
            CalculatorButtonUi("−", ButtonKind.Operator, CalculatorAction.Operator("-"))
        ),
        onAction = onAction
    )

    CalculatorButtonRow(
        buttons = listOf(
            CalculatorButtonUi("1", ButtonKind.Number, CalculatorAction.Digit("1")),
            CalculatorButtonUi("2", ButtonKind.Number, CalculatorAction.Digit("2")),
            CalculatorButtonUi("3", ButtonKind.Number, CalculatorAction.Digit("3")),
            CalculatorButtonUi("+", ButtonKind.Operator, CalculatorAction.Operator("+"))
        ),
        onAction = onAction
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        CalculatorKey(
            label = "0",
            kind = ButtonKind.Number,
            modifier = Modifier.weight(2f),
            onClick = { onAction(CalculatorAction.Digit("0")) },
            wide = true
        )
        CalculatorKey(
            label = ".",
            kind = ButtonKind.Number,
            modifier = Modifier.weight(1f),
            onClick = { onAction(CalculatorAction.Decimal) }
        )
        CalculatorKey(
            label = "=",
            kind = ButtonKind.Operator,
            modifier = Modifier.weight(1f),
            onClick = { onAction(CalculatorAction.Equals) }
        )
    }
}

@Composable
fun CalculatorButtonRow(
    buttons: List<CalculatorButtonUi>,
    onAction: (CalculatorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        buttons.forEach { button ->
            CalculatorKey(
                label = button.label,
                kind = button.kind,
                modifier = Modifier.weight(1f),
                onClick = { onAction(button.action) }
            )
        }
    }
}
