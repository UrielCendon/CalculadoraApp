package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class DivisionCalculadoraTest {
    private val calculadora = Calculadora()

    @Test
    fun division_valida_devuelve_resultado_correcto() {
        assertEquals(2.5, calculadora.division(10.0, 4.0), 0.0)
    }

    @Test
    fun division_entre_cero_lanza_error() {
        assertThrows(IllegalArgumentException::class.java) {
            calculadora.division(10.0, 0.0)
        }
    }

    @Test
    fun division_con_divisor_negativo_devuelve_resultado_correcto() {
        assertEquals(-4.0, calculadora.division(8.0, -2.0), 0.0)
    }
}
