package controlador;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import excepciones.CasillaYaDescubiertaException;
import modelo.Casilla;
import modelo.Tablero;
import persistencia.GestorPartida;
import vista.ConsolaVista;

public class JuegoController {

    private Tablero tablero;
    private final ConsolaVista vista;
    private final GestorPartida gestorPartida;
    private final Scanner scanner;
    private boolean juegoTerminado;

    public JuegoController() {
        this.tablero = new Tablero();
        this.vista = new ConsolaVista();
        this.gestorPartida = new GestorPartida();
        this.scanner = new Scanner(System.in);
        this.juegoTerminado = false;
    }

    public static void main(String[] args) {
        JuegoController controller = new JuegoController();
        controller.iniciarJuego();
    }

    public void iniciarJuego() {
        vista.mostrarMensaje("=== BUSCAMINAS POO ===");
        while (!juegoTerminado) {
            vista.mostrarTablero(tablero);
            mostrarMenu();
            procesarOpcion();
        }
        scanner.close();
    }

    private void mostrarMenu() {
        vista.mostrarMensaje("Acciones: [D] Descubrir | [M] Marcar/Desmarcar | [G] Guardar | [C] Cargar | [S] Salir");
        System.out.print("Elige una opcion: ");
    }

    private void procesarOpcion() {
        String opcion = scanner.next().trim().toUpperCase();
        try {
            switch (opcion) {
                case "D":
                    accionDescubrir();
                    break;
                case "M":
                    accionMarcar();
                    break;
                case "G":
                    gestorPartida.guardarPartida(tablero);
                    vista.mostrarMensaje("Partida guardada correctamente.");
                    break;
                case "C":
                    tablero = gestorPartida.cargarPartida();
                    vista.mostrarMensaje("Partida cargada correctamente.");
                    break;
                case "S":
                    juegoTerminado = true;
                    vista.mostrarMensaje("Saliendo del juego...");
                    break;
                default:
                    vista.mostrarMensaje("Opcion no valida. Usa D, M, G, C o S.");
            }
        } catch (CasillaYaDescubiertaException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            vista.mostrarMensaje("Aviso: " + e.getMessage());
        } catch (InputMismatchException e) {
            vista.mostrarMensaje("Error de entrada: tipo de dato invalido.");
            scanner.nextLine();
        } catch (IOException | ClassNotFoundException e) {
            vista.mostrarMensaje("Error de archivo: " + e.getMessage());
        }
    }

    private void accionDescubrir() throws CasillaYaDescubiertaException {
        int[] coords = leerCoordenada();
        int fila = coords[0];
        int col = coords[1];

        tablero.descubrirCasilla(fila, col);
        Casilla casilla = tablero.getCasilla(fila, col);

        if (casilla.isTieneMina()) {
            vista.mostrarTablero(tablero);
            vista.mostrarMensaje("BOOM! Encontraste una mina (X). Has perdido.");
            juegoTerminado = true;
        } else if (verificarVictoria()) {
            vista.mostrarTablero(tablero);
            vista.mostrarMensaje("Felicidades! Descubriste todas las casillas seguras. Ganaste!");
            juegoTerminado = true;
        }
    }

    private void accionMarcar() {
        int[] coords = leerCoordenada();
        Casilla casilla = tablero.getCasilla(coords[0], coords[1]);
        if (!casilla.isDescubierta()) {
            casilla.setMarcada(!casilla.isMarcada());
            vista.mostrarMensaje(casilla.isMarcada() ? "Casilla marcada." : "Marca removida.");
        } else {
            vista.mostrarMensaje("No puedes marcar una casilla ya descubierta.");
        }
    }

    private int[] leerCoordenada() {
        System.out.print("Ingresa coordenada (ej. A5): ");
        String entrada = scanner.next().trim().toUpperCase();
        if (entrada.length() < 2 || entrada.length() > 3) {
            throw new IllegalArgumentException("Formato invalido. Usa letra de fila y numero de columna (ej. A5).");
        }

        char letraFila = entrada.charAt(0);
        int fila = letraFila - 'A';
        int col = Integer.parseInt(entrada.substring(1)) - 1;

        if (!tablero.esCoordenadaValida(fila, col)) {
            throw new ArrayIndexOutOfBoundsException("Coordenada fuera de los limites del tablero.");
        }
        return new int[]{fila, col};
    }

    private boolean verificarVictoria() {
        for (int f = 0; f < tablero.getFilas(); f++) {
            for (int c = 0; c < tablero.getColumnas(); c++) {
                Casilla casilla = tablero.getCasilla(f, c);
                if (!casilla.isTieneMina() && !casilla.isDescubierta()) {
                    return false;
                }
            }
        }
        return true;
    }
}