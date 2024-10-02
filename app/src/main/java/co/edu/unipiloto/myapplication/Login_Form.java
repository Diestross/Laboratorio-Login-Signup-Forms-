package co.edu.unipiloto.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class Login_Form extends AppCompatActivity {

    private TextInputLayout usuarioLayout, passwordLayout;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_form);
        getSupportActionBar().setTitle("Login");

        dbHelper = new DatabaseHelper(this);

        usuarioLayout = findViewById(R.id.usuario);
        passwordLayout = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.Iniciarsecion);
        loginButton.setOnClickListener(v -> loginUser());
    }

    public void btn_signupForm(View view){
        startActivity(new Intent(getApplicationContext(), Signup_Form.class));
    }
    private void loginUser() {
        String usuario = usuarioLayout.getEditText().getText().toString().trim();
        String password = passwordLayout.getEditText().getText().toString().trim();

        // Verificar si los campos están vacíos
        if (usuario.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validar el login
        boolean result = dbHelper.checkUser(usuario, password);

        if (result) {
            Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
            // Redirigir a la actividad principal
            Intent intent = new Intent(Login_Form.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
}