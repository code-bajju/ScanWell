package com.example.scanwell;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SensorInfo extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private ProgressBar accelerometerProgressBar;
    private ProgressBar gyroscopeProgressBar;
    private ProgressBar proximityProgressBar;
    private TextView accelerometerTextView;
    private TextView gyroscopeTextView;
    private TextView proximityTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_info);

        // Initialize SensorManager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Initialize ProgressBars
        accelerometerProgressBar = findViewById(R.id.accelerometer_progress_bar);
        gyroscopeProgressBar = findViewById(R.id.gyroscope_progress_bar);
        proximityProgressBar = findViewById(R.id.proximity_progress_bar);

        // Initialize TextViews
        accelerometerTextView = findViewById(R.id.accelerometer_text_view);
        gyroscopeTextView = findViewById(R.id.gyroscope_text_view);
        proximityTextView = findViewById(R.id.proximity_text_view);

        // Check the functionality of sensors
        checkAccelerometerFunctionality();
        checkGyroscopeFunctionality();
        checkProximityFunctionality();
    }

    private void checkAccelerometerFunctionality() {
        Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometerSensor != null) {
            // Accelerometer sensor is available
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Accelerometer sensor is not available
            accelerometerProgressBar.setVisibility(ProgressBar.GONE);
            ((TextView) findViewById(R.id.accelerometer_text_view)).setText("Accelerometer not available on this device.");
        }
    }

    private void checkGyroscopeFunctionality() {
        Sensor gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (gyroscopeSensor != null) {
            // Gyroscope sensor is available
            sensorManager.registerListener(this, gyroscopeSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Gyroscope sensor is not available
            gyroscopeProgressBar.setVisibility(ProgressBar.GONE);
            ((TextView) findViewById(R.id.gyroscope_text_view)).setText("Gyroscope not available on this device.");
        }
    }

    private void checkProximityFunctionality() {
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (proximitySensor != null) {
            // Proximity sensor is available
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            // Proximity sensor is not available
            proximityProgressBar.setVisibility(ProgressBar.GONE);
            ((TextView) findViewById(R.id.proximity_text_view)).setText("Proximity sensor not available on this device.");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Handle sensor events here
        switch (event.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                // Update accelerometer ProgressBar based on accelerometer data
                float maxRange = event.sensor.getMaximumRange();
                float currentValueX = event.values[0];
                float currentValueY = event.values[1];
                float currentValueZ = event.values[2];
                int progressX = (int) ((currentValueX / maxRange) * 100);
                int progressY = (int) ((currentValueY / maxRange) * 100);
                int progressZ = (int) ((currentValueZ / maxRange) * 100);
                accelerometerProgressBar.setProgress(progressX);
                accelerometerTextView.setText("Accelerometer Data:\nX: " + currentValueX + "\nY: " + currentValueY + "\nZ: " + currentValueZ);
                break;
            case Sensor.TYPE_GYROSCOPE:
                // Update gyroscope ProgressBar based on gyroscope data
                float maxGyroRange = event.sensor.getMaximumRange();
                float gyroValueX = event.values[0];
                float gyroValueY = event.values[1];
                float gyroValueZ = event.values[2];
                int gyroProgressX = (int) ((gyroValueX / maxGyroRange) * 100);
                int gyroProgressY = (int) ((gyroValueY / maxGyroRange) * 100);
                int gyroProgressZ = (int) ((gyroValueZ / maxGyroRange) * 100);
                gyroscopeProgressBar.setProgress(gyroProgressX);
                gyroscopeTextView.setText("Gyroscope Data:\nX: " + gyroValueX + "\nY: " + gyroValueY + "\nZ: " + gyroValueZ);
                break;
            case Sensor.TYPE_PROXIMITY:
                // Update proximity ProgressBar based on proximity sensor data
                float maxProximityRange = event.sensor.getMaximumRange();
                float proximityValue = event.values[0];
                int proximityProgress = (int) ((proximityValue / maxProximityRange) * 100);
                proximityProgressBar.setProgress(proximityProgress);
                if (proximityValue < event.sensor.getMaximumRange()) {
                    proximityTextView.setText("Proximity Data: \n Near");
                } else {
                    proximityTextView.setText("Proximity Data:  \n Far");
                }
                break;
            // Add cases for other sensor types as needed
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Unregister sensor listeners to save power when the activity is paused
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Re-register sensor listeners when the activity is resumed
        checkAccelerometerFunctionality();
        checkGyroscopeFunctionality();
        checkProximityFunctionality();
    }
}
