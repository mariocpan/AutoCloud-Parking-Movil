package co.sena.autocloudparking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Button btnRegistro = findViewById(R.id.btnregistro);
        btnRegistro.setOnClickListener(v ->
                startActivity(new Intent(this, RegistroActivity.class)));

        Button btnBusqueda = findViewById(R.id.btnbusqueda);
        btnBusqueda.setOnClickListener(v ->
                startActivity(new Intent(this, BusquedaActivity.class)));

        Button btnTarifa = findViewById(R.id.btntarifa);
        btnTarifa.setOnClickListener(v ->
                startActivity(new Intent(this, TarifaActivity.class)));

        Button btnReporte = findViewById(R.id.btnreporte);
        btnReporte.setOnClickListener(v ->
                startActivity(new Intent(this, ReporteActivity.class)));

        Button btnSalir = findViewById(R.id.btnSalir);
        btnSalir.setOnClickListener(v -> finish());
    }
}