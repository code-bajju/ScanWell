package com.example.scanwell;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraPreviewActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String TAG = "CameraPreviewActivity";
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Button mCaptureButton;
    private Button mSwitchButton;
    private Button mGalleryButton;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);

        mSurfaceView = findViewById(R.id.surface_view);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);

        mCaptureButton = findViewById(R.id.capture_button);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Camera Preview");
        mCaptureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });

        mSwitchButton = findViewById(R.id.switch_button);
        mSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchCamera();
            }
        });

        mGalleryButton = findViewById(R.id.gallery_button);
        mGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        if (!checkCameraHardware()) {
            Toast.makeText(this, "No camera found on this device", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkCameraHardware() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    private void openCamera(int cameraId) {
        try {
            releaseCamera();
            mCamera = Camera.open(cameraId);
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
                Log.e(TAG, "Error releasing camera: " + e.getMessage());
            }
        }
    }

    private void captureImage() {
        if (mCamera != null) {
            mCamera.takePicture(null, null, null, new Camera.PictureCallback() {
                @Override
                public void onPictureTaken(byte[] data, Camera camera) {
                    // Save the captured image to DCIM/Camera directory
                    File pictureFile = getOutputMediaFile();
                    if (pictureFile != null) {
                        try {
                            FileOutputStream fos = new FileOutputStream(pictureFile);
                            fos.write(data);
                            fos.close();
                            Toast.makeText(CameraPreviewActivity.this, "Image saved: " + pictureFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            Log.e(TAG, "Error saving image: " + e.getMessage());
                        }
                    } else {
                        Toast.makeText(CameraPreviewActivity.this, "Error creating media file, check storage permissions", Toast.LENGTH_SHORT).show();
                    }

                    camera.startPreview(); // Restart the preview
                }
            });
        }
    }

    /** Create a File for saving an image */
    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM), "Camera");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }


    private void switchCamera() {
        mCameraId = (mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        openCamera(mCameraId);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType("image/*");
        startActivity(intent);
    }



    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        openCamera(mCameraId);
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        if (mSurfaceHolder.getSurface() == null || mCamera == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.e(TAG, "Error stopping camera preview: " + e.getMessage());
        }

        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size previewSize = getOptimalPreviewSize(parameters.getSupportedPreviewSizes(), width, height);
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        mCamera.setParameters(parameters);

        try {
            mCamera.setPreviewDisplay(mSurfaceHolder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.e(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        releaseCamera();
    }

    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int width, int height) {
        // Your implementation for finding the optimal preview size...
        return sizes.get(0); // Placeholder, replace with actual implementation
    }
}
