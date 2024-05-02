package KatrinZadek;
import org.example.CQuiz;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        CQuiz CQuiz = new CQuiz();
        try {
            CQuiz.loadQuestionsFromFile("C:\\Users\\kroup\\IdeaProjects\\KatrinZadek\\src\\main\\java\\org\\example\\Classes\\Quiz.txt");
            CQuiz.startQuiz();
        } catch (IOException e) {
            System.err.println("Chyba při načítání otázek ze souboru: " + e.getMessage());
        }
    }

}