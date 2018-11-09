package com.utec.asistencia.epro1_android.api;

import com.utec.asistencia.epro1_android.model.Alumno;
import com.utec.asistencia.epro1_android.model.Asistencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IApi {

    @POST("asistencia")
    Call<Asistencia> addAsistencia(@Body Asistencia asistencia);


    @GET("asistencia/{carne}")
    Call<List<Asistencia>> getAsistenciaByCarne(@Path("carne") String carne);

    @GET("asistencia/{carne}/{asignatura}")
    Call<Integer> validarAsistencia(@Path("carne") String carne, @Path("asignatura") String asignatura);


    @POST("alumno")
    Call<Alumno> getAlumnoByCarneAndPassword(@Query("carne") String carne, @Query("password") String password);


}
