package co.sena.autocloudparking;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ReporteActivity extends AppCompatActivity {
    private  final List<String[]> filtradas = new ArrayList<>();
    private String titulo ="";
    private  long totalActual = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        Spinner spPeriodo = findViewById(R.id.sp_periodo);
        Spinner spTipo =   findViewById(R.id.sp_tipo_reporte);
        Button btnGenerar =  findViewById(R.id.btnGenerar);
        Button btnCancelar = findViewById(R.id.btnCancelar);
        Button btnDescargar = findViewById(R.id.btnDescargar);
        TextView tvReporte = findViewById(R.id.tv_reporte);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        btnGenerar.setOnClickListener(v -> {
            Calendar inicio = Calendar.getInstance();
            inicio.set(Calendar.HOUR_OF_DAY, 0);
            inicio.set(Calendar.MINUTE, 0);
            inicio.set(Calendar.SECOND,0);

            int periodo = spPeriodo.getSelectedItemPosition();
            if (periodo == 1) inicio.add(Calendar.DAY_OF_YEAR, -6);
            if (periodo == 2) inicio.set(Calendar.DAY_OF_MONTH, 1);
            if (periodo == 3) inicio.set(Calendar.DAY_OF_YEAR, 1);

            String tipo = spTipo.getSelectedItem().toString();
            StringBuilder texto = new StringBuilder();
            long total = 0;
            filtradas.clear();

            for(String[] f : BaseDatos.obtener(this).cobrados()) {
                try {
                    Date salida = formato.parse(f[3]);
                    if (salida.before(inicio.getTime())) continue;
                    if (!tipo.equals("Todos") && !tipo.equals(f[1])) continue;
                    texto.append(f[0]).append("-").append(f[1])
                            .append("\n Salida: ").append(f[3])
                            .append("\n Valor: ").append(f[4]).append("\n\n");
                    total += Long.parseLong(f[4]);
                    filtradas.add(f);
                } catch (Exception e) {
                }

            }
            totalActual = total;
            titulo = "Reporte" + spPeriodo.getSelectedItem() +"-" + tipo ;
            if (filtradas.isEmpty()) {
                tvReporte.setText("No existen datos para ese periodo");
            } else {
                tvReporte.setText("Vehiculos: " + filtradas.size() + "\nTotal recaudado: $" + total + "\n\n" + texto );
            }
    });
        btnDescargar.setOnClickListener(v -> {
            if(filtradas.isEmpty()) {
                Toast.makeText(this, "Sellecione primero datos parsa generar el reprote", Toast.LENGTH_SHORT).show();
                 return;
            }
            String nombre = "reporte_" + new SimpleDateFormat("ddMMyyyy_HHmm", Locale.getDefault()).format(new Date());
            new AlertDialog.Builder(this)
                    .setTitle("Desgargar como")
                    .setItems(new String[]{"PDF", "EXEL"},(dialogo, opcion) -> {
                         boolean ok;
                         if(opcion == 0){
                             List<String> lineas = new ArrayList<>();
                             lineas.add("AUTOCLOUD PARKING -" + titulo);
                             lineas.add("Vehículos:" + filtradas.size() + "Total recaudo: $" + totalActual);
                             lineas.add("");
                             for(String[] f : filtradas) {
                                 lineas.add(f[0] + "-" + f[1] +"| salida" + f[3] + "| $"+ f[4]);
                             }
                             ok = Exportar.pdf(this, nombre +".pdf",lineas);
                         } else {
                             ok = Exportar.csv(this, nombre +".csv", filtradas);
                         }
                         Toast.makeText(this, ok ? "Guardado en Descargas" : "No se pudo descargar", Toast.LENGTH_LONG).show();
                    })
                    .show();
        });
        btnCancelar.setOnClickListener( v -> finish());
    }
}