package com.example.myapplication

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CalculadoraInstrumentedTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun suma_muestra_resultado_correcto_en_pantalla() {
        composeTestRule.onNodeWithText("2").performClick()
        composeTestRule.onNodeWithText("+").performClick()
        composeTestRule.onNodeWithText("3").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithTag("calculator_display")
            .assertTextEquals("5")
    }

    @Test
    fun division_entre_cero_muestra_error() {
        composeTestRule.onNodeWithText("8").performClick()
        composeTestRule.onNodeWithText("÷").performClick()
        composeTestRule.onNodeWithText("0").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithTag("calculator_display")
            .assertTextEquals("Error")
    }

    @Test
    fun operacion_resuelta_aparece_en_historial() {
        composeTestRule.onNodeWithText("4").performClick()
        composeTestRule.onNodeWithText("×").performClick()
        composeTestRule.onNodeWithText("5").performClick()
        composeTestRule.onNodeWithText("=").performClick()

        composeTestRule.onNodeWithTag("calculator_history_0")
            .assertTextEquals("4 × 5 = 20")
    }
}
