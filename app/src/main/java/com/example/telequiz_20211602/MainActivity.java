package com.example.telequiz_20211602;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private EditText edtNombre;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb = findViewById(R.id.toolbarMain);
        if (tb != null) setSupportActionBar(tb);

        // Referencias a la interfaz gráfica
        edtNombre = findViewById(R.id.edtNombre);
        btnEntrar = findViewById(R.id.btnEntrar);

        // Navegación a SecondActivity
        btnEntrar.setOnClickListener(v -> {
            String nombre = edtNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                Toast.makeText(MainActivity.this, "Ingresa tu nombre", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent i = new Intent(MainActivity.this, SecondActivity.class);
            i.putExtra("nombreUsuario", nombre);
            startActivity(i);
        });
    }
}
