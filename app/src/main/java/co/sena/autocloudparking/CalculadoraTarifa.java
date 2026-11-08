package co.sena.autocloudparking;
public class CalculadoraTarifa {
    public static int valorHora(String tipo) {
    if(tipo.equals("Carro")) return 3000;
    if(tipo.equals("Moto"))  return 2500;
    return 1000;
}
public static long calcular(String tipo,long minutos) {
long horas = (minutos + 59)/60;
if (horas < 1) horas= 1;
return horas * valorHora(tipo);
}
}
