package com.example.scanwell;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class NetworkInfo extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 101;
    private TextView textViewNetworkType, textViewNetworkStrength, textViewSIMName;
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network_info);

        textViewNetworkType = findViewById(R.id.textViewNetworkType);
        textViewNetworkStrength = findViewById(R.id.textViewNetworkStrength);
        textViewSIMName = findViewById(R.id.textViewSIMName);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        // Request permission to access phone state if not granted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, retrieve and display network information
            displayNetworkInfo();
        }
    }


    @SuppressLint("SetTextI18n")
    private void displayNetworkInfo() {
        // Get network type (LTE, VoLTE, GSM, etc.)
        String networkType = getNetworkType();
        textViewNetworkType.setText("Network Type: " + networkType);

        // Get network signal strength
        int signalStrength = getSignalStrength();
        textViewNetworkStrength.setText("Network Strength: " + signalStrength);

        // Get SIM name
        String simName = getSimName();
        textViewSIMName.setText("SIM Name: " + simName);

    }

    private String getSimStatus() {
        int simState1 = 0; // First SIM slot
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            simState1 = telephonyManager.getSimState(0);
        }
        int simState2 = 0; // Second SIM slot
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            simState2 = telephonyManager.getSimState(1);
        }

        String simStatus1 = getSimStateString(simState1);
        String simStatus2 = getSimStateString(simState2);

        return "SIM 1: " + simStatus1 + ", SIM 2: " + simStatus2;
    }

    private String getSimStateString(int simState) {
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                return "Absent";
            case TelephonyManager.SIM_STATE_UNKNOWN:
                return "Unknown";
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                return "PIN Required";
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                return "PUK Required";
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                return "Network Locked";
            case TelephonyManager.SIM_STATE_READY:
                return "Ready";
            default:
                return "Unknown";
        }
    }


    private String getNetworkType() {
        int networkType = telephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return "Unknown";
            // Add more cases for other network types if needed
            default:
                return "Other";
        }
    }

    private int getSignalStrength() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return telephonyManager.getSignalStrength().getCdmaDbm();
        }
        return 0;
    }

    private String getSimName() {
        // Get SIM operator name
        return telephonyManager.getSimOperatorName();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, retrieve and display network information
                displayNetworkInfo();
            } else {
                // Permission denied, handle accordingly
            }
        }
    }
}
