package com.example.servicos.api;

import com.example.servicos.model.Logradouro;
import com.example.servicos.model.Cnpj;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InvertextoApi {
    @GET("/v1/cep/{numero}")
    Call<Logradouro> getLogradouro(
            @Path("numero") String numero,
            @Query("token") String token
    );

    @GET("/v1/cnpj/{numerocnpj}")
    Call<Cnpj> getCnpj(
            @Path("numerocnpj") String numerocnpj,
            @Query("token") String token
    );
}
