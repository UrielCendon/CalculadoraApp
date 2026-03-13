# Tutorial: fuentes, decisiones y referencias del diseno

Este documento explica de donde salio el diseno, por que se tomaron ciertas decisiones y cuando conviene citar fuentes.

## 1. De donde sale el diseno

La base principal del estilo visual viene de dos lugares:

1. La imagen de referencia proporcionada en esta conversacion.
2. Documentacion oficial de Android y Apple para justificar decisiones de implementacion, estado y legibilidad.

## 2. Que se tomo directamente de la imagen

De la imagen se tomaron estas caracteristicas visuales:

- fondo completamente negro,
- display grande alineado a la derecha,
- uso de texto secundario para la operacion,
- historial discreto sobre el resultado,
- teclado en filas de botones redondos,
- botones naranjas para operadores,
- botones grises para numeros,
- boton `0` alargado.

Estas decisiones son una interpretacion del layout mostrado por ti. No se copiaron assets, iconos propietarios ni elementos del sistema iOS.

## 3. Que se tomo de la documentacion oficial de Android

### Compose y manejo de estado

Se uso `rememberSaveable` porque la documentacion oficial de Android recomienda guardar estado de UI cuando se quiere sobrevivir a recreaciones de actividad, como rotacion de pantalla.

Referencias:

- [State and Jetpack Compose](https://developer.android.com/develop/ui/compose/state)
- [Save UI state in Compose](https://developer.android.com/develop/ui/compose/state-saving)

### Botones y componentes Material 3

Se uso `Button`, `Surface`, `Text` y `Scaffold` porque son componentes oficiales y estables del ecosistema Compose.

Referencias:

- [Button | Jetpack Compose](https://developer.android.com/develop/ui/compose/components/button)
- [Material Design 3 in Compose](https://developer.android.com/develop/ui/compose/designsystems/material3)

## 4. Que se tomo de la documentacion oficial de Apple

No se uso documentacion de Apple para copiar el componente exacto de la calculadora. Se uso como apoyo conceptual en dos puntos:

- prioridad de contraste y legibilidad en fondos oscuros,
- consistencia visual y jerarquia clara en la interfaz.

Referencias:

- [Human Interface Guidelines](https://developer.apple.com/design/human-interface-guidelines/)
- [Accessibility | Apple Developer Documentation](https://developer.apple.com/design/human-interface-guidelines/accessibility)

## 5. Por que se tomaron estas decisiones

### Fondo negro

Se eligio negro puro porque la imagen de referencia lo usa y porque en una calculadora hace que el display y los botones destaquen mejor.

### Numeros en gris oscuro

Se eligio gris oscuro para separar visualmente las teclas de entrada de los controles especiales.

### Operadores en naranja

Se eligio naranja porque es el color acento mas reconocible en la referencia. Ayuda a identificar rapido las acciones matematicas.

### Controles en gris claro

Botones como `AC`, `+/-` y `%` tienen otro tono porque no son numeros ni operadores binarios. Cambiar su tono mejora la lectura del teclado.

### Historial superior

Se agrego porque ayuda a entender el contexto de calculo y hace la interfaz mas cercana a la referencia compartida.

### Display grande y alineado a la derecha

Se eligio porque la calculadora necesita dar protagonismo al resultado y esa disposicion coincide con el patron visual de la referencia.

### Boton `0` ancho

Se hizo mas ancho porque es parte de la identidad visual de este tipo de calculadora y mejora el equilibrio visual de la ultima fila.

### `rememberSaveable`

Se uso en lugar de `remember` para que la calculadora conserve la entrada si la pantalla rota. Para una demo de clase, eso permite mostrar una buena practica real.

### Clase `Calculadora` separada

Se mantuvo la aritmetica dentro de una clase independiente para reforzar principios de orientacion a objetos y separacion de responsabilidades.

### Pruebas separadas por nivel

Se decidio usar:

- pruebas unitarias para la logica matematica,
- pruebas instrumentadas para la interfaz Compose.

Esta decision mejora la claridad del proyecto porque cada tipo de prueba valida una capa distinta.

## 6. Decisiones sobre pruebas

### Por que no dejar `ExampleUnitTest`

Ese archivo solo era una plantilla generada por Android Studio. No representaba las necesidades reales del proyecto.

Por eso se reemplazo por archivos de prueba separados por funcionalidad:

- suma,
- resta,
- multiplicacion,
- division,
- porcentaje,
- cambio de signo,
- formateo.

### Por que agregar pruebas instrumentadas

La calculadora no solo necesita que las operaciones den bien, tambien necesita que:

- los botones funcionen,
- el display se actualice,
- el historial aparezca,
- y los errores se muestren correctamente.

Eso no se cubre bien solo con unit tests.

### Referencias tecnicas relacionadas

- [Test your app on Android](https://developer.android.com/training/testing)
- [Test layout inspector and Compose testing overview](https://developer.android.com/develop/ui/compose/testing)
- [Compose testing APIs](https://developer.android.com/develop/ui/compose/testing/apis)

## 7. Cuando si conviene citar fuentes

Si vas a explicar la clase de forma academica o formal, si conviene citar cuando:

- justificas por que usaste `rememberSaveable`,
- explicas por que elegiste componentes oficiales de Compose,
- justificas por que separaste unit tests e instrumented tests,
- hablas de contraste o accesibilidad,
- comparas tu implementacion con patrones de diseno conocidos.

## 8. Nota importante sobre referencias

La referencia mas fuerte de estilo en este proyecto no es una fuente web, sino la imagen compartida en esta conversacion. Las fuentes oficiales se usaron para respaldar decisiones tecnicas y de usabilidad, no para copiar literalmente la app original.
