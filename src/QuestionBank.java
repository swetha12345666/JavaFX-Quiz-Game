import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new Question(
            "What is the capital of France?",
            new String[]{"Paris", "London", "Berlin", "Madrid"},
            "Paris"
        ));

        questions.add(new Question(
            "Which language is used for Android development?",
            new String[]{"Java", "Python", "Swift", "PHP"},
            "Java"
        ));

        questions.add(new Question(
            "What is 2 + 2?",
            new String[]{"3", "4", "5", "6"},
            "4"
        ));

        return questions;
    }
}