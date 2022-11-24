package fr.pv.mushtool;


import static fr.pv.mushtool.Utils.getFloatArrayListFromJsonArray;
import static fr.pv.mushtool.Utils.getIntArrayFromJsonArray;
import static fr.pv.mushtool.Utils.getIntArrayListFromJsonArray;
import static fr.pv.mushtool.Utils.getJsonFromAssets;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;






public class PolarActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = "Polaractivity";

    //private PolarChartVr polarChartVr;
    //BoatList[] mBoatLists = EmployeeDataUtils.getBoat();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_polar);

        Spinner spinnerBoat = (Spinner) findViewById(R.id.spinner_boat);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.boats, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBoat.setAdapter(adapter);
        spinnerBoat.setOnItemSelectedListener(this);





        initUI();
    }

    /**
     * Init UI
     */
    private void initUI() {

        final PolarChartVr polarChartVr = (PolarChartVr) findViewById(R.id.polar_chart);

        // Number of sections
        polarChartVr.setNbSections(19);
        // Number of circles
        polarChartVr.setNbCircles(5);








        // Set data
        final ArrayList<Float> values = new ArrayList<>();
        values.add(4f);
        values.add(3f);
        values.add(5f);
        values.add(2.3f);
        values.add(4.3f);
        values.add(3.3f);
        values.add(2.4f);
        values.add(1f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);
        values.add(1.2f);



        // Use Bezier curve or classic path
        //polarChart.setUseBezierCurve(false);

        // Set the value when touching the graph
        //polarChart.setCanChangeValue(false);

        // Display the value of the section when touched
        //polarChart.setDisplayTouchValue(true);

        // Define custom Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        // paint.setColor(Color.parseColor("#2196F3"));
        paint.setColor(Color.parseColor("red"));
        //polarChart.setShapePaint(paint);

        // Activate onTouchListener and add valueChanged listener
        /*polarChartVr.setPolarChartListener(new PolarChart.PolarChartListener() {
            @Override
            public void onValueChanged(int section, float value) {
                Log.d("PolarChart", "onValueChanged: " + section + " / " + value);
            }
        });*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString();
        //Toast.makeText(parent.getContext(),selection, Toast.LENGTH_LONG);
        int[] test;
        JSONArray test10;
        ArrayList<Integer> test2;
        ArrayList<Float> test3;
        JSONObject jsonBoat;
        try {
            jsonBoat = new JSONObject(getJsonFromAssets(getApplicationContext(), "mono/imoca60foils.json"));
            Log.d(TAG, "Json frame: " +jsonBoat);

            Boat myBoat = new Boat(jsonBoat);
            //Log.d(TAG, "JsonTWA: " +myBoat.jsonTWA);
            test = getIntArrayFromJsonArray(myBoat.jsonTWA);
            test2 = getIntArrayListFromJsonArray(myBoat.jsonTWA);
            Log.d(TAG, "test2: " +test2);

            test3 = getFloatArrayListFromJsonArray(myBoat.mSail.getSpeedSail(1).getJSONArray(1));
            Log.d(TAG, "test3: " +test3);

            /*for(int i =0; i<test.length;i++){
                Log.d(TAG, "test: " +test[i]);
            }*/

            final PolarChartVr polarChartVr = (PolarChartVr) findViewById(R.id.polar_chart);

            // Set the values of sail speed
            polarChartVr.setJib(test2,test3);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}