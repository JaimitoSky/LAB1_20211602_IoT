package com.example.telequiz_20211602;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.telequiz_20211602.data.StatsStore;
import com.example.telequiz_20211602.model.GameEntry;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView tvPlayer = findViewById(R.id.tvPlayer);
        TextView tvStart  = findViewById(R.id.tvStart);
        TextView tvCount  = findViewById(R.id.tvCount);
        LinearLayout container = findViewById(R.id.containerHistory);

        // cabecera
        String name = (StatsStore.playerName == null || StatsStore.playerName.isEmpty())
                ? "-" : StatsStore.playerName;
        tvPlayer.setText(getString(R.string.profile_player, name));
        tvStart.setText(getString(R.string.profile_start, StatsStore.formatTime(StatsStore.appStartMillis)));
        tvCount.setText(getString(R.string.profile_count, StatsStore.getHistory().size()));

        // líneas de historial
        int green = 0xFF2E7D32; // verde fuerte
        int red   = 0xFFC62828; // rojo fuerte
        int orange= 0xFFEF6C00; // naranja

        container.removeAllViews();
        int idx = 1;
        for (GameEntry e : StatsStore.getHistory()) {
            TextView row = new TextView(this);
            row.setTextSize(16);

            if (e.status == GameEntry.Status.COMPLETED) {
                String line = getString(R.string.profile_line, e.topic, e.durationSeconds(), e.score);
                row.setText("Juego " + idx + ": " + line);
                // color por signo del puntaje
                int color = (e.score != null && e.score >= 0) ? green : red;
                row.setTextColor(color);
            } else if (e.status == GameEntry.Status.CANCELED) {
                row.setText("Juego " + idx + ": " + getString(R.string.profile_canceled));
                row.setTextColor(red);
            } else { // IN_PROGRESS
                String line = getString(R.string.profile_line_in_progress, e.topic,
                        StatsStore.formatTime(e.startMillis),
                        getString(R.string.profile_in_progress));
                row.setText("Juego " + idx + ": " + line);
                row.setTextColor(orange);
            }

            // un poco de padding y separación
            row.setPadding(0, 8, 0, 8);
            container.addView(row);
            idx++;
        }
    }
}
