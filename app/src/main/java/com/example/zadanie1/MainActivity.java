package com.example.zadanie1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questionTextView;

    private int currentQuestion = 0;

    private Question[] questions = new Question[] {
            new Question(R.string.q_ciri, false),
            new Question(R.string.q_swords, false),
            new Question(R.string.q_triss, true),
            new Question(R.string.q_jaskier, false),
            new Question(R.string.q_codex, false),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswerCorrectness(false);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion = (currentQuestion + 1) % questions.length;
                setNextQuestion();
            }
        });
        setNextQuestion();
    }


    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correntAnswer = questions[currentQuestion].isTrueAnswer();
        int resultMessageId = 0;
        if(userAnswer == correntAnswer)
            resultMessageId = R.string.correctAnswer;
        else
            resultMessageId = R.string.incorrectAnswer;
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentQuestion].getQuestionId());
    }

}
