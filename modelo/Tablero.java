package modelo;

import java.io.Serializable;
import java.util.Random;
import excepciones.CasillaYaDescubiertaException;

public class Tablero implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int FILAS = 10;
    private static final int COLUMNAS = 10;
    private static final int TOTAL_MINAS = 10;
    private Casilla[][] matriz;

    public Tablero() {
        matriz = new Casilla[FILAS][COLUMNAS];
        inicializarTablero();
        colocarMinasAleatorias();
        calcularMinasAdyacentes();
    }

    private void inicializarTablero() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                matriz[i][j] = new Casilla();
            }
        }
    }

    private void colocarMinasAleatorias() {
        Random random = new Random();
        int minasColocadas = 0;

        while (minasColocadas < TOTAL_MINAS) {
            int f = random.nextInt(FILAS);
            int c = random.nextInt(COLUMNAS);
            if (!matriz[f][c].isTieneMina()) {
                matriz[f][c].setTieneMina(true);
                minasColocadas++;
            }
        }
    }

    private void calcularMinasAdyacentes() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (!matriz[i][j].isTieneMina()) {
                    matriz[i][j].setMinasAdyacentes(contarMinasAlrededor(i, j));
                }
            }
        }
    }

    private int contarMinasAlrededor(int fila, int col) {
        int contador = 0;
        for (int df = -1; df <= 1; df++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (df == 0 && dc == 0) continue;
                int nf = fila + df;
                int nc = col + dc;
                if (esCoordenadaValida(nf, nc) && matriz[nf][nc].isTieneMina()) {
                    contador++;
                }
            }
        }
        return contador;
    }

    public void descubrirCasilla(int fila, int col) throws CasillaYaDescubiertaException {
        if (!esCoordenadaValida(fila, col)) {
            throw new ArrayIndexOutOfBoundsException("Coordenada fuera del tablero.");
        }

        Casilla actual = matriz[fila][col];
        if (actual.isMarcada()) {
            return;
        }
        if (actual.isDescubierta()) {
            throw new CasillaYaDescubiertaException("Esa casilla ya fue descubierta.");
        }

        actual.setDescubierta(true);

        if (!actual.isTieneMina() && actual.getMinasAdyacentes() == 0) {
            for (int df = -1; df <= 1; df++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (df == 0 && dc == 0) continue;
                    int nf = fila + df;
                    int nc = col + dc;
                    if (esCoordenadaValida(nf, nc) && !matriz[nf][nc].isDescubierta() && !matriz[nf][nc].isTieneMina()) {
                        try {
                            descubrirCasilla(nf, nc);
                        } catch (CasillaYaDescubiertaException e) {
                            // Si ya estaba descubierta durante la expansión, continuamos
                        }
                    }
                }
            }
        }
    }

    public boolean esCoordenadaValida(int fila, int col) {
        return fila >= 0 && fila < FILAS && col >= 0 && col < COLUMNAS;
    }

    public Casilla getCasilla(int fila, int col) {
        return matriz[fila][col];
    }

    public int getFilas() {
        return FILAS;
    }

    public int getColumnas() {
        return COLUMNAS;
    }
}