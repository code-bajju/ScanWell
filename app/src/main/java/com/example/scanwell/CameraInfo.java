package com.example.scanwell;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class CameraInfo extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 101;
    private Button btnOpenCamera;
    private TextView cpuTextView;
    private TextView ramTextView;
    private TextView internalStorageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_info);

        btnOpenCamera = findViewById(R.id.btn_open_camera);
        btnOpenCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCameraPreview();
            }
        });

        cpuTextView = findViewById(R.id.cpu_text_view);
        ramTextView = findViewById(R.id.ram_text_view);
        internalStorageTextView = findViewById(R.id.internal_storage_text_view);

        // Get CPU information
        String cpuInfo = Build.BOARD + " " + Build.BRAND + " " + Build.DEVICE;
        cpuTextView.setText(cpuInfo);

        // Get RAM information
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long maxMemory = runtime.maxMemory();
        long usedMemory = totalMemory - runtime.freeMemory();
        ramTextView.setText(formatSize(usedMemory) + " / " + formatSize(totalMemory));

        // Get internal storage information
        long totalInternalStorage = getTotalInternalStorageSize();
        long freeInternalStorage = getFreeInternalStorageSize();
        long usedInternalStorage = totalInternalStorage - freeInternalStorage;
        internalStorageTextView.setText(formatSize(usedInternalStorage) + " / " + formatSize(totalInternalStorage));

        // Check camera permission
        checkCameraPermission();
    }

    // Method to check camera permission
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Request camera permission if not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    REQUEST_CAMERA);
        }
    }

    // Method to handle camera permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission granted, open camera
                openCameraPreview();
            } else {
                // Camera permission denied
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to open the CameraPreviewActivity
    private void openCameraPreview() {
        Intent intent = new Intent(this, CameraPreviewActivity.class);
        startActivity(intent);
    }

    // Method to get total internal storage size
    private long getTotalInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;
    }

    // Method to get free internal storage size
    private long getFreeInternalStorageSize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long freeBlocks = stat.getAvailableBlocksLong();
        return freeBlocks * blockSize;
    }

    // Method to format size in human-readable format
    private String formatSize(long size) {
        if (size <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return String.format("%.2f %s", size / Math.pow(1024, digitGroups), units[digitGroups]);
    }
}
