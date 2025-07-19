import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.util.List;

public class QuizController {
    @FXML private Label questionLabel, feedbackLabel;
    @FXML private VBox optionsBox;
    @FXML private ProgressBar timerBar;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private Timeline timer;
    private double timeLeft = 1.0;

    @FXML
    public void initialize() {
        questions = QuestionBank.getQuestions();
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            showResults();
            return;
        }

        feedbackLabel.setText("");
        Question q = questions.get(currentQuestionIndex);
        questionLabel.setText(q.getQuestion());
        optionsBox.getChildren().clear();

        for (String option : q.getOptions()) {
            Button btn = new Button(option);
            btn.setPrefWidth(250);
            btn.setOnAction(e -> checkAnswer(option));
            optionsBox.getChildren().add(btn);
        }

        startTimer();
    }

    private void checkAnswer(String selected) {
        stopTimer();
        Question q = questions.get(currentQuestionIndex);

        if (selected.equals(q.getCorrectAnswer())) {
            score++;
            feedbackLabel.setText("Correct!");
            feedbackLabel.setStyle("-fx-text-fill: green;");
        } else {
            feedbackLabel.setText("Incorrect! Correct: " + q.getCorrectAnswer());
            feedbackLabel.setStyle("-fx-text-fill: red;");
        }

        currentQuestionIndex++;
        pauseBeforeNextQuestion();
    }

    private void startTimer() {
        timeLeft = 1.0;
        timerBar.setProgress(timeLeft);

        timer = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
            timeLeft -= 0.01;
            timerBar.setProgress(timeLeft);
            if (timeLeft <= 0) {
                stopTimer();
                feedbackLabel.setText("Time's up! Correct: " +
                    questions.get(currentQuestionIndex).getCorrectAnswer());
                feedbackLabel.setStyle("-fx-text-fill: orange;");
                currentQuestionIndex++;
                pauseBeforeNextQuestion();
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void stopTimer() {
        if (timer != null) timer.stop();
    }

    private void pauseBeforeNextQuestion() {
        Timeline pause = new Timeline(new KeyFrame(Duration.seconds(2), e -> loadQuestion()));
        pause.play();
    }

    private void showResults() {
        questionLabel.setText("Quiz Finished! Your Score: " + score + "/" + questions.size());
        optionsBox.getChildren().clear();
        timerBar.setProgress(0);
        feedbackLabel.setText("");
    }
}