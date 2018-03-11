/*Homework 04
GetContentsAsyncTask.java
authors:HW Group 8-
        Prerana Singh
        Vaijyant Tomar
 */


package com.example.preranasingh.triviaquiz;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class GetContentsAsyncTask extends AsyncTask<String, Object, ArrayList<Question>> {

    IData activity;

    public GetContentsAsyncTask(IData activity){
        this.activity=activity;
    }

    @Override
    protected ArrayList<Question> doInBackground(String... params) {
        StringBuilder sb=null;
        try {
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();

            int statusCode = con.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                sb=new StringBuilder();

                String line = reader.readLine();

                while(line != null){
                    sb.append(line);
                    line=reader.readLine();
                }

            }
            return QuestionUtil.QuestionJSONParser.parseQuestions(sb.toString());
          //  return sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

   @Override
    protected void onPostExecute(ArrayList<Question> result) {
        activity.setupData(result);

    }

    public static interface IData{
        public void setupData(ArrayList<Question> data);

    }
}
