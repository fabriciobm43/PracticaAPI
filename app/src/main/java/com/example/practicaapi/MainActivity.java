package com.example.practicaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.practicaapi.Interface.JSONBank;
import com.example.practicaapi.Modelo.Listabancos;
import com.example.practicaapi.WebService.Asynchtask;
import com.example.practicaapi.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Asynchtask {

    EditText etUsuario, etContra, usuario, clave;
    JSONArray ja;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usuario= (EditText)findViewById(R.id.txt_usuario);
        clave=(EditText) findViewById(R.id.txt_clave);

        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> bancos = new HashMap<String, String>();
        WebService ws= new WebService("https://api-uat.kushkipagos.com/transfer-subscriptions/v1/bankList",
                bancos, MainActivity.this, MainActivity.this);
        ws.execute("GET","Public-Merchant-Id","37439b29e2d143a7ad3ebcaef946af9f");
    }
    public void btEnviar(View view){
        Intent intent = new Intent(MainActivity.this, IngresoLogin.class);
        etUsuario = (EditText)findViewById(R.id.txt_usuario);
        etContra = (EditText)findViewById(R.id.txt_clave);
        Bundle b = new Bundle();
        b.putString("USUARIO", etUsuario.getText().toString());
        b.putString("CLAVE", etContra.getText().toString());
        intent.putExtras(b);
        startActivity(intent);
    }
    public void btnRetrofit(View view){
        Intent intent = new Intent(MainActivity.this, RetrofitAPI.class);
        startActivity(intent);
    }
    public void btnVolley(View view){
        Intent intent = new Intent(MainActivity.this, VolleyAPI.class);
        Login();
        startActivity(intent);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView txtBancos= (TextView) findViewById(R.id.mtxt_Listado);
        String lstBancos = "";
        JSONArray JSONlista = new JSONArray(result);
        for(int i=0; i< JSONlista.length();i++){
            JSONObject banco= JSONlista.getJSONObject(i);
            lstBancos = lstBancos + "\n" + banco.getString("name").toString();
        }
        txtBancos.setText("Respuesta ws lista de bancos: "+lstBancos);
    }

    private void Login() {


        RequestQueue queue = Volley.newRequestQueue(this);
        String URL="http://uealecpeterson.net/ws/login.php?usr="+usuario.getText().toString().trim()+"&pass="+clave.getText().toString().trim();
        StringRequest stringRequest =  new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("APPLOG", response);
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("APPLOG", error.toString());
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

}