package com.example.practicaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.practicaapi.Interface.JSONBank;
import com.example.practicaapi.Modelo.Listabancos;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPI extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        //txtJson = findViewById(R.id.txt_Listbank);
        getBancos();
    }
    private void getBancos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api-uat.kushkipagos.com/transfer-subscriptions/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JSONBank JsonBack = retrofit.create(JSONBank.class);
        Call<List<Listabancos>> call = JsonBack.getBancos("37439b29e2d143a7ad3ebcaef946af9f");
        call.enqueue(new Callback<List<Listabancos>>() {
            @Override
            public void onResponse(Call<List<Listabancos>> call, Response<List<Listabancos>> response) {
                TextView txtJson= (TextView) findViewById(R.id.mtxt_Listabank);
                String lstBancos = "";
                if (!response.isSuccessful()) {
                    txtJson.setText("Codigo: " + response.code());
                }
                List<Listabancos> listaBank = response.body();
                for (Listabancos bancos : listaBank) {

                    lstBancos = lstBancos + "\n" +bancos.getName();
                }
                txtJson.append(lstBancos);
            }

            @Override
            public void onFailure(Call<List<Listabancos>> call, Throwable t) {
                //txtJson.setText(t.getMessage());
            }
        });
    }

}