# Tutorial completo: como construir toda la aplicacion de calculadora

Este documento sirve como guia principal para construir la aplicacion completa. Incluye arquitectura, funcionalidad, interfaz, decisiones de diseno, pruebas y recomendaciones de explicacion para clase.

## 1. Objetivo

Construir una aplicacion Android con Jetpack Compose que:

- muestre una calculadora funcional,
- tenga operaciones basicas y algunas funciones extra,
- use una interfaz inspirada en la calculadora de iPhone,
- muestre historial de operaciones arriba del resultado,
- y tenga pruebas unitarias organizadas por funcionalidad.

## 2. Resultado esperado

La app final debe tener:

- fondo negro,
- resultado grande alineado a la derecha,
- historial arriba del resultado,
- teclas redondas con colores por categoria,
- boton `0` ancho,
- operaciones `+`, `−`, `×`, `÷`,
- `AC`, `%`, `+/-`, `.`, `=`,
- pruebas unitarias por operacion.

## 3. Estructura del proyecto

Archivos clave:

- `app/src/main/java/com/example/myapplication/MainActivity.kt`
- `app/src/main/java/com/example/myapplication/Calculadora.kt`
- `app/src/test/java/com/example/myapplication/...`

Separacion recomendada:

- `Calculadora.kt`: logica matematica.
- `MainActivity.kt`: estado de UI y composables.
- `app/src/test/...`: pruebas por funcionalidad.

## 4. Paso 1: crear la clase de negocio

La clase `Calculadora` debe contener metodos pequeños y claros:

```kotlin
class Calculadora {
    fun suma(a: Double, b: Double): Double = a + b
    fun resta(a: Double, b: Double): Double = a - b
    fun multiplicacion(a: Double, b: Double): Double = a * b

    fun division(a: Double, b: Double): Double {
        require(b != 0.0) { "No se puede dividir entre cero" }
        return a / b
    }

    fun porcentaje(a: Double): Double = a / 100.0
    fun cambiarSigno(a: Double): Double = -a
}
```

### Por que hacerlo asi

- Es facil de probar.
- Es facil de explicar.
- Separa matematicas de interfaz.
- Permite que los alumnos entiendan responsabilidades.

## 5. Paso 2: definir el estado de la interfaz

La UI necesita guardar informacion temporal del flujo de la calculadora:

- `display`: lo que se ve en pantalla.
- `expresion`: operacion en curso.
- `historial`: resultados anteriores.
- `numeroActual`: numero que el usuario esta escribiendo.
- `numeroAnterior`: primer operando.
- `operadorActual`: operador seleccionado.
- `reiniciarDisplay`: indica si el siguiente numero reemplaza el actual.

Ejemplo:

```kotlin
var display by rememberSaveable { mutableStateOf("0") }
var historial by rememberSaveable { mutableStateOf(listOf<String>()) }
var numeroActual by rememberSaveable { mutableStateOf("0") }
```

### Por que usar `rememberSaveable`

Porque si el dispositivo rota, Compose puede restaurar estos valores. Eso es mejor que perder la operacion en curso.

## 6. Paso 3: crear la actividad principal

`MainActivity` debe seguir el flujo Compose normal:

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                CalculatorApp()
            }
        }
    }
}
```

### Que explicar en clase

- `ComponentActivity` es la base.
- `onCreate` es el punto de arranque de la actividad.
- `setContent` conecta Android con Compose.

## 7. Paso 4: crear la funcion principal de pantalla

`CalculatorApp()` concentra:

- estado de la UI,
- funciones auxiliares,
- layout general.

Las funciones auxiliares recomendadas son:

- `actualizarNumero`
- `agregarPunto`
- `seleccionarOperador`
- `calcularResultado`
- `limpiarTodo`
- `aplicarPorcentaje`
- `cambiarSigno`

## 8. Paso 5: construir el display

El display no es solo el resultado. Debe incluir:

- historial de operaciones,
- operacion actual,
- resultado principal.

Ejemplo conceptual:

```kotlin
CalculatorDisplay(
    value = display,
    expression = expresion,
    history = historial
)
```

### Decisiones visuales

- Alineado a la derecha para que el resultado domine.
- Texto grande para el resultado.
- Texto pequeño y tenue para historial y operacion.
- Fondo oscuro para alto contraste.

## 9. Paso 6: construir los botones

Se recomienda clasificar los botones por tipo:

- `Number`
- `Control`
- `Operator`

Eso permite cambiar color y comportamiento de forma limpia.

### Ejemplo

```kotlin
enum class ButtonKind {
    Number,
    Control,
    Operator
}
```

Luego cada boton puede usar color segun su categoria.

## 10. Paso 7: distribuir el teclado

Las primeras filas pueden construirse con una funcion reutilizable:

```kotlin
CalculatorButtonRow(
    buttons = listOf(
        CalculatorButtonUi("7", ButtonKind.Number),
        CalculatorButtonUi("8", ButtonKind.Number),
        CalculatorButtonUi("9", ButtonKind.Number),
        CalculatorButtonUi("×", ButtonKind.Operator)
    ),
    onClick = { ... }
)
```

La ultima fila conviene hacerla manual porque el boton `0` debe ser mas ancho.

## 11. Paso 8: conectar UI con la clase `Calculadora`

Cuando el usuario toca `=`, la UI debe decidir que operacion invocar:

```kotlin
val resultado = when (operador) {
    "+" -> calculadora.suma(a, b)
    "-" -> calculadora.resta(a, b)
    "x" -> calculadora.multiplicacion(a, b)
    "/" -> calculadora.division(a, b)
    else -> b
}
```

### Por que esta decision es buena

- La UI controla flujo.
- `Calculadora` controla matematicas.
- Cada capa tiene una responsabilidad clara.

## 12. Paso 9: agregar historial

Cada vez que el usuario presiona `=`, guarda una linea:

```kotlin
"12 × 4 = 48"
```

Y la agregas al historial:

```kotlin
historial = (historial + operacionTexto).takeLast(5)
```

### Por que limitarlo

- Evita saturar la pantalla.
- Mantiene el diseño limpio.
- Conserva solo el contexto mas reciente.

## 13. Paso 10: manejar errores

Ejemplo principal: division entre cero.

`Calculadora.division(...)` lanza una excepcion y la UI la captura:

```kotlin
try {
    val resultado = calculadora.division(10.0, 0.0)
} catch (_: IllegalArgumentException) {
    display = "Error"
}
```

### Que enseñar aqui

- Validacion de reglas del negocio.
- Uso de `require`.
- Manejo de excepciones en la capa de interfaz.

## 14. Paso 11: diseno visual

### Colores

- negro para fondo,
- gris oscuro para numeros,
- gris claro para controles,
- naranja para operadores.

### Formas

- circulos para casi todos los botones,
- capsula para el `0`.

### Jerarquia visual

- historial pequeno y tenue,
- expresion actual intermedia,
- resultado principal muy grande.

### Espaciado

No conviene pegar demasiado los botones. Debe haber suficiente separacion horizontal y vertical para que cada tecla respire.

## 15. Paso 12: pruebas unitarias

No dejes un solo archivo `ExampleUnitTest`. Es mejor separar por funcionalidad:

- `SumaCalculadoraTest.kt`
- `RestaCalculadoraTest.kt`
- `MultiplicacionCalculadoraTest.kt`
- `DivisionCalculadoraTest.kt`
- `PorcentajeCalculadoraTest.kt`
- `CambioSignoCalculadoraTest.kt`
- `FormateoResultadoCalculadoraTest.kt`

### Regla de cobertura para clase

Para cada funcionalidad:

- 1 caso exitoso,
- al menos 2 casos alternos o de borde,
- y cuando aplique, un caso de error real.

### Ejemplo

```kotlin
@Test
fun division_entre_cero_lanza_error() {
    assertThrows(IllegalArgumentException::class.java) {
        calculadora.division(10.0, 0.0)
    }
}
```

## 16. Paso 13: pruebas instrumentadas de la interfaz

Ademas de probar la logica, conviene probar la app real con Compose UI tests.

En este proyecto se usa:

- `CalculadoraInstrumentedTest.kt`

Estas pruebas corren en `app/src/androidTest/...` y necesitan emulador o dispositivo.

### Que casos conviene cubrir

- una operacion exitosa visible en el display,
- un error visible en el display,
- y el historial visible despues del resultado.

### Ejemplo de flujo probado

```kotlin
composeTestRule.onNodeWithText("4").performClick()
composeTestRule.onNodeWithText("×").performClick()
composeTestRule.onNodeWithText("5").performClick()
composeTestRule.onNodeWithText("=").performClick()
composeTestRule.onNodeWithTag("calculator_history_0")
    .assertTextEquals("4 × 5 = 20")
```

### Comandos utiles

Pruebas unitarias:

```powershell
.\gradlew.bat testDebugUnitTest
```

Compilar pruebas instrumentadas:

```powershell
.\gradlew.bat assembleAndroidTest
```

Ejecutar pruebas instrumentadas:

```powershell
.\gradlew.bat connectedDebugAndroidTest
```

## 17. Errores comunes que conviene explicar

### Error 1: mezclar logica y UI

Si toda la logica vive dentro del boton, la app funciona, pero el codigo queda dificil de probar y mantener.

### Error 2: no guardar estado

Si no usas estado Compose, el display no se actualiza correctamente.

### Error 3: no controlar division entre cero

La app puede fallar o producir resultados invalidos.

### Error 4: no pensar el layout

Una calculadora necesita equilibrio visual. Si los botones quedan muy pegados o el texto se corta, la experiencia se siente descuidada.

### Error 5: no probar la interfaz

Aunque la logica este bien, la app puede fallar por un boton mal conectado, un display que no se actualiza o un historial que no aparece.

## 18. Fuentes y referencias

La referencia visual principal fue la imagen compartida en esta conversacion.

Fuentes tecnicas oficiales recomendadas:

- [State and Jetpack Compose](https://developer.android.com/develop/ui/compose/state)
- [Save UI state in Compose](https://developer.android.com/develop/ui/compose/state-saving)
- [Button | Jetpack Compose](https://developer.android.com/develop/ui/compose/components/button)
- [Material Design 3 in Compose](https://developer.android.com/develop/ui/compose/designsystems/material3)
- [Compose testing overview](https://developer.android.com/develop/ui/compose/testing)
- [Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/)

## 19. Como presentarlo en clase

Orden recomendado:

1. Mostrar la app funcionando.
2. Enseñar `Calculadora.kt`.
3. Enseñar el estado de `CalculatorApp()`.
4. Enseñar el display y los botones.
5. Mostrar el historial.
6. Ejecutar las pruebas unitarias.
7. Mostrar una prueba de UI instrumentada.

## 20. Ejercicio final para alumnos

Pide que implementen una mejora:

- `DEL`,
- `CE`,
- memoria `M+`,
- animacion de historial,
- cambio de tema,
- o pruebas de UI con Compose.
