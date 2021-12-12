package de.thu.hallomad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Sensor sensorOrientation;
    private SensorManager sensorManager;
    private ImageView compassImage;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (sensors.size() == 0) {
            finish();
            return;
        }

        sensorOrientation = sensors.get(0);

        compassImage = findViewById(R.id.compass_image);
        textView = findViewById(R.id.textView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorOrientation, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        float[] values = event.values.clone();
//        SensorManager.getOrientation()
        float data = (float) Math.toDegrees(-values[0]);
        textView.setText(String.valueOf(data));

        compassImage.setRotation(data);


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}