package com.example.myapplication

import kotlin.math.roundToLong

class Calculadora {
    fun suma(a: Double, b: Double): Double {
        return a + b
    }

    fun resta(a: Double, b: Double): Double {
        return a - b
    }

    fun multiplicacion(a: Double, b: Double): Double {
        return a * b
    }

    fun division(a: Double, b: Double): Double {
        require(b != 0.0) { "No se puede dividir entre cero" }
        return a / b
    }

    fun porcentaje(a: Double): Double {
        return a / 100.0
    }

    fun cambiarSigno(a: Double): Double {
        return -a
    }

    fun formatearResultado(valor: Double): String {
        return if (valor == valor.roundToLong().toDouble()) {
            valor.roundToLong().toString()
        } else {
            valor.toString()
        }
    }
}
