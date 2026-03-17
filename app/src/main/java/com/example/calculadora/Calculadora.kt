package com.example.calculadora

import kotlin.math.roundToLong
import kotlin.math.sqrt

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

    //funcionalidad adicional: raíz cuadrada
    fun raizCuadrada(a: Double): Double {
        require(a >= 0.0) { "No se puede calcular la raíz cuadrada de un número negativo" }
        return sqrt(a)
    }

    fun formatearResultado(valor: Double): String {
        return if (valor == valor.roundToLong().toDouble()) {
            valor.roundToLong().toString()
        } else {
            // Limitar a 10 decimales para evitar errores de punto flotante
            val str = "%.10f".format(valor).trimEnd('0').trimEnd('.')
            str
        }
    }
}
