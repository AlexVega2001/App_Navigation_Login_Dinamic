package com.example.app_navigation_login_dinamic.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.app_navigation_login_dinamic.Models.Fragment_Chat;
import com.example.app_navigation_login_dinamic.Models.Fragment_Message;
import com.example.app_navigation_login_dinamic.Models.Fragment_Profile;
import com.example.app_navigation_login_dinamic.R;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    TextView txtvUser;
    CircleImageView imgCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //El bundle recupera la informacion enviada desde el MainActivity
        Bundle bundle = this.getIntent().getExtras();
        int idUser = Integer.parseInt(bundle.getString("idUser"));
        GetOptions(idUser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.idToolbar);
        setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Realizamos la accion del boton de menu en la esquina superior izquierda de nuestra
        //Actividad
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        txtvUser = (TextView) header.findViewById(R.id.txtvUserName);
        imgCircleView = (CircleImageView) header.findViewById(R.id.imgUser);

        //Fragmento que se cargara por defecto
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new Fragment_Message())
                .commit();
        navigationView.setCheckedItem(R.id.nav_profile);

    }

    private void GetOptions(int id) {
        String url = "https://my-json-server.typicode.com/AlexVega2001/jsonServerFake/users?id=" + id;
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest requestArray = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Menu menu = navigationView.getMenu();
                        SubMenu menu1 = menu.addSubMenu("Redes Sociales");
                        try {
                            JSONObject jsonObject = new JSONObject(response.get(0).toString());
                            int id = jsonObject.getInt("id");
                            txtvUser.setText(jsonObject.getString("user"));
                            menu1.add(jsonObject.getString("Option1"));
                            menu1.add(jsonObject.getString("Option2"));

                            if (id == 1){
                                imgCircleView.setImageResource(R.drawable.user1);
                            } else if (id == 2) {
                                imgCircleView.setImageResource(R.drawable.user2);
                            } else{
                                imgCircleView.setImageResource(R.drawable.user3);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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

    //Metodo que permite que al presionar el boton "back", no queremos que nos saque de la actividad
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        boolean fragmentTransaction = false;

        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_message:
                fragment = new Fragment_Message();
                fragmentTransaction = true;
                break;
            case R.id.nav_chat:
                fragment = new Fragment_Chat();
                fragmentTransaction = true;
                break;
            case R.id.nav_profile:
                fragment = new Fragment_Profile();
                fragmentTransaction = true;
                break;
        }

        if (fragmentTransaction) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
        }

        drawerLayout.closeDrawers();
        return true;
    }

}