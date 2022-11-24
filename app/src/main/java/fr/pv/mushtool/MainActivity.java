package fr.pv.mushtool;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {


    Button mPolarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPolarButton = findViewById(R.id.Polar_Button);

        mPolarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent polarActivityIntent = new Intent(MainActivity.this, PolarActivity.class);
                startActivity(polarActivityIntent);
            }
        });
    }

}