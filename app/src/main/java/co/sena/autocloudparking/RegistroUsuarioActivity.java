package co.sena.autocloudparking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast ;


import androidx.appcompat.app.AppCompatActivity;

public class RegistroUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        EditText etUsuario  = findViewById(R.id.et_nuevo_usuario);
        EditText etContraseña= findViewById(R.id.et_nueva_contraseña);
        EditText etConfirmar= findViewById(R.id.et_confirmar_contraseña);
        Button   btnCrearCuenta= findViewById(R.id.btnCrearCuenta);
        Button   btnRegresar= findViewById(R.id.btnRegresar);

    btnCrearCuenta.setOnClickListener( v -> {
        String usuario = etUsuario.getText().toString().trim();
        String contraseña= etContraseña.getText().toString();
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
        } else if (contraseña.length() <6) {
            Toast.makeText(this,"La contraseña debe tener como minimo 6 caracteres",Toast.LENGTH_SHORT).show();
        } else if (!contraseña.equals(etConfirmar.getText().toString())) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        } else if (new BaseDatos(this).registrarUsuario(usuario,contraseña)) {
            Toast.makeText(this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this,"El usuario ya existe", Toast.LENGTH_LONG).show();

        }
        });
    btnRegresar.setOnClickListener(v-> finish());
    }
}