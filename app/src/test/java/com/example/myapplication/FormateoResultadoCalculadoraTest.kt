package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

class FormateoResultadoCalculadoraTest {
    private val calculadora = Calculadora()

    @Test
    fun formateo_de_entero_quita_decimal() {
        assertEquals("10", calculadora.formatearResultado(10.0))
    }

    @Test
    fun formateo_de_decimal_conserva_decimal() {
        assertEquals("10.5", calculadora.formatearResultado(10.5))
    }

    @Test
    fun formateo_de_negativo_conserva_signo() {
        assertEquals("-3.25", calculadora.formatearResultado(-3.25))
    }
}
