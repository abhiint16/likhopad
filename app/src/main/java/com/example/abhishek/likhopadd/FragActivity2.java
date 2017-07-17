package com.example.abhishek.likhopadd;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by abhishek on 02-08-2016.
 */
public class FragActivity2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("fragclass3", "fragclass3");
        //super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.frag2, container, false);
    }
}
