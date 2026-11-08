package co.sena.autocloudparking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class SeguridadTest {

    @Test
    public void mismaClaveYSalDanElMismoHash() {
        assertEquals(Seguridad.hash("clave123", "sal1"), Seguridad.hash("clave123", "sal1"));
    }

    @Test
    public void claveDistintaDaHashDistinto() {
        assertNotEquals(Seguridad.hash("clave123", "sal1"), Seguridad.hash("otraClave", "sal1"));
    }

    @Test
    public void salDistintaDaHashDistinto() {
        assertNotEquals(Seguridad.hash("clave123", "sal1"), Seguridad.hash("clave123", "sal2"));
    }

    @Test
    public void elHashTiene64Caracteres() {
        assertEquals(64, Seguridad.hash("clave123", "sal1").length());
    }
}