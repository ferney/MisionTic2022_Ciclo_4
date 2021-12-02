package com.example.appbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Search extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup group, Bundle savedInstanceState){
        return inflater.inflate(R.layout.search,group,false);

    }
}
