package org.example;

import org.example.Classes.*;
import java.io.*;
import java.util.*;

public class CQuiz {
    private List<Question> questions;
    private Scanner scanner;

    public CQuiz() {
        this.questions = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void loadQuestionsFromFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        List<String> currentQuestionLines = new ArrayList<>();
        for (String line : lines) {
            if (line.equals("/")) {
                // Process the lines collected for the current question
                processQuestionLines(currentQuestionLines);
                // Clear the list for the next question
                currentQuestionLines.clear();
            } else {
                // Add the line to the list for the current question
                currentQuestionLines.add(line);
            }
        }
    }

    private void processQuestionLines(List<String> questionLines) {
        String text = "";
        List<String> options = new ArrayList<>();
        String correctAnswer = "";
        int points = 0;

        for (String line : questionLines) {
            if (line.startsWith("Otázka")) {
                text = line.split(":")[1].trim();
            } else if (line.startsWith("Správně:")) {
                correctAnswer = line.split(":")[1].trim();
            } else if (line.startsWith("Špatně:")) {
                options.add(line.split(":")[1].trim());
            } else if (line.startsWith("Hodnota:")) {
                points = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        // Create the question object and add it to the list
        Question question = new Question(text, options, correctAnswer, points);
        questions.add(question);
    }

    private String promptUser(Question question) {
        System.out.println(question.getText());
        List<String> options = question.getOptions();
        Collections.shuffle(options);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.print("Zadej číslo odpovědi: ");

        int choice;
        while (true) {
            try {
                choice = scanner.nextInt();
                if (choice < 1 || choice > options.size()) {
                    throw new InputMismatchException("Invalid choice. Please enter a number between 1 and " + options.size());
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("NeuMuis CIST ?M ? ? ? ?? ");
                scanner.nextLine(); // Consume the invalid input
            }
        }
        return options.get(choice - 1);
    }

    public void startQuiz() {
        int totalPoints = 0;
        for (Question question : questions) {
            String userAnswer = promptUser(question);
            if (question.isCorrect(userAnswer)) {
                System.out.println("Správně!");
                totalPoints += question.getPoints();
            } else {
                System.out.println("Špatně.");
            }
        }
        System.out.println("Kvíz skončil. Celkový počet bodů: " + totalPoints);
    }
}