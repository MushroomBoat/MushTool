package fr.pv.mushtool;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sail {
    private JSONArray twa, tws;
    private  JSONObject [] sail;
    private ArrayList<Integer> sailId;
    private ArrayList<String> sailName;
    private int numberOfSail=0;

    private double [][][] speedSail;
    private JSONArray [] speed;




    public static final String TAG = "Sail";
    Sail(JSONArray twa, JSONArray tws, JSONArray sail) throws JSONException {
        this.twa = twa;
        this.tws = tws;
        this.numberOfSail = sail.length();
        //this.sail= sail;
        this.sailName = new ArrayList<>();
        this.sailId = new ArrayList<>();
        this.speed = new JSONArray[sail.length()];
        this.sail = new JSONObject[sail.length()];

        for(int i = 0; i<this.numberOfSail; i++){
            this.sail[i] = sail.getJSONObject(i);
            this.sailId.add(this.sail[i].getInt("id"));
            this.sailName.add(this.sail[i].getString("name"));
            this.speed[i]= this.sail[i].getJSONArray("speed");

        }


        Log.d(TAG, "speed1:"+ this.speed[0]);
        Log.d(TAG, "speed2:"+ this.speed[1]);
        Log.d(TAG, "sailId:"+ this.sailId);
        //Log.d(TAG, "sailname:"+ this.sailName);
        //Log.d(TAG, "sail:"+ this.sail[0]);





    }

    public JSONArray getTwa() {
        return twa;
    }

    public JSONArray getTws() {
        return tws;
    }

    public JSONArray getSpeedSail(int sailId) {
        return this.speed[sailId-1];
    }

}
