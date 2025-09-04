package com.example.telequiz_20211602.model;

public class GameEntry {
    public enum Status { IN_PROGRESS, COMPLETED, CANCELED }

    public String topic;
    public long startMillis;     // hora de inicio
    public Long endMillis;       // hora de fin (null si en curso)
    public Integer score;        // null si en curso
    public Status status;

    public GameEntry(String topic, long startMillis) {
        this.topic = topic;
        this.startMillis = startMillis;
        this.status = Status.IN_PROGRESS;
    }

    public long durationSeconds() {
        if (endMillis == null) return 0;
        return Math.max(0, (endMillis - startMillis) / 1000);
    }
}
