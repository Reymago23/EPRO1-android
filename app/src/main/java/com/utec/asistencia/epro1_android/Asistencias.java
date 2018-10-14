package com.utec.asistencia.epro1_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.utec.asistencia.epro1_android.api.RetrofitClient;
import com.utec.asistencia.epro1_android.model.Alumno;
import com.utec.asistencia.epro1_android.model.Asistencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Asistencias extends AppCompatActivity {

    private TextView tvAsistencias;

    private static final String SP_LOGIN = "sp_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencias);


        tvAsistencias = findViewById(R.id.tv_asistencias);

        SharedPreferences prefs = getSharedPreferences(SP_LOGIN, MODE_PRIVATE);

        String carne = prefs.getString("carne", "0");

        Call<List<Asistencia>> call = RetrofitClient.getInstance().getApi().getAsistenciaByCarne(carne);


        call.enqueue(new Callback<List<Asistencia>>() {
            @Override
            public void onResponse(Call<List<Asistencia>> call, Response<List<Asistencia>> response) {


                if (response.body() != null){

                    List<Asistencia> asistencias =  response.body();

                    if ( !asistencias.isEmpty() ){

                        RecyclerView rvAsistencias =  findViewById(R.id.rv_asistencias);

                        AsistenciasAdapter adapter = new AsistenciasAdapter(asistencias);

                        rvAsistencias.setAdapter(adapter);

                        rvAsistencias.setLayoutManager(new LinearLayoutManager(Asistencias.this));

                    }

                }else
                {
                    tvAsistencias.setText("No se encontro ninguna asistencia");
                }

            }

            @Override
            public void onFailure(Call<List<Asistencia>> call, Throwable t) {

            }
        });




    }
}
