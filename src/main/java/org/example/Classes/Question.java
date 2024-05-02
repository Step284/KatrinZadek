package org.example.Classes;

import java.util.List;

public class Question {
    private String text;
    private List<String> options;
    private String correctAnswer;
    private int points;

    public Question(String text, List<String> options, String correctAnswer, int points) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.points = points;
    }

    // Getters
    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getPoints() {
        return points;
    }

    // Method to check if user's answer is correct
    public boolean isCorrect(String userAnswer) {
        return correctAnswer.equalsIgnoreCase(userAnswer);
    }
}
