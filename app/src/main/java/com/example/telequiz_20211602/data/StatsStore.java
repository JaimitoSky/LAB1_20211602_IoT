package com.example.telequiz_20211602.data;

import com.example.telequiz_20211602.model.GameEntry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class StatsStore {
    public static String playerName = "";           // nombre
    public static long appStartMillis = System.currentTimeMillis();
    private static final List<GameEntry> history = new ArrayList<>();
    private static GameEntry current = null;

    public static GameEntry startGame(String topic) {
        current = new GameEntry(topic, System.currentTimeMillis());
        history.add(current);
        return current;
    }

    public static void finishCurrent(int score) {
        if (current != null && current.status == GameEntry.Status.IN_PROGRESS) {
            current.status = GameEntry.Status.COMPLETED;
            current.score = score;
            current.endMillis = System.currentTimeMillis();
            current = null;
        }
    }

    public static void cancelCurrent() {
        if (current != null && current.status == GameEntry.Status.IN_PROGRESS) {
            current.status = GameEntry.Status.CANCELED;
            current.endMillis = System.currentTimeMillis();
            current = null;
        }
    }

    public static GameEntry getCurrent() { return current; }
    public static List<GameEntry> getHistory() { return history; }

    public static String formatTime(long millis) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date(millis));
    }
}
