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
        lyCInfo.setVisibility(View.GONE);

        Intent i = getIntent();
        int hayClases = i.getIntExtra("hayClases", 2);

//        Toast.makeText(Confirmacion.this, "hayClases: " + hayClases, Toast.LENGTH_SHORT).show();

        if (hayClases == 1){

            String asignaturaDetails = i.getStringExtra("asignatura");
            String[] detailsArr = asignaturaDetails.split(",");
            String[] timeArr = detailsArr[3].split("-");
            //Toast.makeText(Confirmacion.this, "timeArr[0]: " + timeArr[0] + " timeArr[1]: " + timeArr[1], Toast.LENGTH_SHORT).show();


            if (isBetween(timeArr[0], timeArr[1])){

                SharedPreferences prefsLogin = getSharedPreferences(SP_LOGIN, MODE_PRIVATE);
                final String asignatura = detailsArr[0];
                final String seccion = detailsArr[1];
                final String aula = detailsArr[4];
                final String ciclo = detailsArr[5];
                final String carne = prefsLogin.getString("carne", "0000000000");

                Call<Integer> call = RetrofitClient.getInstance().getApi().validarAsistencia(carne, asignatura);

                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {

                        if (response.body() != null){

                            if (response.body() == 0){

                                Asistencia asistencia = new Asistencia(carne, asignatura, seccion, aula, ciclo);
                                Call<Asistencia> callAdd = RetrofitClient.getInstance().getApi().addAsistencia(asistencia);

                                callAdd.enqueue(new Callback<Asistencia>() {
                                    @Override
                                    public void onResponse(Call<Asistencia> call, Response<Asistencia> response) {

                                        if (response.code() == 201) {

                                            Asistencia a = response.body();

                                            Locale locale = new Locale("es", "sv");
                                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss aaa", locale);

                                            lyCInfo.setVisibility(View.VISIBLE);
                                            ivImage.setImageResource(R.drawable.ic_check);
                                            tvConfirmacion.setTextColor(getResources().getColor(R.color.success));
                                            tvConfirmacion.setText(R.string.confirmacion_success);

                                            tvAsignatura.setText(a.getAsignatura().toLowerCase());
                                            tvSeccion.setText(a.getSeccion());
                                            tvCarne.setText(a.getCarne());
                                            tvAula.setText(a.getAula());
                                            tvCiclo.setText(a.getCiclo());
                                            tvFecha.setText(sdf.format(a.getFechaHora()));

                                        }else {

                                            ivImage.setImageResource(R.drawable.ic_error);
                                            tvConfirmacion.setTextColor(getResources().getColor(R.color.danger));
                                            tvConfirmacion.setText(R.string.confirmacion_error);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Asistencia> call, Throwable t) {

                                        ivImage.setImageResource(R.drawable.ic_error);
                                        tvConfirmacion.setTextColor(getResources().getColor(R.color.danger));
                                        tvConfirmacion.setText(R.string.confirmacion_error);
                                    }
                                });

                            }else {

                                ivImage.setImageResource(R.drawable.ic_warning);
                                tvConfirmacion.setTextColor(getResources().getColor(R.color.success));
                                tvConfirmacion.setText(R.string.confirmacion_prev_asistencia);
                            }

                        }else{

                            ivImage.setImageResource(R.drawable.ic_error);
                            tvConfirmacion.setTextColor(getResources().getColor(R.color.danger));
                            tvConfirmacion.setText(R.string.confirmacion_error);
                        }

                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {

                        ivImage.setImageResource(R.drawable.ic_error);
                        tvConfirmacion.setTextColor(getResources().getColor(R.color.danger));
                        tvConfirmacion.setText(R.string.confirmacion_error);
                    }
                });

            }else{

                ivImage.setImageResource(R.drawable.ic_warning);
                tvConfirmacion.setTextColor(getResources().getColor(R.color.warning));
                tvConfirmacion.setText(R.string.confirmacion_error_hora);
            }

        }else{

            ivImage.setImageResource(R.drawable.ic_warning);
            tvConfirmacion.setTextColor(getResources().getColor(R.color.warning));
            tvConfirmacion.setText(R.string.confirmacion_error_dia);
        }

    }


    private static boolean isBetween(String pStart, String pEnd) {

        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

        try {

            Date start = parser.parse(pStart);
            Date end = parser.parse(pEnd);
            Date dateNow = new Date();

            String strNow = dateNow.getHours() + ":" + dateNow.getMinutes();

            Date now = parser.parse(strNow);

            if (now.after(start) && now.before(end)) {

                return true;
            }

        } catch (Exception e) { }

        return false;
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
