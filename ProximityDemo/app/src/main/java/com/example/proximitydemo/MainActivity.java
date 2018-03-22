package com.example.proximitydemo;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView distanceText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        distanceText = findViewById(R.id.distanceText);
        //get instance of sensor service
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //use manager to get a particular sensor (depends on the phone on the capabilities)
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float distance = sensorEvent.values[0];
            if (sensorEvent.values[0] <= 0.0) {
                //near
                distanceText.setText("near: " + Float.toString(distance) + ", maximum range is: " +  mSensor.getMaximumRange());
            } else {
                //far
                distanceText.setText("far: " + Float.toString(distance) + ", maximum range is: " + mSensor.getMaximumRange());
            }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
