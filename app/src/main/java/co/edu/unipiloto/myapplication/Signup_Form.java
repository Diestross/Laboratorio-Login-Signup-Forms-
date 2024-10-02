package co.edu.unipiloto.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Signup_Form extends AppCompatActivity {

    private TextInputLayout nombreLayout, usuarioLayout, correoLayout, direccionLayout, passwordLayout, confirmPasswordLayout, geolocalizacionLayout;
    private RadioGroup generoGroup;
    private Spinner rolSpinner;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle saveInstaceState){
        super.onCreate(saveInstaceState);
        setContentView(R.layout.activity_signup_form);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Signup");
        }

        dbHelper = new DatabaseHelper(this);

        nombreLayout = findViewById(R.id.nombre);
        usuarioLayout = findViewById(R.id.usuario);
        correoLayout = findViewById(R.id.correo);
        direccionLayout = findViewById(R.id.direccion);
        passwordLayout = findViewById(R.id.password);
        confirmPasswordLayout = findViewById(R.id.password_check);
        generoGroup = findViewById(R.id.radioGroup);
        rolSpinner = findViewById(R.id.spinner_rol);
        geolocalizacionLayout = findViewById(R.id.direccion_geolocalizacion);

        Button registerButton = findViewById(R.id.registrarse);
        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String nombre = nombreLayout.getEditText().getText().toString().trim();
        String usuario = usuarioLayout.getEditText().getText().toString().trim();
        String correo = correoLayout.getEditText().getText().toString().trim();
        String direccion = direccionLayout.getEditText().getText().toString().trim();
        String password = passwordLayout.getEditText().getText().toString().trim();
        String confirmPassword = confirmPasswordLayout.getEditText().getText().toString().trim();
        String coordenadas = geolocalizacionLayout.getEditText().getText().toString().trim();

        // Verificar si todos los campos están llenos
        if (nombre.isEmpty() || usuario.isEmpty() || correo.isEmpty() || direccion.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty() || coordenadas.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si las contraseñas coinciden
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar si se seleccionó un género
        if (generoGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Por favor, selecciona un género", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener género seleccionado
        String genero = ((RadioButton) findViewById(generoGroup.getCheckedRadioButtonId())).getText().toString();

        // Obtener el rol seleccionado
        String rol = rolSpinner.getSelectedItem().toString();

        // Intentar registrar al usuario
        boolean result = dbHelper.registerUser(nombre, usuario, correo, direccion, password, genero, rol, coordenadas);

        if (result) {
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            // Redirigir al login
            Intent intent = new Intent(Signup_Form.this, Login_Form.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show();
        }
    }


}
