package co.sena.autocloudparking;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculadoraTarifaTest {

    @Test
    public void carroUnaHora() {
        assertEquals(3000, CalculadoraTarifa.calcular("Carro", 60));
    }

    @Test
    public void minutosSeRedondeanHaciaArriba() {
        assertEquals(6000, CalculadoraTarifa.calcular("Carro", 61));
    }

    @Test
    public void minimoUnaHora() {
        assertEquals(2500, CalculadoraTarifa.calcular("Moto", 0));
    }

    @Test
    public void bicicleta() {
        assertEquals(1000, CalculadoraTarifa.calcular("Bicicleta", 30));
    }
}