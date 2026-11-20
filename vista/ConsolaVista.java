package vista;

import modelo.Casilla;
import modelo.Tablero;

public class ConsolaVista {

    public void mostrarTablero(Tablero tablero) {
        int filas = tablero.getFilas();
        int columnas = tablero.getColumnas();

        System.out.print("   ");
        for (int c = 1; c <= columnas; c++) {
            System.out.printf("%3d", c);
        }
        System.out.println();

        for (int f = 0; f < filas; f++) {
            char letraFila = (char) ('A' + f);
            System.out.printf("%2c ", letraFila);

            for (int c = 0; c < columnas; c++) {
                Casilla casilla = tablero.getCasilla(f, c);
                System.out.printf("%3s", obtenerSimbolo(casilla));
            }
            System.out.println();
        }
    }

    private String obtenerSimbolo(Casilla casilla) {
        if (casilla.isMarcada()) {
            return "M";
        }
        if (!casilla.isDescubierta()) {
            return "#";
        }
        if (casilla.isTieneMina()) {
            return "X";
        }
        if (casilla.getMinasAdyacentes() == 0) {
            return "V";
        }
        return String.valueOf(casilla.getMinasAdyacentes());
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}