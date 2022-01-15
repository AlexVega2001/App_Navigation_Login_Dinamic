package com.example.app_navigation_login_dinamic.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.app_navigation_login_dinamic.Models.Users;
import com.example.app_navigation_login_dinamic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Declaro un arreglo para el llenado de los usuarios
    List<Users> lstusers;

    //Declaro los controles para los textos y el botón
    private EditText txtUser, txtPassword;
    private Button btnLogear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar los controles
        txtUser = (EditText) findViewById(R.id.edtUser);
        txtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogear = (Button) findViewById(R.id.btnIniciarSesion);

        //Inicializar el arreglo para el listado de usuarios
        lstusers = new ArrayList<>();

        GetUsers();

        btnLogear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validaUser() >= 0) {
                    Toast.makeText(getApplicationContext(), "Acceso Concedido",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,
                            MainActivity2.class);

                    //Creamos la información a pasar entre actividades - Pares Key-Value
                    Bundle b = new Bundle();
                    b.putString("idUser", Integer.toString(lstusers.get(validaUser())
                                                                       .getIdUser()));

                    //Añadimos la información al intent
                    intent.putExtras(b);

                    // Iniciamos la nueva actividad
                    startActivity(intent);
                    txtUser.setText("");
                    txtPassword.setText("");
                } else if (validaUser() == -1) {
                    Toast.makeText(getApplicationContext(), "Acceso Denegado",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void GetUsers() {
        String url = "https://my-json-server.typicode.com/AlexVega2001/jsonServerFake/users";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest requestArray = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Response(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de Conexión:" +
                                error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        queue.add(requestArray);
    }

    private void Response(JSONArray jArray) {

        try {
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(jArray.get(i).toString());
                Users users = new Users();
                users.setIdUser(jsonObject.getInt("id"));
                users.setUsername(jsonObject.getString("user"));
                users.setPassword(jsonObject.getString("password"));
                users.setOpc1(jsonObject.getString("Option1"));
                users.setOpc2(jsonObject.getString("Option2"));
                lstusers.add(users);
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Error al cargar lista: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private int validaUser() {
        int indice = -1;
        for (int i = 0; i < lstusers.size(); i++) {
            if (lstusers.get(i).getUsername().equals(txtUser.getText().toString()) &&
                    lstusers.get(i).getPassword().equals(txtPassword.getText().toString())) {
                indice = i;
            }
        }
        return indice;
    }
}