package com.example.scanwell;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

public class MobileInfo extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_info);
        setDeviceModelInfo();
        setEMIInfo();
        setBuildNumberInfo();
        setAndroidVersionInfo();
        setSecurityUpdateInfo();
        setManufactureNumberInfo();
    }

    private void setDeviceModelInfo() {
        TextView deviceModelViewText = findViewById(R.id.deviceModelViewText);
        deviceModelViewText.setText("Model No: " + Build.MODEL);
    }

    private void setEMIInfo() {
        TextView emiTextView = findViewById(R.id.emiTextView);
        emiTextView.setText("Device Manufacturer: "+ Build.MANUFACTURER);
    }

    private void setBuildNumberInfo() {
        TextView buildNumberTextView = findViewById(R.id.buildNumberTextView);
        buildNumberTextView.setText("Build Number: " + Build.DISPLAY);
    }

    private void setAndroidVersionInfo() {
        TextView androidVersionTextView = findViewById(R.id.androidVersionTextView);
        androidVersionTextView.setText("Android Version: " + Build.VERSION.RELEASE);
    }

    private void setSecurityUpdateInfo() {
        TextView securityUpdateTextView = findViewById(R.id.securityUpdateTextView);
        securityUpdateTextView.setText("Serial Number: "+ Build.TIME);
    }

    private void setManufactureNumberInfo() {
        TextView manufactureNumber = findViewById(R.id.manufactureNumber);
        manufactureNumber.setText("CPU: " + Build.CPU_ABI);
    }

}
