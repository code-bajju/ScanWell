package com.example.scanwell;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    private Button btnMobile ,btnNetwork , btnDisplay , btnBattery , btnSensor ,btnCamera; // Add more buttons as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        // Reference buttons
        btnMobile = findViewById(R.id.btnMobile);
        btnNetwork = findViewById(R.id.btnNetwork);
        btnDisplay = findViewById(R.id.btnDisplay);
        btnCamera = findViewById(R.id.btnCamera);
        btnSensor = findViewById(R.id.btnSensor);
        btnBattery = findViewById(R.id.btnBattery);

        btnMobile.setOnClickListener(this);
        btnNetwork.setOnClickListener(this);
        btnBattery.setOnClickListener(this);
        btnDisplay.setOnClickListener(this);
        btnSensor.setOnClickListener(this);
        btnCamera.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnMobile) {
            Intent mobileIntent = new Intent(this, MobileInfo.class);
            startActivity(mobileIntent);
        } else if (view.getId() == R.id.btnNetwork) {
            Intent anotherIntent = new Intent(this, NetworkInfo.class);
            startActivity(anotherIntent);
        }else if (view.getId() == R.id.btnDisplay) {
            Intent anotherIntent = new Intent(this, DisplayInfo.class);
            startActivity(anotherIntent);
        }else if (view.getId() == R.id.btnCamera) {
            Intent anotherIntent = new Intent(this, CameraInfo.class);
            startActivity(anotherIntent);
        }else if (view.getId() == R.id.btnSensor) {
            Intent anotherIntent = new Intent(this, SensorInfo.class);
            startActivity(anotherIntent);
        }else if (view.getId() == R.id.btnBattery) {
            Intent anotherIntent = new Intent(this, BatteryInfo.class);
            startActivity(anotherIntent);
        }
    }
}
