package co.sena.autocloudparking;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class BusquedaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);

        EditText etBuscar = findViewById(R.id.et_buscar);
        Button btnBuscar = findViewById(R.id.btnBuscar);
        TextView tvResultado = findViewById(R.id.tv_resultado);

        btnBuscar.setOnClickListener(v -> {
            String placa = etBuscar.getText().toString().trim().toUpperCase();
            String[] ficha = new BaseDatos(this).buscar(placa);

            if (placa.isEmpty()){
             tvResultado.setText(BaseDatos.obtener(this).dentro());
             return;
            }

            if (ficha == null) {
                tvResultado.setText("Vehículo no encontrado");
            } else {
                tvResultado.setText("placa: " + placa + "\nTipo: " + ficha[0] + "\nIngreso: " + ficha[1]);
            }
        });

        }
    }

