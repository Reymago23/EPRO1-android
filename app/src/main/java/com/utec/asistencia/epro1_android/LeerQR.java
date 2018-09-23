package com.utec.asistencia.epro1_android;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class LeerQR extends AppCompatActivity {


    private CameraSource cameraSource;
    private TextView tvQRText;
    private SurfaceView surfaceView;

    private static final int CAMERA_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_qr);

        surfaceView = findViewById(R.id.sv_camera);
        tvQRText = findViewById(R.id.tv_qr_result);


        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();


        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .build();


        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {


                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    requestCameraPermission();

                } else {

                    try {

                        cameraSource.start(surfaceHolder);

                    } catch (IOException e) {

                        e.printStackTrace();
                    }
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


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(final Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if (qrCodes.size() != 0) {

                    tvQRText.post(new Runnable() {
                        @Override
                        public void run() {

                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

                            assert vibrator != null;
                            vibrator.vibrate(100);


                            String asignatura = qrCodes.valueAt(0).displayValue;

                            cameraSource.stop();
                            cameraSource.release();

                            Intent i = new Intent(getApplicationContext(), Confirmacion.class);

                            i.putExtra("asignatura", asignatura);

                            startActivity(i);
                        }
                    });
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_asistencias:
                startActivity(new Intent(this, Asistencias.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void requestCameraPermission() {

        // COMPLETED: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permiso necesario")
                    .setMessage("Para poder leer el codigo QR se necesita acceso a la camara")
                    .setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            ActivityCompat.requestPermissions(LeerQR.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();

        } else {


            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Permiso concedido, gracias!", Toast.LENGTH_SHORT).show();

                surfaceView.setVisibility(View.GONE);
                surfaceView.setVisibility(View.VISIBLE);
            } else {

                Toast.makeText(this, "Permiso denegado, :(", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
