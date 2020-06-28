package com.example.practicaapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.practicaapi.WebService.Asynchtask;
import com.example.practicaapi.WebService.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IngresoLogin extends AppCompatActivity implements Asynchtask {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingreso_login);

        Bundle bundle = this.getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws= new WebService("http://uealecpeterson.net/ws/login.php?usr="
                + bundle.getString("USUARIO") + "&pass=" + bundle.getString("CLAVE"),
                datos, IngresoLogin.this, IngresoLogin.this);
        ws.execute("GET");



    }

    @Override
    public void processFinish(String result) throws JSONException {
        TextView login= (TextView) findViewById(R.id.lb_login);
        login.setText("Respuesta ws: "+ result);

    }
}