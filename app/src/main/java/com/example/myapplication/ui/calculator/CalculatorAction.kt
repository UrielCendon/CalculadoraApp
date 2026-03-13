package com.example.myapplication.ui.calculator

sealed interface CalculatorAction {
    data class Digit(val value: String) : CalculatorAction
    data object Decimal : CalculatorAction
    data class Operator(val value: String) : CalculatorAction
    data object Equals : CalculatorAction
    data object Clear : CalculatorAction
    data object Percent : CalculatorAction
    data object ToggleSign : CalculatorAction
}
