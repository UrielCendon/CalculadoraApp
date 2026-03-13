package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

class SumaCalculadoraTest {
    private val calculadora = Calculadora()

    @Test
    fun suma_con_enteros_devuelve_resultado_correcto() {
        assertEquals(9.0, calculadora.suma(4.0, 5.0), 0.0)
    }

    @Test
    fun suma_con_decimal_devuelve_resultado_correcto() {
        assertEquals(10.5, calculadora.suma(8.0, 2.5), 0.0)
    }

    @Test
    fun suma_con_numero_negativo_devuelve_resultado_correcto() {
        assertEquals(3.0, calculadora.suma(8.0, -5.0), 0.0)
    }
}
