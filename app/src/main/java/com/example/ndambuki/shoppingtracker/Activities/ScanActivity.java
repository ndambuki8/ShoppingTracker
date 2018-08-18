package com.example.ndambuki.shoppingtracker.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ndambuki.shoppingtracker.Database.DatabaseHelper;
import com.example.ndambuki.shoppingtracker.R;
import com.example.ndambuki.shoppingtracker.Utils.Methods;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.IOException;
import java.io.Serializable;

public class ScanActivity extends AppCompatActivity implements Serializable{

    Button viewList, OK, rescan;
    DatabaseHelper myDb;
    SurfaceView cameraView;
    public TextView textView, tvAmount;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
    static int totalAmount;
    static int x;
    Methods myMethods;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        myMethods = new Methods(this);
        viewList = (Button) findViewById(R.id.btnViewScanList);
        tvAmount = (TextView) findViewById(R.id.tvAmountRemaining);
        tvAmount.setText("Amount Remaining : " + myMethods.getAmount());
        viewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScannedListActivity();

            }
        });

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        OK = (Button) findViewById(R.id.btnOK);
        rescan = (Button) findViewById(R.id.btnRescan);


        textView = (TextView) findViewById(R.id.text_view);

        myDb = new DatabaseHelper(this);

                   /* OK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!textView.getText().toString().trim().isEmpty()){
                                long result = myDb.insertScannedItem(textView.getText().toString().trim());
                                long i;
                                if (result != -1){
                                    Toast.makeText(ScanActivity.this, "Scanned Successfully", Toast.LENGTH_SHORT).show();
                                    //myMethods.getAmount()=- String.valueOf(result);

                                }else {
                                    Toast.makeText(ScanActivity.this, "Value Not Saved", Toast.LENGTH_SHORT).show();
                                }

                                StringBuilder str = new StringBuilder();

                                if (str>= 0){
                                    i = Long.valueOf(myMethods.getAmount());
                                    i = i - result;
                                    String s = String.valueOf(i);
                                    tvAmount.setText("Amount Remaining : "+ s);
                                } else {
                                    Toast.makeText(ScanActivity.this, "You Have No More Cash to Spend!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });*/

        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        //TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();

        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");

        } else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {
                    try {

                        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ScanActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                        }

                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                    cameraSource.stop();

                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();

                    if (items.size() != 0) {
                        textView.post(new Runnable() {
                            @Override
                            public void run() {

                                StringBuilder stringBuilder = new StringBuilder();

                                for (int i = 0; i < items.size(); ++i) {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                    // stringBuilder.toString().replaceAll("[^0-9]+", "");
                                }

                                textView.setText(stringBuilder.toString().replaceAll("[^0-9]+", ""));
                            }
                        });
                    }


                    OK.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           //if (!textView.getText().toString().trim().isEmpty()) {
                            if ((!textView.getText().toString().trim().isEmpty()) && (totalAmount >= Integer.parseInt(textView.getText().toString())))
                            {
                                final long result = myDb.insertScannedItem(textView.getText().toString().trim());
                                /*if (!TextUtils.isEmpty(textView.getText().toString().trim())){
                                    myDb.insertScannedItem(textView.getText().toString().trim());
                                }*/
                                //populateListView();

                                if (result != -1) {
                                    Toast.makeText(ScanActivity.this, "Scanned Successfully", Toast.LENGTH_SHORT).show();
                                    //myMethods.getAmount()=- String.valueOf(result);

                                } else {
                                    Toast.makeText(ScanActivity.this, "Value Not Saved", Toast.LENGTH_SHORT).show();
                                }

                                if (items.size() != 0) {
                                    textView.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            StringBuilder stringBuilder = new StringBuilder();
                                            //int x;

                                            for (int i = 0; i < items.size(); ++i) {
                                                TextBlock item = items.valueAt(i);
                                                stringBuilder.append(item.getValue());
                                                stringBuilder.append("\n");
                                                // stringBuilder.toString().replaceAll("[^0-9]+", "");
                                            }


                                            //x = Long.valueOf(stringBuilder.toString().trim());
                                            x = Integer.valueOf(stringBuilder.toString().replaceAll("[^0-9]+", ""));

                                            calculateAmountRemaining(x);


                                        }
                                    });
                                }

                            } else if (textView.getText().toString().trim().isEmpty()){

                                Toast.makeText(ScanActivity.this, "Scan an Item Price First", Toast.LENGTH_SHORT).show();
                            }

                            else {
                                Toast.makeText(ScanActivity.this, "You DO NOT Have Enough Cash!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            });
        }


    }

    private void openScannedListActivity() {
        startActivity(new Intent(this, ScanlistActivity.class));
    }

    public void calculateAmountRemaining(int price) {

        if (totalAmount > 0 && totalAmount >= price) {
            totalAmount = totalAmount - price;
            String s = String.valueOf(totalAmount);
            tvAmount.setText("Amount Remaining : " + s.toString().replaceAll("[^0-9]+", ""));
            System.out.println(totalAmount);
        }/* else {
            Toast.makeText(ScanActivity.this, "You DO NOT Have Enough Cash!", Toast.LENGTH_SHORT).show();
            // tvAmount.setText("Amount Remaining : " + s);
        }*/

    }


}
//android:icon="@mipmap/ic_launcher"
//android:roundIcon="@mipmap/ic_launcher_round"
