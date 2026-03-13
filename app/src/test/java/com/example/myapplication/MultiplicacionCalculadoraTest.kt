package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

class MultiplicacionCalculadoraTest {
    private val calculadora = Calculadora()

    @Test
    fun multiplicacion_con_enteros_devuelve_resultado_correcto() {
        assertEquals(20.0, calculadora.multiplicacion(4.0, 5.0), 0.0)
    }

    @Test
    fun multiplicacion_por_cero_devuelve_cero() {
        assertEquals(0.0, calculadora.multiplicacion(8.0, 0.0), 0.0)
    }

    @Test
    fun multiplicacion_con_negativo_devuelve_resultado_correcto() {
        assertEquals(-12.0, calculadora.multiplicacion(3.0, -4.0), 0.0)
    }
}
