package com.example.sennevervaecke.mystable;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by sennevervaecke on 7/25/2018.
 */

public class FindHorses extends AsyncTask<String, Void, Void>{

    private ArrayList<Horse> horses;
    private Handler handler;
    private Context context;
    private final String URLSTRING = "https://www.alsingen.be/mystable/findHorse.php?name=";
    private final String TAG = "FindHorses";
    public final static int RESPONSE = 0;
    public final static int NORESULT = -1;
    public final static int TOMANYRESULT = -2;
    public final static int TOSHORTINPUT = -3;
    public final static int NOINTERNET = -4;
    public final static int CONNECTIONERROR = -5;


    public FindHorses(Handler handler, Context context){
        this.handler = handler;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

    }
    @Override
    protected Void doInBackground(String... arg){
        if(!Helper.isConnected(context)) {
            Message.obtain(handler, NOINTERNET).sendToTarget();
            return null;
        }
        horses = new ArrayList<>();
        for (String horseName: arg) {
            try {
                //get response from server
                URL url = new URL(URLSTRING + horseName);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setChunkedStreamingMode(0);

                //check for response
                if(httpURLConnection.getResponseCode() == 200){
                    String response = "";
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                    //check for error messages
                    if(response.equals("-1")){
                        Message.obtain(handler, NORESULT).sendToTarget();
                    } else if(response.equals("-2")){
                        Message.obtain(handler, TOMANYRESULT).sendToTarget();
                    } else if(response.equals("-3")){
                        Message.obtain(handler, TOSHORTINPUT).sendToTarget();
                    } else {
                        //handle response
                        JSONArray jsonArray = new JSONArray(response);
                        int size = jsonArray.getJSONArray(0).getInt(0);

                        for (int i = 1; i <= size && i <= 100; i++) {
                            JSONObject jsonHorse = jsonArray.getJSONObject(i);
                            Horse horse = new Horse(
                                    jsonHorse.getString("name"),
                                    jsonHorse.getString("s_name"),
                                    jsonHorse.getString("d_name"),
                                    jsonHorse.getString("ds_name"),
                                    jsonHorse.getInt("year"),
                                    jsonHorse.getString("reg"),
                                    jsonHorse.getString("fei")
                            );
                            horses.add(horse);
                        }
                        Message.obtain(handler, RESPONSE, horses).sendToTarget();
                    }
                } else {
                    Message.obtain(handler, CONNECTIONERROR).sendToTarget();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
