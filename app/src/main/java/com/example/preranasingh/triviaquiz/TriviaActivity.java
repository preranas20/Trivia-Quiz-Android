

package com.example.preranasingh.triviaquiz;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.lang.Character.FORMAT;

public class TriviaActivity extends AppCompatActivity implements URLImage.IData, RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private static final String FORMAT = "%02d:%02d:%02d";
    static final String STATS_KEY = "STATUS";
    static final String TOTAL_KEY ="TOTAL" ;
    int seconds , minutes;
    ArrayList<Question> questionData = new ArrayList<Question>();
    private TextView textTime,question,questionNo;
    private RadioGroup rg;
    private RadioButton checkedButton;
    private ImageView questionImage;
    private ProgressBar progressImageStatus;
    int i=0,k,count=0,radioId,answer,total,correctAnswers;
    private Button next,quit;
    private android.content.Intent statsIntent;


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        textTime= (TextView) findViewById(R.id.textStopWatch);
        question = (TextView) findViewById(R.id.textQuestion);
        rg = (RadioGroup) findViewById(R.id.radiogroupChoices);
        progressImageStatus = (ProgressBar) findViewById(R.id.progressImageLoad);
        questionNo= (TextView) findViewById(R.id.textQNo);
        questionImage= (ImageView) findViewById(R.id.imageView2);
        next= (Button) findViewById(R.id.btnNext);
        next.setOnClickListener(this);
        quit= (Button) findViewById(R.id.btnQuit);
        quit.setOnClickListener(this);

        k=0;

        statsIntent = new Intent(TriviaActivity.this,StatsActivity.class);
        if(getIntent().getExtras()!= null){

            questionData = getIntent().getExtras().getParcelableArrayList(MainActivity.QUESTIONS_KEY);


            getQuestions(k);



           new CountDownTimer(120000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                textTime.setText(""+String.format("Time Left: %03d seconds",
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
                if(TriviaActivity.this.isFinishing())
                  cancel();
            }


            public void onFinish() {
                goToNextActivity();


            }
        }.start();



    }

}

    public void getQuestions(int k) {
        rg.setVisibility(View.INVISIBLE);
        rg.removeAllViews();


    progressImageStatus.setVisibility(View.VISIBLE);
        questionImage.setVisibility(View.INVISIBLE);

    question.setText(questionData.get(k).getText().toString());

    questionNo.setText("Q " + (questionData.get(k).getId() + 1));
        if(questionData.get(k).getImageURL()!=null) {

            new URLImage(this).execute(questionData.get(k).getImageURL());
        }
        else {
            progressImageStatus.setVisibility(View.INVISIBLE);
            questionImage.setImageResource(R.drawable.shape);
            rg.setVisibility(View.VISIBLE);
        }
    answer = questionData.get(k).getAnswer();
    for (int j = 0; j < questionData.get(k).getChoices().size(); j++) {
        RadioButton rb = new RadioButton(this);
        radioId = Integer.parseInt("1000" + (j+1) + k);
        rb.setId(radioId);
        rb.setText(questionData.get(k).getChoices().get(j).toString());
        rg.addView(rb);


    }

    rg.setOnCheckedChangeListener(this);
    }




    @Override
    public void setupData(Bitmap data) {
        progressImageStatus.setVisibility(View.INVISIBLE);
        questionImage.setVisibility(View.VISIBLE);
        questionImage.setImageBitmap(data);
        rg.setVisibility(View.VISIBLE);;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedId) {

             if(progressImageStatus.getVisibility()==View.VISIBLE)
                 rg.setEnabled(false);

            int selectedIndex =String.valueOf(checkedId).charAt(4) - '0';;
            if (selectedIndex == answer) {
                count = count + 1;

            }else{
                Toast.makeText(this,"Wrong Option Selected ",Toast.LENGTH_LONG).show();

            }
        int m=++i;
        if(m<questionData.size())
            getQuestions(m);
        else
            goToNextActivity();


        }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnNext:
                goToNextActivity();
                break;
            case R.id.btnQuit:
                Intent intent = new Intent(TriviaActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;


        }
    }

    public void goToNextActivity(){
        total = questionData.size();
        correctAnswers = count;
        statsIntent.putExtra(STATS_KEY,correctAnswers);
        statsIntent.putExtra(TOTAL_KEY,total);
        startActivity(statsIntent);
        finish();
    }
}


