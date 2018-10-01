package com.utec.asistencia.epro1_android;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.utec.asistencia.epro1_android.api.RetrofitClient;
import com.utec.asistencia.epro1_android.model.Asistencia;

import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Confirmacion extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);


       final TextView tvAsignatura = findViewById(R.id.tv_asignatura);
        final TextView tvSeccion = findViewById(R.id.tv_seccion);
        final TextView tvAula = findViewById(R.id.tv_aula);
        final TextView tvFecha = findViewById(R.id.tv_fecha);
        final TextView tvCarne = findViewById(R.id.tv_carne);


        final TextView tvConfirmacion = findViewById(R.id.tv_confirmacion);
        final ImageView ivFeedback = findViewById(R.id.iv_image);

        Intent i = getIntent();

        String stringFromQR = i.getStringExtra("asignatura");

        String[] datos = stringFromQR.split(",");



        if (datos.length == 3){

            final String asignatura = datos[0];
            final  String seccion = datos[1];
            final  String aula = datos[2];
            final  String carne = "2501262012";

            Asistencia asistencia = new Asistencia(carne, asignatura, seccion, aula);


            Call<Asistencia> call = RetrofitClient.getInstance()
                    .getApi().addAsistencia(asistencia);

            Log.i("Confirmation.java", "asignatura: " + asignatura
                    + ", seccion: " + seccion + ", aula: " + aula + ", carne: " + carne);

            Log.i("Confirmation.java", "call.isExecuted(): " + call.isExecuted());
            Log.i("Confirmation.java", "call.isCanceled(): " + call.isCanceled());
            Log.i("Confirmation.java", "call.request(): " + call.request());

            call.enqueue(new Callback<Asistencia>() {
                @Override
                public void onResponse(Call<Asistencia> call, Response<Asistencia> response) {

                    if (response.code() == 201){

                        Asistencia a = response.body();


                        tvAsignatura.setText(a.getAsignatura());
                        tvSeccion.setText(a.getSeccion());
                        tvCarne.setText(a.getCarne());
                        tvAula.setText(a.getAula());
                        tvFecha.setText(a.getFechaHora().toString());

                    }else{

                        ivFeedback.setImageResource(R.drawable.ic_error);

                        tvConfirmacion.setText(R.string.confirmacion_error);

                        tvAsignatura.setText(asignatura);
                        tvSeccion.setText(seccion);
                        tvCarne.setText(carne);
                        tvAula.setText(aula);
                        tvFecha.setText(new Timestamp(System.currentTimeMillis()).toString());
                    }

                }

                @Override
                public void onFailure(Call<Asistencia> call, Throwable t) {

                    tvAsignatura.setText(call.toString());
                    Toast.makeText(getApplicationContext(), "Ocurrio un error de conexion", Toast.LENGTH_LONG).show();
                }
            });




        }

    }
}
