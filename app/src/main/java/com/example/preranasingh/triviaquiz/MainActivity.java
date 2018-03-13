

package com.example.preranasingh.triviaquiz;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetContentsAsyncTask.IData, View.OnClickListener {


    static final String QUESTIONS_KEY ="QUESTIONS" ;
    private TextView statusText;
    private RelativeLayout.LayoutParams lp;
    private Button Exit,Start;
    ArrayList<Question> questionsData =new  ArrayList<Question>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnStart).setEnabled(false);
        statusText = (TextView) findViewById(R.id.textLoadStatus);
        lp = (RelativeLayout.LayoutParams) statusText.getLayoutParams();
        Exit = (Button)findViewById(R.id.btnExit);
        Start = (Button) findViewById(R.id.btnStart);

        Exit.setOnClickListener(this);

        if(isConnectedOnline()){
           new GetContentsAsyncTask(this).execute("http://dev.theappsdr.com/apis/trivia_json/index.php");


           Exit.setOnClickListener(this);

           Start.setOnClickListener(this);
        }
        else
           Toast.makeText(this,"No Network Connected",Toast.LENGTH_LONG).show();



    }

    public void setupData(ArrayList<Question> data) {
        questionsData = data;
        findViewById(R.id.progressLoad).setVisibility(View.GONE);
        findViewById(R.id.imageTrivia).setVisibility(View.VISIBLE);
        statusText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        statusText.setText("Trivia Ready");
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        statusText.setLayoutParams(lp);

        findViewById(R.id.btnStart).setEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnExit:
                finish();
                System.exit(0);
                break;

            case R.id.btnStart:
                Intent intent = new Intent(MainActivity.this,TriviaActivity.class);
                intent.putParcelableArrayListExtra(QUESTIONS_KEY,questionsData);
                startActivity(intent);
                break;




        }
    }

    private boolean isConnectedOnline(){
        ConnectivityManager cm= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo!= null && networkInfo.isConnected())
            return true;
        return false;
    }
}
