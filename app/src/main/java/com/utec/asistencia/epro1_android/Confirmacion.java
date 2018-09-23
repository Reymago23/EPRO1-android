package com.utec.asistencia.epro1_android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Confirmacion extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion);


        TextView tvAsignatura = findViewById(R.id.tv_asignatura);

        Intent i = getIntent();

        String asignatura = i.getStringExtra("asignatura");

        tvAsignatura.setText("Asistencia registrada para : " + asignatura);
    }
}
