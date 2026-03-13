package com.example.myapplication.ui.calculator

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.Calculadora
import com.example.myapplication.ui.theme.MyApplicationTheme

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
                    nextState = nextState.copy(display = "0", currentNumber = "0", expression = "")
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
                            expression = "$result ${action.value}"
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
                        expression = "${calculadora.formatearResultado(currentValue)} ${action.value}"
                    )
                }

                uiState = nextState.copy(
                    currentOperator = action.value,
                    restartDisplay = true
                )
            }

            CalculatorAction.Equals -> {
                val previous = uiState.previousNumber ?: return
                val operator = uiState.currentOperator ?: return
                val current = uiState.currentNumber.toDoubleOrNull() ?: return

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
                uiState = CalculatorUiState()
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
        }
    }

    Scaffold(containerColor = IphoneBlack) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = IphoneBlack,
                shape = RoundedCornerShape(36.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Bottom)
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

                    CalculatorDisplay(
                        value = uiState.display,
                        expression = uiState.expression,
                        history = uiState.history,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CalculatorKeypad(onAction = ::handleAction)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CalculatorScreenPreview() {
    MyApplicationTheme {
        CalculatorScreen()
    }
}
