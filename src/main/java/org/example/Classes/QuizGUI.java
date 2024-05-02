package org.example.Classes;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

    public class QuizGUI extends Application {

        private Stage primaryStage;
        private Scene scene;
        private Label questionLabel;
        private Button[] optionButtons;

        @Override
        public void start(Stage primaryStage) {
            this.primaryStage = primaryStage;
            this.questionLabel = new Label();
            this.optionButtons = new Button[4]; // Assuming 4 options per question

            // Create layout
            VBox layout = new VBox(10);
            layout.getChildren().add(questionLabel);
            for (int i = 0; i < optionButtons.length; i++) {
                optionButtons[i] = new Button();
                int optionNumber = i + 1;
                optionButtons[i].setOnAction(e -> handleOptionClick(optionNumber));
                layout.getChildren().add(optionButtons[i]);
            }

            // Create scene
            scene = new Scene(layout, 400, 300);

            // Set up stage
            primaryStage.setTitle("Quiz");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        private void handleOptionClick(int optionNumber) {
            // Handle user's option selection
            System.out.println("User selected option " + optionNumber);
            // You can perform further actions based on the user's selection here
        }

        public void displayQuestion(String question, String[] options) {
            questionLabel.setText(question);
            for (int i = 0; i < options.length && i < optionButtons.length; i++) {
                optionButtons[i].setText(options[i]);
            }
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
