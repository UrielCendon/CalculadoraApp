package com.example.calculadora.ui.calculator

data class CalculatorButtonUi(
    val label: String,
    val kind: ButtonKind,
    val action: CalculatorAction
)

enum class ButtonKind {
    Number,
    Control,
    Operator,
    Special
}

data class CalculatorUiState(
    val display: String = "0",
    val expression: String = "",
    val history: List<String> = emptyList(),
    val currentNumber: String = "0",
    val previousNumber: Double? = null,
    val currentOperator: String? = null,
    val restartDisplay: Boolean = false
)
