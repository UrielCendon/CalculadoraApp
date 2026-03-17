package com.example.calculadora.ui.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora.Calculadora
import com.example.calculadora.ui.theme.CalculadoraTheme

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    val calculadora = remember { Calculadora() }
    var uiState by remember { mutableStateOf(CalculatorUiState()) }

    fun executeOperation(a: Double, b: Double, operator: String): String {
        val result = when (operator) {
            "+" -> calculadora.suma(a, b)
            "-" -> calculadora.resta(a, b)
            "x" -> calculadora.multiplicacion(a, b)
            "/" -> calculadora.division(a, b)
            else -> b
        }
        return calculadora.formatearResultado(result)
    }

    fun visibleSymbol(operator: String): String {
        return when (operator) {
            "/" -> "÷"
            "x" -> "×"
            "-" -> "−"
            else -> operator
        }
    }

    fun pushHistory(operation: String) {
        uiState = uiState.copy(history = (uiState.history + operation).takeLast(5))
    }

    fun handleAction(action: CalculatorAction) {
        when (action) {

            is CalculatorAction.Digit -> {
                val nextNumber =
                    if (uiState.currentNumber == "0" || uiState.restartDisplay || uiState.display == "Error") {
                        action.value
                    } else {
                        uiState.currentNumber + action.value
                    }
                uiState = uiState.copy(
                    currentNumber = nextNumber,
                    display = nextNumber,
                    restartDisplay = false
                )
            }

            CalculatorAction.Decimal -> {
                val nextNumber = when {
                    uiState.display == "Error" || uiState.restartDisplay -> "0."
                    uiState.currentNumber.contains(".") -> uiState.currentNumber
                    else -> "${uiState.currentNumber}."
                }
                uiState = uiState.copy(
                    currentNumber = nextNumber,
                    display = nextNumber,
                    restartDisplay = false
                )
            }

            is CalculatorAction.Operator -> {
                var nextState = uiState
                if (nextState.display == "Error") {
                    nextState = nextState.copy(
                        display = "0", currentNumber = "0", expression = ""
                    )
                }
                val currentValue = nextState.currentNumber.toDoubleOrNull() ?: 0.0

                if (nextState.previousNumber != null && nextState.currentOperator != null && !nextState.restartDisplay) {
                    try {
                        val result = executeOperation(
                            nextState.previousNumber,
                            currentValue,
                            nextState.currentOperator
                        )
                        nextState = nextState.copy(
                            display = result,
                            currentNumber = result,
                            previousNumber = result.toDoubleOrNull(),
                            expression = "$result ${visibleSymbol(action.value)}"
                        )
                    } catch (_: IllegalArgumentException) {
                        uiState = nextState.copy(
                            display = "Error",
                            currentNumber = "0",
                            previousNumber = null,
                            currentOperator = null,
                            expression = "",
                            restartDisplay = true
                        )
                        return
                    }
                } else {
                    nextState = nextState.copy(
                        previousNumber = currentValue,
                        expression = "${calculadora.formatearResultado(currentValue)} ${visibleSymbol(action.value)}"
                    )
                }

                uiState = nextState.copy(
                    currentOperator = action.value,
                    restartDisplay = true
                )
            }

            CalculatorAction.Equals -> {
                val previous  = uiState.previousNumber ?: return
                val operator  = uiState.currentOperator ?: return
                val current   = uiState.currentNumber.toDoubleOrNull() ?: return

                try {
                    val result = executeOperation(previous, current, operator)
                    val operationText =
                        "${calculadora.formatearResultado(previous)} ${visibleSymbol(operator)} ${calculadora.formatearResultado(current)} = $result"

                    uiState = uiState.copy(
                        display = result,
                        currentNumber = result,
                        previousNumber = null,
                        currentOperator = null,
                        expression = "",
                        restartDisplay = true
                    )
                    pushHistory(operationText)
                } catch (_: IllegalArgumentException) {
                    uiState = uiState.copy(
                        display = "Error",
                        currentNumber = "0",
                        previousNumber = null,
                        currentOperator = null,
                        expression = "",
                        restartDisplay = true
                    )
                }
            }

            CalculatorAction.Clear -> {
                uiState = CalculatorUiState(history = uiState.history)
            }

            CalculatorAction.Percent -> {
                val current = uiState.currentNumber.toDoubleOrNull() ?: return
                val result = calculadora.porcentaje(current)
                val text = calculadora.formatearResultado(result)
                uiState = uiState.copy(currentNumber = text, display = text)
            }

            CalculatorAction.ToggleSign -> {
                val current = uiState.currentNumber.toDoubleOrNull() ?: return
                val result = calculadora.cambiarSigno(current)
                val text = calculadora.formatearResultado(result)
                uiState = uiState.copy(currentNumber = text, display = text)
            }

            // ─── Funcionalidad adicional: Raíz Cuadrada ───────────────────
            CalculatorAction.SquareRoot -> {
                val current = uiState.currentNumber.toDoubleOrNull() ?: return
                try {
                    val result = calculadora.raizCuadrada(current)
                    val text = calculadora.formatearResultado(result)
                    val operationText = "√${calculadora.formatearResultado(current)} = $text"
                    uiState = uiState.copy(
                        display = text,
                        currentNumber = text,
                        expression = "",
                        restartDisplay = true
                    )
                    pushHistory(operationText)
                } catch (_: IllegalArgumentException) {
                    uiState = uiState.copy(
                        display = "Error",
                        currentNumber = "0",
                        previousNumber = null,
                        currentOperator = null,
                        expression = "",
                        restartDisplay = true
                    )
                }
            }

            CalculatorAction.ClearHistory -> {
                uiState = uiState.copy(history = emptyList())
            }

            CalculatorAction.Backspace -> {
                if (uiState.display == "Error" || uiState.restartDisplay) return
                val next = if (uiState.currentNumber.length <= 1) "0"
                           else uiState.currentNumber.dropLast(1)
                uiState = uiState.copy(currentNumber = next, display = next)
            }
        }
    }

    Scaffold(containerColor = CalcBackground) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(CalcBackground)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Bottom)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Display
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = CalcSurface,
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 4.dp
                ) {
                    CalculatorDisplay(
                        value = uiState.display,
                        expression = uiState.expression,
                        history = uiState.history,
                        onClearHistory = { handleAction(CalculatorAction.ClearHistory) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Teclado
                CalculatorKeypad(onAction = ::handleAction)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    CalculadoraTheme {
        CalculatorScreen()
    }
}
