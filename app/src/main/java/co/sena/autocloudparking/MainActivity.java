package co.sena.autocloudparking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciodesesion);

        EditText etUsuario= findViewById(R.id.et_usuario);
        EditText etPassword= findViewById(R.id.et_password);
        Button btnIngresar = findViewById(R.id.btn_Ingresar);
        Button btnCrearCuenta = findViewById(R.id.btn_CrearCuenta);
        btnCrearCuenta.setOnClickListener( v ->
                startActivity(new Intent(this, RegistroUsuarioActivity.class)));

        btnIngresar.setOnClickListener(v -> {
        String usuario = etUsuario.getText().toString().trim();
        String contraseña = etPassword.getText().toString();

         if (usuario.isEmpty() || contraseña.isEmpty()) {
             Toast.makeText(this, "Ingrese Usuario y Contraseña", Toast.LENGTH_SHORT).show();
         } else if (new BaseDatos(this).validarUsuario(usuario, contraseña)) {
         startActivity(new Intent(this, MenuPrincipalActivity.class));
        } else {
            Toast.makeText(this,"Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
         }
    });
    }
}