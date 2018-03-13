


package com.example.preranasingh.triviaquiz;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static java.lang.System.in;


public class QuestionUtil {

    static public class QuestionJSONParser{

        static ArrayList<Question> parseQuestions(String in) throws JSONException {


            ArrayList<Question> questionDetails =new ArrayList<Question>();
            ArrayList<String> choices = new ArrayList<String>();

            JSONObject root = new JSONObject(in);
            JSONArray questionsJSONArray = root.getJSONArray("questions");

            for(int i=0;i<questionsJSONArray.length();i++){

                JSONObject JSONQuestionObject = questionsJSONArray.getJSONObject(i);

                Question question = new Question();
                question.setId(JSONQuestionObject.getInt("id"));
                question.setText(JSONQuestionObject.getString("text"));
                try {
                    question.setImageURL(JSONQuestionObject.getString("image"));
                }catch(JSONException e){
                    question.setImageURL(null);
                }


                JSONObject JSONChoicesObject = JSONQuestionObject.getJSONObject("choices");
                JSONArray choiceJSONArray = JSONChoicesObject.getJSONArray("choice");
                question.setChoices(choiceJSONArray);
                question.setAnswer(JSONChoicesObject.getInt("answer"));

                questionDetails.add(question);
            }


            return questionDetails;
        }

    }
}
