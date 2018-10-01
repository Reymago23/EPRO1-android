package com.utec.asistencia.epro1_android.api;

import com.utec.asistencia.epro1_android.model.Alumno;
import com.utec.asistencia.epro1_android.model.Asistencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IApi {

    @POST("asistencia")
    Call<Asistencia> addAsistencia(@Body Asistencia asistencia);


    @GET("asistencia")
    Call<List<Asistencia>> getAllAsistencias();


    @POST("alumno")
    Call<Alumno> getAlumnoByCarneAndPassword(@Query("carne") String carne, @Query("password") String password);


}
