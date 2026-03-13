# Tutorial: calculadora estilo iPhone en Jetpack Compose

Este tutorial explica como construir una calculadora sencilla con una interfaz inspirada en la imagen de referencia que compartiste. La meta no es clonar iOS al pixel, sino reproducir sus ideas visuales principales en Android con Jetpack Compose.

## Objetivo de la interfaz

Queremos que la pantalla tenga estas caracteristicas:

- fondo negro,
- display grande alineado a la derecha,
- historial pequeño arriba del resultado,
- botones circulares para numeros y operadores,
- botones grises para numeros,
- botones gris claro para controles,
- botones naranjas para operaciones,
- boton `0` mas ancho que los demas.

## Archivos importantes

- `app/src/main/java/com/example/myapplication/MainActivity.kt`
- `app/src/main/java/com/example/myapplication/Calculadora.kt`

## Paso 1. Separar logica y UI

La clase `Calculadora` solo contiene operaciones matematicas:

- `suma`
- `resta`
- `multiplicacion`
- `division`
- `porcentaje`
- `cambiarSigno`

Esto permite explicar en clase que:

- la interfaz captura eventos,
- la clase de negocio resuelve operaciones,
- y Compose solo redibuja la pantalla cuando cambia el estado.

## Paso 2. Crear el estado de la pantalla

En `MainActivity.kt` la pantalla guarda:

- `display`
- `expresion`
- `historial`
- `numeroActual`
- `numeroAnterior`
- `operadorActual`
- `reiniciarDisplay`

Se usa `rememberSaveable` para que el estado sobreviva a cambios de configuracion, por ejemplo una rotacion de pantalla.

## Paso 3. Usar un contenedor oscuro

La pantalla usa `Scaffold` con fondo negro.

Despues se coloca un `Surface` negro con esquinas redondeadas para que el contenido se vea como una pieza compacta, similar a una calculadora real.

## Paso 4. Crear el display y el historial

La funcion `CalculatorDisplay(...)` usa una `Column` alineada a la derecha.

Se muestran tres niveles:

- historial de operaciones anteriores,
- operacion actual,
- numero principal grande.

La decision de usar esta jerarquia viene de la referencia visual: primero contexto, luego operacion en curso y al final el resultado protagonista.

## Paso 5. Crear tipos de boton

Se definio:

- `ButtonKind.Number`
- `ButtonKind.Control`
- `ButtonKind.Operator`

Eso permite asignar color y estilo segun el rol del boton.

## Paso 6. Construir filas de botones

Se usa `CalculatorButtonRow(...)` para filas completas de 4 botones.

Cada boton ocupa el mismo ancho usando `weight(1f)` y una proporcion cuadrada con `aspectRatio(1f)`. Como ademas usan `CircleShape`, visualmente parecen circulares.

## Paso 7. Hacer el boton 0 mas ancho

La ultima fila se construye manualmente para que:

- `0` ocupe dos espacios,
- `.` ocupe uno,
- `=` ocupe uno.

Eso produce la capsula horizontal tipica de este tipo de calculadora.

## Paso 8. Conectar botones con acciones

Cada boton ejecuta una accion diferente:

- numeros -> `actualizarNumero`
- `.` -> `agregarPunto`
- `+`, `−`, `×`, `÷` -> `seleccionarOperador`
- `=` -> `calcularResultado`
- `%` -> `aplicarPorcentaje`
- `+/-` -> `cambiarSigno`
- `AC` -> `limpiarTodo`

## Paso 9. Historial de resultados

Cada vez que una operacion termina con `=`, la pantalla agrega una linea al historial, por ejemplo:

- `12 × 4 = 48`

El historial se limita a pocas entradas para no saturar la interfaz.

## Paso 10. Resultado final

Con esta estructura se consigue:

- una interfaz visualmente cercana a la referencia,
- una clase `Calculadora` facil de explicar,
- una separacion clara entre UI y logica,
- y un proyecto adecuado para clase.

## Paso 11. Probar la interfaz

Como la pantalla usa Jetpack Compose, conviene agregar pruebas instrumentadas para validar los flujos visibles.

En este proyecto existe:

- `app/src/androidTest/java/com/example/myapplication/CalculadoraInstrumentedTest.kt`

### Que valida

- una suma exitosa,
- division entre cero,
- historial visible despues de resolver una operacion.

### Como lo hace

Las pruebas presionan botones reales de la interfaz y luego revisan el texto del display o del historial.

Ejemplo conceptual:

```kotlin
composeTestRule.onNodeWithText("2").performClick()
composeTestRule.onNodeWithText("+").performClick()
composeTestRule.onNodeWithText("3").performClick()
composeTestRule.onNodeWithText("=").performClick()
composeTestRule.onNodeWithTag("calculator_display").assertTextEquals("5")
```

### Por que esto es importante

- valida que la UI y la logica trabajen juntas,
- detecta errores de flujo,
- y sirve para enseñar pruebas funcionales sobre Compose.
