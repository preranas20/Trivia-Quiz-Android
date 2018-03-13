
package com.example.preranasingh.triviaquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity implements View.OnClickListener, GetContentsAsyncTask.IData {

    private int correctAnsData,total;
   // private int PercentageValue;
    private ProgressBar progress;
    private TextView progressText,progressMsg;
    private Button quit,tryAgain;
    private ArrayList<Question> questionsData=new ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        progress=(ProgressBar) findViewById(R.id.progressStats);
        progress.setMax(100);
        progressText= (TextView) findViewById(R.id.textPercentage);
        progressMsg = (TextView) findViewById(R.id.textQuizMessage);
        quit = (Button) findViewById(R.id.btnQuit);
        quit.setOnClickListener(this);
        tryAgain = (Button) findViewById(R.id.btnTry);
        tryAgain.setOnClickListener(this);

        if(getIntent().getExtras()!= null) {

            correctAnsData = getIntent().getExtras().getInt(TriviaActivity.STATS_KEY);
            total = getIntent().getExtras().getInt(TriviaActivity.TOTAL_KEY);
            updateStats(correctAnsData,total);
        }




    }

    public void updateStats(int correct,int total){

        int totalquesn=total;
        int correctAns=correct;
        int percentageValue = (int)((correctAns*100)/totalquesn);
        progress.setProgress(percentageValue);
        progressText.setText(percentageValue+"%");
        if(percentageValue==100){
            progressMsg.setText("All the Answers are correct!");
        }




    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btnTry:
                
                new GetContentsAsyncTask(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");


                break;
            case R.id.btnQuit:
                Intent intent = new Intent(StatsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                finish();
                break;
        }
    }

    @Override
    public void setupData(ArrayList<Question> data) {
        questionsData = data;
        Intent intent1 = new Intent(StatsActivity.this, TriviaActivity.class);
        intent1.putParcelableArrayListExtra(MainActivity.QUESTIONS_KEY,questionsData);
        startActivity(intent1);

        finish();
    }
}
