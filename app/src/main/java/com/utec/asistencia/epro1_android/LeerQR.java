package com.utec.asistencia.epro1_android;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
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
import com.utec.asistencia.epro1_android.model.Asignatura;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LeerQR extends AppCompatActivity {

    private CameraSource cameraSource;
    private TextView tvQRText;
    private SurfaceView surfaceView;
    private static final int CAMERA_PERMISSION_CODE = 1;
    private static boolean errorQR = false;
    private static final String SP_LOGIN = "sp_login";
    private static final String SP_CONFIRMATION = "sp_confirmation";
    private String asignatura = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        errorQR = false;

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
            public void receiveDetections(final Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> qrCodes = detections.getDetectedItems();

                if (qrCodes.size() != 0) {

                    tvQRText.post(new Runnable() {
                        @Override
                        public void run() {
                            Vibrator vibrator = (Vibrator) getApplicationContext()
                                                        .getSystemService(Context.VIBRATOR_SERVICE);

                            if (vibrator != null){  vibrator.vibrate(100); }

                            String asignaturas = qrCodes.valueAt(0).displayValue;
                            int hayClases = 0;
                            Intent i = new Intent(getApplicationContext(), Confirmacion.class);

                            if (isDayOfClass(asignaturas)) {

                                hayClases = 1;
                                i.putExtra("asignatura", asignatura);
                            }

                            if(cameraSource != null){

                                cameraSource.stop();
                                cameraSource.release();
                            }

                            if (errorQR){
                                hayClases = -1;
                                Log.v("LeerQR.java", "errorQR = " + errorQR);
                                Log.v("LeerQR.java", "errorQR = TRUE");
                            }

                         //   Toast.makeText(LeerQR.this, "hayClases: " + hayClases, Toast.LENGTH_SHORT).show();
                            i.putExtra("hayClases", hayClases);


                            startActivity(i);
                        }
                    });
                }
            }

            @Override
            public void release() {

            }
        });
    }


    private boolean isDayOfClass(String asignaturas){

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        int today = calendar.get(Calendar.DAY_OF_WEEK);

        String[] all = asignaturas.split("\\+");

        for (String asig : all) {

            String[] one = asig.split(",");

            int[] daysIntArr;

            try {
                daysIntArr = getDaysNumArr(one[2].split("\\-"));
                errorQR = false;
            }catch (Exception e){
                Log.v("LeerQR.java", e.getMessage());
                Log.v("LeerQR.java", "errorQR = TRUE");

                errorQR = true;
                return false;
            }

            for (int dayInArr : daysIntArr) {

                if (today == dayInArr) {
                    asignatura = asig;
                    return true;
                }
            }

        }

        return false;
    }

    private int[]  getDaysNumArr(String[] days) {

        int[] daysNumArr = new int[days.length];

        for (int i = 0; i < days.length; i++) {

            daysNumArr[i] = getDayNum(days[i]);
        }

        return daysNumArr;
    }

    public int getDayNum(String day) {

        int dayInt = -1;
        switch (day.toLowerCase()) {
            case "dom":
                dayInt = 1;
                break;
            case "lu":
                dayInt = 2;
                break;
            case "ma":
                dayInt = 3;
                break;
            case "mie":
                dayInt = 4;
                break;
            case "jue":
                dayInt = 5;
                break;
            case "vie":
                dayInt = 6;
                break;
            case "sab":
                dayInt = 7;
                break;
        }

        return dayInt;
    }

    @Override
    protected void onResume() {
        super.onResume();
        errorQR = false;
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

            case R.id.menu_logout:
                SharedPreferences settings = getApplicationContext().getSharedPreferences(SP_LOGIN, Context.MODE_PRIVATE);

                settings.edit().clear().apply();
                settings = getApplicationContext().getSharedPreferences(SP_CONFIRMATION, Context.MODE_PRIVATE);

                settings.edit().clear().apply();

                startActivity(new Intent(this, Login.class));
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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

    private void requestCameraPermission() {

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

}
