package com.example.facedetect;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.facedetect.listener.FaceDetectionListener;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Camera mCamera;
    boolean mPreviewRunning = false;
    private static SurfaceHolder PreviewHolder;
    private SurfaceView Preview;
    Button cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        Preview = findViewById(R.id.cameraArea);
        PreviewHolder = Preview.getHolder();
        PreviewHolder.addCallback(this);
        PreviewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        cameraButton = findViewById(R.id.detectButton);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.setFaceDetectionListener(new FaceDetectionListener());
                startFaceDetection();
            }
        });
    }

    public void startFaceDetection(){
        // Try starting Face Detection
        Camera.Parameters parameters = mCamera.getParameters();

        // start face detection only *after* preview has started
        if (parameters.getMaxNumDetectedFaces() > 0){
            // camera supports face detection, so can start it:
            mCamera.startFaceDetection();
        }
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        mCamera = Camera.open();
        startFaceDetection();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int w, int h) {
        try {
            if (mPreviewRunning) {
                mCamera.stopPreview();
                mPreviewRunning = false;
            }
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setPreviewSize(w, h);
            mCamera.setParameters(parameters);
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
            startFaceDetection();
        } catch (Exception e) {

        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mPreviewRunning = false;
        mCamera.release();
        mCamera = null;
    }
}