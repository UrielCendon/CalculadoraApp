package com.example.myapplication

import org.junit.Assert.assertEquals
import org.junit.Test

class CambioSignoCalculadoraTest {
    private val calculadora = Calculadora()

    @Test
    fun cambiar_signo_de_positivo_devuelve_negativo() {
        assertEquals(-8.0, calculadora.cambiarSigno(8.0), 0.0)
    }

    @Test
    fun cambiar_signo_de_negativo_devuelve_positivo() {
        assertEquals(8.0, calculadora.cambiarSigno(-8.0), 0.0)
    }

    @Test
    fun cambiar_signo_de_cero_permanece_en_cero() {
        assertEquals(0.0, calculadora.cambiarSigno(0.0), 0.0)
    }
}
