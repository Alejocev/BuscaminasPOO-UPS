# Buscaminas POO (Java)

Juego de Buscaminas en consola desarrollado en Java aplicando Programación Orientada a Objetos, patrón MVC, manejo de excepciones y persistencia mediante serialización (`partida_guardada.dat`).

## Estructura del proyecto
- `modelo`: `Casilla`, `Tablero`.
- `excepciones`: `CasillaYaDescubiertaException`.
- `persistencia`: `GestorPartida`.
- `vista`: `ConsolaVista` (filas `A–J`, columnas `1–10`, símbolos `X` mina, `V` vacío descubierto).
- `controlador`: `JuegoController` (bucle principal y `main`).
- `pruebas`: `TableroTest` (pruebas unitarias con JUnit 5).

## Cómo ejecutar
1. Abrir el proyecto en Eclipse.
2. Ejecutar `controlador.JuegoController` como *Java Application*.
3. Usar `[D]` Descubrir, `[M]` Marcar/Desmarcar, `[G]` Guardar, `[C]` Cargar o `[S]` Salir, ingresando coordenadas como `A5`.

## Ejemplo de tablero en consola
```text
   1  2  3  4  5  6  7  8  9 10
A  V  V  1  #  #  #  #  #  #  #
B  V  1  2  #  #  #  #  #  #  #