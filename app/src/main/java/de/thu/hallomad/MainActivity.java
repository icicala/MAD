package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor lightsensor;
    TextView textView;
    View colorframe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        for (Sensor s : sensorList) {
            Log.i("SensorList", s.toString());
        }

        List<Sensor> lighSensorList = sensorManager.getSensorList(Sensor.TYPE_LIGHT);
        if (lighSensorList.isEmpty()) {
            finish();
            return;
        }
        lightsensor = lighSensorList.get(0);
        textView = findViewById(R.id.text_view);
        colorframe = findViewById(R.id.colorFrame);

    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, lightsensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] value = event.values.clone();
        textView.setText(value[0] + " LUX");
        final int colorLevel = (int) (255 * (1.0 - Math.exp(-value[0] / 500.0)));
        colorframe.setBackgroundColor(Color.argb(255, colorLevel, colorLevel, 0));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }
}