# Calculadora sencilla con Jetpack Compose

Este proyecto muestra una calculadora basica hecha en Android con Kotlin y Jetpack Compose. La idea es que los alumnos entiendan dos partes:

1. La logica de negocio en una clase Kotlin normal.
2. La interfaz visual hecha con componentes Compose.

## 1. Estructura general

Los archivos mas importantes son:

- `app/src/main/java/com/example/myapplication/Calculadora.kt`
- `app/src/main/java/com/example/myapplication/MainActivity.kt`
- `app/src/main/AndroidManifest.xml`

## 2. Que hace cada archivo

### `AndroidManifest.xml`

Este archivo le dice a Android:

- cual es la clase `Application` de la app,
- cual es la actividad principal,
- y cual pantalla debe abrir cuando el usuario toca el icono.

En este proyecto Android inicia en `MainActivity`.

### `MyApplication.kt`

Esta clase hereda de `Application`. Se crea antes que la actividad principal.

Se usa para inicializaciones globales de la app. En este proyecto solo escribe un mensaje en Logcat para mostrar que la aplicacion ya fue creada.

### `Calculadora.kt`

Esta clase contiene la logica de la calculadora. No dibuja nada en pantalla.

Su responsabilidad es:

- realizar las operaciones matematicas,
- devolver el resultado de una suma,
- devolver el resultado de una resta,
- devolver el resultado de una multiplicacion,
- devolver el resultado de una division,
- y formatear el resultado para mostrarlo en pantalla.

Esto es importante en clase porque separa la logica de negocio de la interfaz.

### `MainActivity.kt`

Esta clase abre la interfaz usando `setContent { ... }`.

Aqui vive la pantalla Compose que:

- crea una instancia de `Calculadora`,
- guarda el numero actual, el numero anterior y el operador seleccionado,
- muestra el valor actual en el display,
- dibuja los botones,
- y cuando un boton se presiona llama a un metodo de `Calculadora`.

## 3. Objetos Compose usados en la interfaz

### `Scaffold`

`Scaffold` es una estructura base para pantallas Compose.

Sirve para organizar la pantalla principal y manejar espacios seguros del sistema, por ejemplo las barras superior e inferior del celular.

En esta app se usa como contenedor principal de toda la calculadora.

### `Column`

`Column` acomoda elementos uno debajo de otro.

En la calculadora se usa para poner verticalmente:

- el titulo,
- el display,
- y las filas de botones.

### `Row`

`Row` acomoda elementos de izquierda a derecha.

Cada fila de la calculadora es un `Row` con cuatro espacios. Dentro de cada fila se colocan botones como `7`, `8`, `9`, `+`.

### `Spacer`

`Spacer` ocupa espacio vacio.

En la ultima fila se usa para dejar huecos donde no queremos poner botones. Eso ayuda a que la distribucion visual siga siendo uniforme.

### `Surface`

`Surface` es una superficie visual. Se usa mucho para crear contenedores con color, elevacion y forma.

En esta app el display esta dentro de un `Surface`, por eso parece una pantalla separada del resto de botones.

### `Text`

`Text` muestra texto en pantalla.

Se usa para:

- el titulo de la app,
- la operacion actual,
- el historial,
- el numero del display,
- y las etiquetas dentro de los botones.

### `Button`

`Button` crea botones presionables.

Cada tecla de la calculadora es un `Button`. Cuando el usuario lo toca, se ejecuta la funcion `onClick`.

### `MaterialTheme`

`MaterialTheme` da acceso a colores, tipografias y formas del tema visual de la app.

## 4. Objetos de estado usados

### `remember`

`remember` guarda un objeto mientras la pantalla siga viva en Compose.

Aqui se usa para conservar la instancia de `Calculadora` aunque la interfaz se vuelva a dibujar.

### `rememberSaveable`

`rememberSaveable` guarda estado de UI que puede restaurarse si la actividad se recrea.

Aqui se usa para:

- `display`
- `historial`
- `numeroActual`
- `numeroAnterior`
- `operadorActual`
- `reiniciarDisplay`

## 5. Como funciona la clase `Calculadora`

La clase expone funciones matematicas claras:

- `suma(a, b)`
- `resta(a, b)`
- `multiplicacion(a, b)`
- `division(a, b)`
- `porcentaje(a)`
- `cambiarSigno(a)`
- `formatearResultado(valor)`

## 6. Como funciona la interfaz de la calculadora

La interfaz mantiene el estado de uso:

- `numeroActual`: lo que el usuario esta escribiendo,
- `numeroAnterior`: el primer valor de una operacion,
- `operadorActual`: el simbolo seleccionado,
- `display`: el texto que se muestra,
- `historial`: operaciones resueltas que se apilan arriba,
- `reiniciarDisplay`: indica si el siguiente numero debe reemplazar el actual.

## 7. Flujo cuando el usuario toca un boton

1. El usuario toca un `Button`.
2. Compose ejecuta `onClick`.
3. La pantalla decide si ese boton es numero, operador, punto, borrar o igual.
4. Si hace falta calcular, llama a un metodo de `Calculadora`.
5. Se actualiza la variable `display`.
6. Se actualiza el historial si hubo un resultado final.
7. Compose vuelve a dibujar la pantalla.

## 8. Ideas para explicar en clase

- La interfaz no hace cuentas por si sola; delega operaciones a `Calculadora`.
- La clase `Calculadora` si hace las operaciones.
- Compose redibuja la interfaz cuando cambia el estado.
- Separar logica e interfaz hace el codigo mas facil de entender y mantener.

## 9. Pruebas del proyecto

Este proyecto ya tiene dos tipos de pruebas:

- pruebas unitarias en `app/src/test/java/com/example/myapplication`
- pruebas instrumentadas en `app/src/androidTest/java/com/example/myapplication`

### Pruebas unitarias

Las pruebas unitarias validan la logica de la clase `Calculadora` sin necesidad de abrir la app ni usar un emulador.

Archivos incluidos:

- `SumaCalculadoraTest.kt`
- `RestaCalculadoraTest.kt`
- `MultiplicacionCalculadoraTest.kt`
- `DivisionCalculadoraTest.kt`
- `PorcentajeCalculadoraTest.kt`
- `CambioSignoCalculadoraTest.kt`
- `FormateoResultadoCalculadoraTest.kt`

Regla usada en clase:

- al menos 1 caso exitoso,
- al menos 2 casos alternos o de borde,
- y cuando aplica, un error real como division entre cero.

### Pruebas instrumentadas

Las pruebas instrumentadas validan el comportamiento de la interfaz real de la app con Compose.

Archivo incluido:

- `CalculadoraInstrumentedTest.kt`

Este test comprueba por ejemplo:

- que `2 + 3 = 5` se vea en pantalla,
- que dividir entre cero muestre `Error`,
- y que el historial se agregue arriba del resultado.

### Como ejecutarlas

Pruebas unitarias:

```powershell
.\gradlew.bat testDebugUnitTest
```

Compilar pruebas instrumentadas:

```powershell
.\gradlew.bat assembleAndroidTest
```

Ejecutar pruebas instrumentadas en emulador o dispositivo:

```powershell
.\gradlew.bat connectedDebugAndroidTest
```
