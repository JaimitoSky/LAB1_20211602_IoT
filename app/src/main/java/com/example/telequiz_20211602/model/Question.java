package com.example.telequiz_20211602.model;

import java.util.List;

public class Question {
    public String text;
    public List<String> options;   // 4 opciones
    public int correctIndex;       // índice correcto dentro de options

    public Question(String text, List<String> options, int correctIndex) {
        this.text = text;
        this.options = options;
        this.correctIndex = correctIndex;
    }
}