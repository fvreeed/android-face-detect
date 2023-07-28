package com.example.facedetect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_VIDEO_CAPTURE = 22;

    Button cameraButton;
    VideoView cameraArea;
    Camera mCamera;
    SurfaceHolder mHolder;
    //SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraButton = findViewById(R.id.cameraButton);
        cameraArea = findViewById(R.id.cameraArea);
        mHolder = cameraArea.getHolder();
        //surfaceView = findViewById(R.id.surfaceView);
        //surfaceView.setZOrderOnTop(true);
        //mHolder.addCallback((SurfaceHolder.Callback) this);

        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                try {
//                    mCamera.setPreviewDisplay(mHolder);
//                    mCamera.startPreview();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                cameraIntent.putExtra(
//                        "android.data.extras.CAMERA_FACING",
//                        1
//                );
                startActivityForResult(cameraIntent, REQUEST_VIDEO_CAPTURE);
            }
        });

//        FaceDetectorOptions realTimeDetection =
//                new FaceDetectorOptions.Builder()
//                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
//                        .build();
//        detector = FaceDetection.getClient(realTimeDetection);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = data.getData();
            cameraArea.setVideoURI(videoUri);
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    public final void startFaceDetection () {
//
//    }
}