package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

class RestaCalculadoraTest {
    private val calculadora = Calculadora()

    @Test
    fun resta_con_enteros_devuelve_resultado_correcto() {
        assertEquals(6.0, calculadora.resta(10.0, 4.0), 0.0)
    }

    @Test
    fun resta_con_decimal_devuelve_resultado_correcto() {
        assertEquals(7.5, calculadora.resta(10.0, 2.5), 0.0)
    }

    @Test
    fun resta_que_da_negativa_devuelve_resultado_correcto() {
        assertEquals(-3.0, calculadora.resta(2.0, 5.0), 0.0)
    }
}
