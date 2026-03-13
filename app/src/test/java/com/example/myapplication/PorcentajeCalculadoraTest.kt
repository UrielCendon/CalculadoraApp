package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

class PorcentajeCalculadoraTest {
    private val calculadora = Calculadora()

    @Test
    fun porcentaje_valido_devuelve_resultado_correcto() {
        assertEquals(0.25, calculadora.porcentaje(25.0), 0.0)
    }

    @Test
    fun porcentaje_de_cero_devuelve_cero() {
        assertEquals(0.0, calculadora.porcentaje(0.0), 0.0)
    }

    @Test
    fun porcentaje_de_negativo_devuelve_resultado_correcto() {
        assertEquals(-0.4, calculadora.porcentaje(-40.0), 0.0)
    }
}
