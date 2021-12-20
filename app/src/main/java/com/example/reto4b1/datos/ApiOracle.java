package com.example.reto4b1.datos;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reto4b1.casos_uso.CasoUsoSucursal;
import com.example.reto4b1.modelos.Sucursal;
import com.example.reto4b1.modelos.SucursalAdapter;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ApiOracle {
    private RequestQueue queue;
    private CasoUsoSucursal casoUsoSucursal;
    private String url ="https://g3068f273c4fd46-bdreto5.adb.sa-santiago-1.oraclecloudapps.com/ords/admin/sucursales/sucursales";
    private Context context;

    public ApiOracle(Context context) {
        this.context = context;
        queue = Volley.newRequestQueue(context);
        casoUsoSucursal = new CasoUsoSucursal();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void insertSucursal(String name, String location, ImageView imageView) {
        String image = casoUsoSucursal.imageViewToString(imageView);

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("location", location);
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
        JsonObjectRequest postSucursal = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(postSucursal);
    }
    // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
    public void getSucursalById(String id, EditText name, EditText location, ImageView imageView){
        JsonObjectRequest getSucursalById = new JsonObjectRequest(Request.Method.GET, url+"/"+id, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        name.setText(jsonObject.getString("name"));
                        location.setText(jsonObject.getString("location"));
                        byte[] img = casoUsoSucursal.stringToByte(jsonObject.getString("image"));
                        imageView.setImageBitmap(casoUsoSucursal.byteToBitmap(img));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(getSucursalById);
    }

    public void getSucursales(EditText name, EditText location, ImageView imageView){
        // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
        JsonObjectRequest getAllSucursales = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        new Handler().postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {
                                try {
                                    name.setText(jsonObject.getString("name"));
                                    location.setText(jsonObject.getString("location"));
                                    byte[] img = casoUsoSucursal.stringToByte(jsonObject.getString("image"));
                                    imageView.setImageBitmap(casoUsoSucursal.byteToBitmap(img));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, (i+2)*1000);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(getAllSucursales);
    }

    public void deleteSucursal(String id){
        JsonObjectRequest deleteSucursal = new JsonObjectRequest(Request.Method.DELETE, url + "/" + id, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(deleteSucursal);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void updateSucursal(String id, String name, String location, ImageView imageView) {
        String image = casoUsoSucursal.imageViewToString(imageView);

        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("name", name);
            json.put("location", location);
            json.put("image", image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
        JsonObjectRequest putSucursal = new JsonObjectRequest(Request.Method.PUT, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(putSucursal);
    }

    public void getAllSucursalFragment(GridView gridView, ProgressBar progressBar){
        // PETICION(POST,DELETE,PUT,GET), URL, JSON, RESPONSE.LISTENER, ERROR.LISTENER
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Sucursal> lista = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for(int i=0;i<jsonArray.length();i++){
                     JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Sucursal sucursal = new Sucursal(
                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("location"),
                                casoUsoSucursal.stringToByte(jsonObject.getString("image")),
                                jsonObject.getString("is_favorite")
                        );
                        lista.add(sucursal);
                    }
                    SucursalAdapter sucursalAdapter = new SucursalAdapter(context, lista);
                    progressBar.setVisibility(ProgressBar.INVISIBLE);

                    gridView.setAdapter(sucursalAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void getAllSucursalMaps(GoogleMap googleMap){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("items");
                    for (int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Sucursal sucursal = new Sucursal(
                                jsonObject.getString("id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("location"),
                                casoUsoSucursal.stringToByte(jsonObject.getString("image")),
                                jsonObject.getString("is_favorite")
                        );

                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(sucursal.stringToLatLong());
                        markerOptions.title(sucursal.getName());
                        googleMap.addMarker(markerOptions);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

    public void updateFavorite(String id, Boolean favorite){
        JSONObject json = new JSONObject();
        int send = 0;
        if(favorite){
            send = 1;
        }
        try {
            json.put("is_favorite", send);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url+"/"+id, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);
    }

}
