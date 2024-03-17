package com.example.scanwell;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        // Get display metrics
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        // Access display properties
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        float density = displayMetrics.density;
        float dpWidth = screenWidth / density;
        float dpHeight = screenHeight / density;
        int densityDpi = displayMetrics.densityDpi;
        int orientation = getResources().getConfiguration().orientation;

        // Display properties on TextViews
        TextView screenResolutionTextView = findViewById(R.id.screen_resolution);
        screenResolutionTextView.setText("Screen Resolution: " + screenWidth + "x" + screenHeight);

        TextView screenDensityTextView = findViewById(R.id.screen_density);
        screenDensityTextView.setText("Screen Density: " + density + " (" + densityDpi + "dpi)");

        TextView screenSizeTextView = findViewById(R.id.screen_size);
        screenSizeTextView.setText("Screen Size: " + dpWidth + "dp x " + dpHeight + "dp");

        TextView orientationTextView = findViewById(R.id.orientation);
        orientationTextView.setText("Orientation: " + (orientation == 1 ? "Portrait" : "Landscape"));
    }
}
