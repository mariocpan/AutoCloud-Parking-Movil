package co.sena.autocloudparking;
import android.widget.EditText;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class RegistroActivity extends AppCompatActivity {

@Override
protected void onCreate(Bundle saveInstanceState) {
    super.onCreate(saveInstanceState);
    setContentView(R.layout.activity_registro);
    EditText etPlaca = findViewById(R.id.et_placa);
    EditText etFecha = findViewById(R.id.et_fecha);
    Spinner  spTipo  = findViewById(R.id.sp_tipo_vehiculo);
    Button btnIngresar = findViewById(R.id.btnIngresar);
    Button btnCancelar = findViewById(R.id.btnCancelar);

    String ahora = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
    etFecha.setText(ahora);

    btnIngresar.setOnClickListener(v -> {
        String placa = etPlaca.getText().toString().trim().toUpperCase();
        String tipo =  spTipo.getSelectedItem().toString();

        if (spTipo.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Por favor, seleccione el tipo de vehículo", Toast.LENGTH_SHORT).show();
            return;
        }

        if (tipo.equals("Bicicleta")) {
            placa = BaseDatos.obtener(this).siguenteCodigoBici();
        } else if  (!placa.matches("[A-Z]{3}[0-9]{3}")) {
            Toast.makeText(this, "La placa debe ser 3 letras y 3 números (ej: ABC123)", Toast.LENGTH_SHORT).show();
            return;

        }
        final String codigo = placa;
        if ( BaseDatos.obtener(this).guardar(codigo, tipo,etFecha.getText().toString())) {
            new AlertDialog.Builder(this)
                    .setTitle("Registro exitoso")
                    .setMessage((tipo.equals("Bicicleta") ? "Código de la bicicleta: " : "Placa: ")
                            + codigo + "\n\nEntregue este código al cliente.")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar",(d,w)-> finish())
                    .show();

        } else {
            Toast.makeText(this,"La placa ya fue registrada", Toast.LENGTH_SHORT).show();
        }
    });
    btnCancelar.setOnClickListener(v -> finish());
}
}