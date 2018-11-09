package com.utec.asistencia.epro1_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.utec.asistencia.epro1_android.api.RetrofitClient;
import com.utec.asistencia.epro1_android.model.Asistencia;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Confirmacion extends AppCompatActivity {

    private static String SP_LOGIN = "sp_login";
    private static String SP_CONFIRMATION = "sp_confirmation";

    private TextView tvAsignatura;
    private TextView tvSeccion;
    private TextView tvAula;
    private TextView tvFecha;
    private TextView tvCarne;
    private TextView tvCiclo;
    private TextView tvConfirmacion;
    private ImageView ivImage;
    private LinearLayout lyCInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);

        initViews();

        Intent i = getIntent();

        int hayClases = i.getIntExtra("hayClases", 2);


        Toast.makeText(this, "Hay clases: " + hayClases, Toast.LENGTH_LONG).show();

    }





    private void initViews(){

        tvAsignatura = findViewById(R.id.tv_asignatura);
        tvSeccion = findViewById(R.id.tv_seccion);
        tvAula = findViewById(R.id.tv_aula);
        tvFecha = findViewById(R.id.tv_fecha);
        tvCarne = findViewById(R.id.tv_carne);
        tvCiclo = findViewById(R.id.tv_ciclo);

        tvConfirmacion = findViewById(R.id.tv_confirmacion);
        ivImage = findViewById(R.id.iv_image);
        lyCInfo = findViewById(R.id.ly_c_info);
    }

}
