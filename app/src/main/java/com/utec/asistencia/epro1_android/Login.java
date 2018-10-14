package com.utec.asistencia.epro1_android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.utec.asistencia.epro1_android.api.RetrofitClient;
import com.utec.asistencia.epro1_android.model.Alumno;
import com.utec.asistencia.epro1_android.model.Asistencia;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    private EditText edtCarne;
    private EditText edtPassword;

    private boolean hasErrors = false;

    private static final String SP_LOGIN = "sp_login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        SharedPreferences prefs = getSharedPreferences(SP_LOGIN, MODE_PRIVATE);

        int isAuth = prefs.getInt("auth", 0);
        
        if (isAuth == 1){

            //Toast.makeText(this, "Is auth", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(Login.this, LeerQR.class);

            startActivity(i);
        }



        edtCarne = findViewById(R.id.edt_carne);
        edtPassword = findViewById(R.id.edt_clave);



        findViewById(R.id.btn_entrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateNotEmpty(edtCarne, edtPassword);

                if (!hasErrors){


                    if (isValidCarne(edtCarne)){

                        Call<Alumno> call = RetrofitClient.getInstance()
                                .getApi().getAlumnoByCarneAndPassword(edtCarne.getText().toString(), edtPassword.getText().toString());


                        call.enqueue(new Callback<Alumno>() {
                            @Override
                            public void onResponse(Call<Alumno> call, Response<Alumno> response) {


                                if (response.code() == 200){

                                    Toast.makeText(Login.this, "Acceso concedido!", Toast.LENGTH_SHORT).show();

                                    SharedPreferences.Editor spLogin = getSharedPreferences(SP_LOGIN, MODE_PRIVATE).edit();

                                    spLogin.putString("carne", edtCarne.getText().toString());

                                    spLogin.putInt("auth", 1);

                                    spLogin.apply();



                                    Intent i = new Intent(Login.this, LeerQR.class);

                                    startActivity(i);
                                }else {

                                    Toast.makeText(Login.this, "Response Code: " + response.code(), Toast.LENGTH_SHORT).show();
                                }
                                
                            }

                            @Override
                            public void onFailure(Call<Alumno> call, Throwable t) {

                                Toast.makeText(Login.this, "ERROR: usuario no existe.", Toast.LENGTH_SHORT).show();

                            }


                        });


                    }else {

                        edtCarne.setError("Debe ser un carne valido sin guiones.");
                        edtCarne.requestFocus();

                    }

                }



            }
        });
    }




    private boolean isValidCarne(EditText edtCarne){


        return edtCarne.getText().toString().trim().length() == 10;
    }



    private void validateNotEmpty(EditText ... editText){

        for (EditText edt : editText) {

            if (edt.getText().toString().trim().isEmpty()){

                edt.setError("Campo requerido");
                edt.requestFocus();

                hasErrors = true;

                return;
            }
        }

        hasErrors = false;

    }


}
