package com.aquino.munidenuncias;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alumno on 13/11/2017.
 */

public interface ApiService {

    String API_BASE_URL = "https://denuncias-api-stefanny09.c9users.io";

    @GET("api/v1/denuncias")
    Call<List<Denuncia>> getDenuncias();


}
