package fr.pv.mushtool;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Boat {
    public static final String TAG = "Boat";
    JSONArray jsonTWA, jsonTWS;
    JSONObject jsonBoat;
    JSONObject jsonBoatPolar;
    Sail mSail;
    String boatName;
    double maxSpeed;

    Boat(JSONObject jsonBoat) throws JSONException {
        this.jsonBoat =jsonBoat;
        this.jsonBoatPolar = jsonBoat.getJSONObject("polar");
        this.boatName = this.jsonBoatPolar.getString("label");
        this.maxSpeed = this.jsonBoatPolar.getDouble("maxSpeed");
        this.jsonTWA =this.jsonBoatPolar.getJSONArray("twa");
        this.jsonTWS =this.jsonBoatPolar.getJSONArray("tws");
        mSail = new Sail(this.jsonTWA,this.jsonTWS, this.jsonBoatPolar.getJSONArray("sail"));
        Log.d(TAG, "Boat type: " +this.boatName);
        Log.d(TAG, "Boat maxspeed: " +this.maxSpeed);
    }



}
