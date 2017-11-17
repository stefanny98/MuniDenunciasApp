package com.aquino.munidenuncias.services;

import com.aquino.munidenuncias.models.Denuncia;
import com.aquino.munidenuncias.models.Usuario;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Alumno on 13/11/2017.
 */

public interface ApiService {

    String API_BASE_URL = "https://denuncias-api-stefanny09.c9users.io";

    @GET("api/v1/denuncias")
    Call<List<Denuncia>> getDenuncias();

    @FormUrlEncoded
    @POST("/api/v1/login")
    Call<Usuario> login(@Field("username") String username,
                        @Field("password") String password);

    @FormUrlEncoded
    @POST("/api/v1/register")
    Call<Usuario> register(@Field("username") String username,
                           @Field("password") String password,
                           @Field("correo") String correo);

    @GET("api/v1/getUser/{id}")
    Call<Usuario> getUser(@Path("id") Integer id);

    @DELETE("/api/v1/denuncias/{id}")
    Call<ResponseMessage> destroyDenuncia(@Path("id") Integer id);

    @FormUrlEncoded
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenuncia(@Field("titulo") String titulo,
                                         @Field("comentario") String comentario,
                                         @Field("latitud") Double latitud,
                                         @Field("longitud") Double longitud,
                                         @Field("usuarios_id") Integer usuarios_id);
    @Multipart
    @POST("/api/v1/denuncias")
    Call<ResponseMessage> createDenunciaWithImage(
            @Part("titulo") RequestBody titulo,
            @Part("comentario") RequestBody comentario,
            @Part("latitud") RequestBody latitud,
            @Part("longitud") RequestBody longitud,
            @Part("usuarios_id") RequestBody usuarios_id,
            @Part MultipartBody.Part imagen
    );

    @GET("api/v1/denuncias/{id}")
    Call<Denuncia> showDenuncia(@Path("id") Integer id);


}
