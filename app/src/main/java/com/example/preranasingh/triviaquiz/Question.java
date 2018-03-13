
package com.example.preranasingh.triviaquiz;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class Question  implements Parcelable{

    int id;
    int answer;
    String text,imageURL;
    ArrayList<String> choices=new ArrayList<String>() ;

    JSONArray jsonChoices;


    public Question() {
    }




    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArrayList<String> getChoices() {

        return choices;
    }

    public ArrayList<String> setChoices(JSONArray jsonChoices) throws JSONException {
       this.jsonChoices = jsonChoices;
        for(int i = 0; i < jsonChoices.length(); i++){
            choices.add(String.valueOf(jsonChoices.get(i)));
       }


        return choices;
    }

    @Override
    public String toString() {
        return "id:"+id+"text:"+text+"imageURL:"+imageURL+"choices:"+choices+"answer:"+answer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(text);
        parcel.writeString(imageURL);
        parcel.writeSerializable(choices);
        parcel.writeInt(answer);

    }public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    private Question(Parcel in) {
        this.id=in.readInt();
        this.text=in.readString();
        this.imageURL= in.readString();
        this.choices= (ArrayList<String>) in.readSerializable();
        this.answer=in.readInt();
    }



}
