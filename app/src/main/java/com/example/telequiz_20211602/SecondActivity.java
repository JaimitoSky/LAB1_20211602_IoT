package com.example.telequiz_20211602;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.telequiz_20211602.data.QuizRepository;

public class SecondActivity extends AppCompatActivity {

    private TextView txtBienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // Toolbar
        Toolbar tb = findViewById(R.id.toolbarSecond);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Bienvenida
        txtBienvenido = findViewById(R.id.txtBienvenido);
        String nombre = getIntent().getStringExtra("nombreUsuario");
        if (nombre == null) nombre = "";
        txtBienvenido.setText(getString(R.string.label_bienvenido) + " " + nombre);

        // Botones de temáticas
        findViewById(R.id.btnRedes).setOnClickListener(v -> startGame(QuizRepository.TOPIC_REDES));
        findViewById(R.id.btnCiber).setOnClickListener(v -> startGame(QuizRepository.TOPIC_CIBER));
        findViewById(R.id.btnMicro).setOnClickListener(v -> startGame(QuizRepository.TOPIC_MICRO));
    }

    private void startGame(String topic) {
        Intent i = new Intent(this, ActivityGame.class);
        i.putExtra(ActivityGame.EXTRA_TOPIC, topic);
        startActivity(i);
    }


    @Override
    public boolean onSupportNavigateUp() {
        // Botón atrás de la barra → volver a inicio
        finish();
        return true;
    }

    // ----- Menú superior -----
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
