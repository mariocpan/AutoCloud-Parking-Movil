package co.sena.autocloudparking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TarifaActivity extends AppCompatActivity {

    private String placaActual;
    private long   totalActual;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tarifa);

        EditText etPlaca = findViewById(R.id.et_placa_tarifa);
        Button btncalcular = findViewById(R.id.btnCalcular);
        Button btnCobrar =    findViewById(R.id.btnCobrar);
        Button btnCancelar = findViewById(R.id.btnCancelar);
        TextView  tvResultado = findViewById(R.id.tv_tarifa_resultado);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        btncalcular.setOnClickListener(v -> {
            placaActual = etPlaca.getText().toString().trim().toUpperCase();
            String[] ficha = new BaseDatos(this).buscar(placaActual);

              if(ficha == null) {
               tvResultado.setText("Vehículo no encontrado");
               btnCobrar.setVisibility(View.GONE);
               return;
            }
            try {
                Date ingreso = formato.parse(ficha[1]);
                long minutos = (new Date().getTime() - ingreso.getTime()) / 60000;
                totalActual = CalculadoraTarifa.calcular(ficha[0], minutos);
                tvResultado.setText("Tipo: " + ficha[0] + "\nIngreso " + ficha[1] + "\nTiempo: " + minutos + "min\nTotal a pagar:$" + totalActual);
                btnCobrar.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                tvResultado.setText("No fue posible calcular la tarifa");
            }
            });
            btnCobrar.setOnClickListener(v -> {
                String salida = formato.format(new Date());
                if(new BaseDatos(this).cobrar(placaActual, salida, totalActual)) {
                    tvResultado.setText( "Cobro exitoso\nplaca: " + placaActual + "\nTotal cobrado: $" + totalActual);
                    btnCobrar.setVisibility(View.GONE);
                } else {
                  Toast.makeText(this,"No se pudo registar el cobro", Toast.LENGTH_SHORT).show();
                }
                });
            btnCancelar.setOnClickListener(v -> finish());
    }
    }
