package com.example.telequiz_20211602;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ResultActivity extends AppCompatActivity {

    private TextView txtTopic, txtTitle, txtScore;
    private Button btnBack, btnPlayAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar tb = findViewById(R.id.toolbarResult);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtTopic = findViewById(R.id.txtTopic);
        txtTitle = findViewById(R.id.txtTitle);
        txtScore = findViewById(R.id.txtScore);
        btnBack = findViewById(R.id.btnBack);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);

        String topic = getIntent().getStringExtra("topic");
        int score = getIntent().getIntExtra("score", 0);

        txtTopic.setText(getString(R.string.result_topic, topic));
        txtTitle.setText(getString(R.string.result_title));
        txtScore.setText(String.valueOf(score));

        // color: verde si positivo, rojo si negativo
        int bg = score >= 0 ? 0xFFB9F6CA : 0xFFFFCDD2;
        txtScore.setBackgroundTintList(android.content.res.ColorStateList.valueOf(bg));

        btnBack.setText(getString(R.string.back));
        btnBack.setOnClickListener(v -> finish()); // vuelve a la última pregunta (por si quieres revisar)

        btnPlayAgain.setText(getString(R.string.play_again));
        btnPlayAgain.setOnClickListener(v -> {
            // volver a selección de temáticas
            Intent i = new Intent(this, SecondActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
            finish();
        });
    }

    @Override
    public boolean onSupportNavigateUp() { finish(); return true; }
}
