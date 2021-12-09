package com.example.fireejercicio;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewPersonasAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Persona> mPersonas;
    LayoutInflater mInflater;
    Persona mPersona;

    public ListViewPersonasAdapter( Context context,  ArrayList<Persona> personas) {
        mContext = context;
        mPersonas = personas;
        mInflater=(LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return mPersonas.size();
    }

    @Override
    public Object getItem(int i) {
        return mPersonas.get(i);
    }

    @Override
    public long getItemId( int i) {
        return 0;
    }

    @Override
    public View getView( int i,  View view, ViewGroup viewGroup) {
        View fila=view;
        if(view==null) {
            fila = mInflater.inflate(
                    R.layout.lista_personas,
                    null,
                    true
            );
        }
            TextView nombre=fila.findViewById(R.id.nombres);
            TextView telefono=fila.findViewById(R.id.telefonos);

            mPersona=mPersonas.get(i);

            nombre.setText(mPersona.getNombre());
            telefono.setText(mPersona.getTelefono());
            return fila;

        }

    }

