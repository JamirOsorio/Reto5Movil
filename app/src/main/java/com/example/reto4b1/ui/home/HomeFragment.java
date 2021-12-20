package com.example.reto4b1.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.reto4b1.R;
import com.example.reto4b1.databinding.FragmentHomeBinding;
import com.example.reto4b1.datos.ApiOracle;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    private TextView text_home;
    private final int REQUEST_CODE_GALLERY = 999;

    private Button btnConsultar, btnEliminar, btnActualizar;
    private EditText name, location, id;
    private ImageView imgSelectedOracle;

    private FragmentHomeBinding binding;
    private String url="";

    private ApiOracle api;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        api = new ApiOracle(getContext());

        text_home = (TextView) root.findViewById(R.id.text_home);

        btnEliminar = (Button) root.findViewById(R.id.btnEliminarOracle);
        btnConsultar = (Button) root.findViewById(R.id.btnConsultarOracle);
        btnActualizar = (Button) root.findViewById(R.id.btnActualizarOracle);

        id = (EditText) root.findViewById(R.id.editIdOracle);
        name = (EditText) root.findViewById(R.id.editNameOracle);
        location = (EditText) root.findViewById(R.id.editLocationOracle);

        imgSelectedOracle = (ImageView) root.findViewById(R.id.imgSelectedOracle);



        RequestQueue queue = Volley.newRequestQueue(getContext());
        url ="https://g4c5a7eb22d6797-androidreto5.adb.us-ashburn-1.oraclecloudapps.com/ords/admin/servicios/servicios";

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTest = id.getText().toString().trim();
                if(idTest.equals("")){
                    api.getSucursales(name, location, imgSelectedOracle);
                }else {
                    api.getSucursalById(idTest, name, location, imgSelectedOracle);
                }
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idTest = id.getText().toString().trim();
                api.deleteSucursal(idTest);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String idUpdate = id.getText().toString();
                String nameUpdate = name.getText().toString();
                String locationUpdate = location.getText().toString();
                api.updateSucursal(idUpdate, nameUpdate, locationUpdate, imgSelectedOracle);
            }
        });

//        btnInsertar.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onClick(View v) {
//
//                JSONObject json = new JSONObject();
//                CasoUsoSucursal casoUsoSucursal = new CasoUsoSucursal();
//                byte[] image = casoUsoSucursal.imageViewToByte(imgSelectedOracle);
//
////                Bitmap thumbnail = casoUsoSucursal.byteToBitmap(image);
////
////                thumbnail=casoUsoSucursal.getResizedBitmap(thumbnail, 400);
////                imgSelectedOracle.setImageBitmap(thumbnail);
//                String imgToSend = casoUsoSucursal.imageViewToString(imgSelectedOracle);
//
//                text_home.setText(imgToSend);
//                Toast.makeText(getContext(), "Tam ->"+ imgToSend.length(), Toast.LENGTH_SHORT).show();
//
//                try {
//                    json.put("name", name.getText().toString());
//                    json.put("description", description.getText().toString());
//                    json.put("price", price.getText().toString());
//                    json.put("image", imgToSend);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                JsonObjectRequest postService = new JsonObjectRequest(Request.Method.POST, url, json,
//                        new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//
//                            }
//                        });
//                queue.add(postService);
//            }
//        });





        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        url ="https://g9df035d5e9fa0c-androidreto5.adb.sa-saopaulo-1.oraclecloudapps.com/ords/admin/servicios/servicios";



        switch (item.getItemId()){
            case R.id.action_add:
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    text_home.setText(response.getString("items"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                text_home.setText("Response: " + error.toString());
                            }
                        });
                queue.add(jsonObjectRequest);
                return true;

            case R.id.action_show:

                JSONObject json = new JSONObject();




                try {
                    json.put("name","pepe4");
                    json.put("price", "pepePrice4");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

                Volley.newRequestQueue(getContext()).add(jsonRequest);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}