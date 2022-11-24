package fr.pv.mushtool;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.util.Log;

import org.json.JSONArray;

public class Utils {


    public static final String TAG = "Utils";

    static String getJsonFromAssets(Context context, String fileName ) {

        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            Log.d(TAG, "boatpolarsize: " +size);
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }


    static int[] getIntArrayFromJsonArray(JSONArray jsonArray) throws Exception{
        int[] mIntList = new int[jsonArray.length()];
        for(int i =0; i<jsonArray.length(); i++){
            mIntList[i]= jsonArray.getInt(i);
           // Log.d(TAG, "intArray: " +mIntList[i]);
        }

        return mIntList;

    }

    static ArrayList<Integer> getIntArrayListFromJsonArray(JSONArray jsonArray) throws Exception{
        ArrayList<Integer> mIntList = new ArrayList<Integer>(jsonArray.length());
        for(int i =0; i<jsonArray.length(); i++){
            mIntList.add(jsonArray.getInt(i));

        }

        return mIntList;

    }

    static ArrayList<Float> getFloatArrayListFromJsonArray(JSONArray jsonArray) throws Exception{
        ArrayList<Float> mIntList = new ArrayList<Float>(jsonArray.length());
        for(int i =0; i<jsonArray.length(); i++){
            mIntList.add((float) jsonArray.getDouble(i));

        }

        return mIntList;

    }

    static int getPreviousIndexArrayList(ArrayList<Integer> arrayList, int value) throws Exception{
        int index = 0;

        if (value >= arrayList.get(arrayList.size()-1)){
         index = arrayList.size()-1;
        }
        else{
            while(value>=arrayList.get(index)){
                index++;

            }
            index =index- 1;
        }

        return index;

    }

    static float getInterpol1D(int value, ArrayList<Integer> x, ArrayList<Float> y) throws Exception {
        int index_x1 = getPreviousIndexArrayList(x, value);
        int index_x2;
        if (index_x1 <x.size()-1){
            index_x2 = index_x1+1;
        }else{
            index_x2 = index_x1;
        }
        float result = y.get(index_x1)+(y.get(index_x2)-y.get(index_x1))*((value-x.get(index_x1))/(x.get(index_x2)-x.get(index_x1)));
        return result;
    }
}

