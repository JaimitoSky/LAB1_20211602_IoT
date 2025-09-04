package com.example.telequiz_20211602;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.telequiz_20211602.data.QuizRepository;
import com.example.telequiz_20211602.model.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityGame extends AppCompatActivity {

    public static final String EXTRA_TOPIC = "topic";

    // Puntajes
    private static final int SCORE_CORRECT = 2;
    private static final int SCORE_WRONG   = -3;

    private String topic;
    private List<Question> questions = new ArrayList<>();
    private int index = 0;
    private int totalScore = 0;

    // Estado por pregunta
    private final Map<Integer, Integer> chosen = new HashMap<>();
    private final Map<Integer, Integer> qScore = new HashMap<>();

    // UI
    private TextView txtTopic, txtScore, txtQuestion, txtPerQuestionScore;
    private Button btnOpt1, btnOpt2, btnOpt3, btnOpt4, btnPrev, btnNext, btnHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Toolbar
        Toolbar tb = findViewById(R.id.toolbarGame);
        setSupportActionBar(tb);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Topic
        topic = getIntent().getStringExtra(EXTRA_TOPIC);
        if (topic == null) topic = QuizRepository.TOPIC_REDES;

        // UI refs
        txtTopic  = findViewById(R.id.txtTopic);
        txtScore  = findViewById(R.id.txtScore);
        txtQuestion = findViewById(R.id.txtQuestion);
        txtPerQuestionScore = findViewById(R.id.txtPerQuestionScore);
        btnOpt1 = findViewById(R.id.btnOpt1);
        btnOpt2 = findViewById(R.id.btnOpt2);
        btnOpt3 = findViewById(R.id.btnOpt3);
        btnOpt4 = findViewById(R.id.btnOpt4);
        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        btnHint = findViewById(R.id.btnHint);

        txtTopic.setText(topic);

        // Datos (toma hasta 7 para evitar index out of bounds)
        List<Question> all = QuizRepository.shuffledGame(topic);
        int take = Math.min(7, all.size());
        questions = new ArrayList<>(all.subList(0, take));

        // Listeners de opciones
        Button[] opts = {btnOpt1, btnOpt2, btnOpt3, btnOpt4};
        for (int k = 0; k < opts.length; k++) {
            final int optIndex = k;
            opts[k].setOnClickListener(v -> onChoose(optIndex));
        }

        // Pista
        btnHint.setOnClickListener(v ->
                Toast.makeText(this, getString(R.string.hint_text), Toast.LENGTH_SHORT).show());

        // Navegación
        btnPrev.setOnClickListener(v -> { if (index > 0) { index--; render(); } });
        btnNext.setOnClickListener(v -> {
            if (chosen.containsKey(index)) {
                if (index < questions.size() - 1) {
                    index++;
                    render();
                } else {
                    Toast.makeText(this, getString(R.string.toast_finish, totalScore), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

        render();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // ----- Menú (perfil) -----
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

    // ----- Render -----
    private void render() {
        Question q = questions.get(index);

        txtQuestion.setText(getString(R.string.question_numbered, index + 1, q.text));

        btnOpt1.setText(q.options.get(0));
        btnOpt2.setText(q.options.get(1));
        btnOpt3.setText(q.options.get(2));
        btnOpt4.setText(q.options.get(3));

        resetButton(btnOpt1); resetButton(btnOpt2);
        resetButton(btnOpt3); resetButton(btnOpt4);
        txtPerQuestionScore.setText("");

        if (chosen.containsKey(index)) paintAnswered();

        btnNext.setEnabled(chosen.containsKey(index));
        txtScore.setText(getString(R.string.score_prefix, totalScore));
        btnPrev.setEnabled(index > 0);
    }

    private void onChoose(int opt) {
        Question q = questions.get(index);

        // Quitar puntaje anterior si reelige
        int previous = qScore.getOrDefault(index, 0);
        totalScore -= previous;

        int earned = (opt == q.correctIndex) ? SCORE_CORRECT : SCORE_WRONG;
        qScore.put(index, earned);
        chosen.put(index, opt);
        totalScore += earned;

        btnNext.setEnabled(true);
        paintAnswered();
        txtScore.setText(getString(R.string.score_prefix, totalScore));
    }

    private void paintAnswered() {
        int opt = chosen.get(index);
        Question q = questions.get(index);

        Button[] opts = {btnOpt1, btnOpt2, btnOpt3, btnOpt4};
        for (int i = 0; i < opts.length; i++) {
            if (i == q.correctIndex) {
                tintButton(opts[i], 0xFFB9F6CA); // verde claro
            } else if (i == opt) {
                tintButton(opts[i], 0xFFFFCDD2); // rojo claro
            } else {
                resetButton(opts[i]);
            }
        }
        int earned = qScore.getOrDefault(index, 0);
        txtPerQuestionScore.setText(
                earned == 0 ? "" :
                        (earned > 0 ? getString(R.string.plus_points, earned)
                                : String.valueOf(earned))
        );
    }

    private void resetButton(Button b) { b.setBackgroundTintList(null); }
    private void tintButton(Button b, int color) { b.setBackgroundTintList(ColorStateList.valueOf(color)); }
}
