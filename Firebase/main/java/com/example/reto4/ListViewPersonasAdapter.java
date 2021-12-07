package com.example.reto4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewPersonasAdapter extends BaseAdapter {
   Context mContext;
   ArrayList<Persona> personaDate;
   LayoutInflater mLayoutInflater;
   Persona mPersona;

    public ListViewPersonasAdapter( Context context,  ArrayList<Persona> personaDate) {
        mContext = context;
        this.personaDate = personaDate;
        mLayoutInflater=(LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return personaDate.size();
    }

    @Override
    public Object getItem( int i) {
        return personaDate.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View row=view;
        if(view==null){
            row=mLayoutInflater.inflate(R.layout.lista_personas,
            null,
            true);
        }
        //enlazar vista
        TextView nombre=row.findViewById(R.id.nombres);
        TextView telefono=row.findViewById(R.id.telefono);
        TextView fecha=row.findViewById(R.id.fecharegistro);

        mPersona= personaDate.get(i);
        nombre.setText(mPersona.getNombre());
        telefono.setText(mPersona.getTelefono());
        fecha.setText(mPersona.getFecharegistro());
        return row;
    }
}
