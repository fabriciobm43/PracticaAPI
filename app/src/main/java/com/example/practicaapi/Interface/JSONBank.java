package com.example.practicaapi.Interface;

import com.example.practicaapi.Modelo.Listabancos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface JSONBank {
    @GET("bankList")
    Call<List<Listabancos>> getBancos(@Header("Public-Merchant-Id") String id);
}
