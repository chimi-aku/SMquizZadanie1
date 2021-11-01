package com.example.zadanie1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private static final String QUIZ_TAG = "MainActivity";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.example.myapplication.PromptActivity.correctAnswer";
    private static  final int REQUEST_CODE_PROMPT = 0;

    private int currentindex = 0;
    boolean answerWasShow;

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button promptButton;
    private TextView questionTextView;


    private Question[] questions = new Question[] {
            new Question(R.string.q_ciri, false),
            new Question(R.string.q_swords, false),
            new Question(R.string.q_triss, true),
            new Question(R.string.q_jaskier, false),
            new Question(R.string.q_codex, false),
    };


    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG,"Wywołana została metoda onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX,currentindex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia on_Create");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            currentindex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        promptButton = findViewById(R.id.hint);
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
                answerWasShow = false;
                currentindex = (currentindex + 1) % questions.length;
                setNextQuestion();
            }
        });
        setNextQuestion();

        promptButton.setOnClickListener((v) -> {
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer = questions[currentindex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            //startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){return;}
        if(requestCode == REQUEST_CODE_PROMPT){
            if(data ==  null){return;}
            answerWasShow = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOW, false);
        }
    }

    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentindex].isTrueAnswer();
        int resultMessageId = 0;
        if(answerWasShow){
            resultMessageId = R.string.answer_was_shown;
        }else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correctAnswer;
            } else {
                resultMessageId = R.string.incorrectAnswer;
            }
        }
        Toast.makeText(this,resultMessageId,Toast.LENGTH_SHORT).show();

    }



    private void setNextQuestion() {
        questionTextView.setText(questions[currentindex].getQuestionId());
    }




    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia onStart ");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia onResume ");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia onStop ");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG,"Wywołana została metoda cyklu życia onDestroy");
    }

}
