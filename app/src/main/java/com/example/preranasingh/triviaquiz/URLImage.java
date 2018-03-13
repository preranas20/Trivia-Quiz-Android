

package com.example.preranasingh.triviaquiz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class URLImage extends AsyncTask<String,Void,Bitmap> {
    InputStream in;

    IData activity;

    public URLImage(IData activity){
        this.activity=activity;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
            try {
                //provide URL string
                URL url = new URL(params[0]);
                //to set connection to the url
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //set the Request method for the http page
                con.setRequestMethod("GET");
                in = con.getInputStream();
                //to read images
                Bitmap image = BitmapFactory.decodeStream(in);
                return image;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(in != null)
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
            return null;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(result != null){
            activity.setupData(result);
        }
    }

    public static interface IData{
        public void setupData(Bitmap data);

    }
}
