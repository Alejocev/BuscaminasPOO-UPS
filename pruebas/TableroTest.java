package pruebas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import excepciones.CasillaYaDescubiertaException;
import modelo.Tablero;
import persistencia.GestorPartida;

class TableroTest {

    @Test
    void testDimensionesTablero() {
        Tablero tablero = new Tablero();
        assertEquals(10, tablero.getFilas());
        assertEquals(10, tablero.getColumnas());
    }

    @Test
    void testCoordenadaValidaEInvalida() {
        Tablero tablero = new Tablero();
        assertTrue(tablero.esCoordenadaValida(0, 0));
        assertTrue(tablero.esCoordenadaValida(9, 9));
        assertFalse(tablero.esCoordenadaValida(-1, 5));
        assertFalse(tablero.esCoordenadaValida(10, 2));
    }

    @Test
    void testCasillaYaDescubiertaLanzaExcepcion() throws CasillaYaDescubiertaException {
        Tablero tablero = new Tablero();
        tablero.descubrirCasilla(0, 0);
        assertThrows(CasillaYaDescubiertaException.class, () -> {
            tablero.descubrirCasilla(0, 0);
        });
    }

    @Test
    void testGuardarYCargarPartida() throws Exception {
        Tablero original = new Tablero();
        original.getCasilla(2, 3).setMarcada(true);

        GestorPartida gestor = new GestorPartida();
        gestor.guardarPartida(original);
        Tablero cargado = gestor.cargarPartida();

        assertNotNull(cargado);
        assertTrue(cargado.getCasilla(2, 3).isMarcada());
    }
}