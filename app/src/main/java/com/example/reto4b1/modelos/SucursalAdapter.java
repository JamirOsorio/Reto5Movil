package com.example.reto4b1.modelos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reto4b1.MapsActivityShow;
import com.example.reto4b1.R;
import com.example.reto4b1.datos.ApiOracle;

import java.util.ArrayList;

public class SucursalAdapter extends BaseAdapter {
    Context context;
    ArrayList<com.example.reto4b1.modelos.Sucursal> sucursales;
    LayoutInflater inflater;

    public SucursalAdapter(Context context, ArrayList<com.example.reto4b1.modelos.Sucursal> sucursales) {
        this.context = context;
        this.sucursales = sucursales;
    }

    @Override
    public int getCount() {
        return sucursales.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.sucursal_item, null);
        }

        ImageView imageView = convertView.findViewById(R.id.imgItem);
        ImageView imgFavorite = convertView.findViewById(R.id.imgFavorite);
        TextView tvId = convertView.findViewById(R.id.tvIdItem);
        TextView tvName = convertView.findViewById(R.id.tvNameItem);
        TextView tvLocalization = convertView.findViewById(R.id.tvLocalizationItem);

        Sucursal sucursal = sucursales.get(position);

        byte[] image = sucursal.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0 , image.length);

        imageView.setImageBitmap(bitmap);
        tvId.setText(sucursal.getId());
        tvName.setText(sucursal.getName());
        tvLocalization.setText(sucursal.getLocalization());

        if(sucursal.getFavorite()){
            imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiOracle apiOracle = new ApiOracle(context);
                if(sucursal.getFavorite()){
                    Toast.makeText(context, "Eliminado de Favoritos", Toast.LENGTH_SHORT).show();
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }else{
                    Toast.makeText(context, "Agregado a Favoritos", Toast.LENGTH_SHORT).show();
                    imgFavorite.setImageResource(R.drawable.ic_baseline_favorite_24);
                }
                apiOracle.updateFavorite(sucursal.getId(), !sucursal.getFavorite());


            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivityShow.class);
                intent.putExtra("type", "0");
                intent.putExtra("coordenadas", sucursal.getLocalization());
                intent.putExtra("name", sucursal.getName());
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
