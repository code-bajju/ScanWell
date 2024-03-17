package com.example.scanwell;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class BatteryInfo extends AppCompatActivity {

    private TextView batteryStatusTextView;
    private TextView batteryHealthTextView;
    private ProgressBar batteryProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_info);

        batteryStatusTextView = findViewById(R.id.batteryStatusTextView);
        batteryHealthTextView = findViewById(R.id.batteryHealthTextView);
        batteryProgressBar = findViewById(R.id.batteryProgressBar);

        // Register a receiver to listen for battery status changes
        registerBatteryReceiver();
    }

    private void registerBatteryReceiver() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryReceiver, filter);
    }

    private final BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = (level / (float) scale) * 100;

            // Update TextView with battery status
            batteryStatusTextView.setText("Battery Level: " + batteryPct + "%");

            // Update ProgressBar with battery level
            batteryProgressBar.setProgress((int) batteryPct);

            // Retrieve battery health
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN);
            String healthString;
            switch (health) {
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthString = "Good";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthString = "Dead";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthString = "Over Voltage";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthString = "Overheat";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthString = "Unspecified Failure";
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD:
                    healthString = "Cold";
                    break;
                default:
                    healthString = "Unknown";
                    break;
            }
            // Update TextView with battery health
            batteryHealthTextView.setText("Battery Health: " + healthString);
        }
    };
}
