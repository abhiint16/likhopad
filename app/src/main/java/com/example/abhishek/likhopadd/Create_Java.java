package com.example.abhishek.likhopadd;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by abhishek on 04-08-2016.
 */
public class Create_Java extends Fragment implements TextWatcher, View.OnClickListener {

    View view;

    FloatingActionButton main , fab1 , fab2 , fab3;
    EditText createxml_content_id , createxml_title_id;
    String title , content;
    AccountManager mAccountManager;
    SendToDB sendToDB;
    int i=0;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.createxml , container ,false);
        createxml_content_id=(EditText)view.findViewById(R.id.createxml_content_id);
        createxml_title_id=(EditText)view.findViewById(R.id.createxml_title_id);
        main=(FloatingActionButton)view.findViewById(R.id.fab);
        fab1=(FloatingActionButton)view.findViewById(R.id.fab_1);
        fab2=(FloatingActionButton)view.findViewById(R.id.fab_2);
        fab3=(FloatingActionButton)view.findViewById(R.id.fab_3);
        main.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);


        createxml_title_id.addTextChangedListener(this);
        createxml_content_id.addTextChangedListener(this);
        return  view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

//        final SharedPreferences prefs = PreferenceManager
//                .getDefaultSharedPreferences(new MainActivity());
//        prefs.edit().putString("autoSave", s.toString()).commit();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

        Log.d("entedsssds","ajjhahjahhjhjs");
        if((v.getId()==R.id.fab)&&(i%2==0))
        {
            System.out.println(i);
            Log.d("eveneven","eveneveneven");
            fab1.setVisibility(view.VISIBLE);
            fab2.setVisibility(view.VISIBLE);
            fab3.setVisibility(view.VISIBLE);
            main.setImageResource(R.drawable.delete2);
            //main

            System.out.println(i);

            System.out.println(i);
        }
        if((v.getId()==R.id.fab)&&(i%2!=0))
        {
            System.out.println(i);
            Log.d("odododoodo","odoododod");
            fab1.setVisibility(view.INVISIBLE);
            fab2.setVisibility(view.INVISIBLE);
            fab3.setVisibility(view.INVISIBLE);
            System.out.println(i);
            main.setImageResource(R.drawable.ic_eject_white_24dp);
            System.out.println(i);
        }

        if(v.getId()==R.id.fab_1)
        {
            Snackbar snackbar=Snackbar.make(view.findViewById(R.id.createxml_layout_id) , "" , Snackbar.LENGTH_LONG);
            snackbar.setAction("PREVIEW" , new Create_Java());
            snackbar.setAction("Cancel" , new Create_Java());
            snackbar.show();
        }
        if(v.getId()==R.id.fab_2)
        {
            Snackbar snackbar=Snackbar.make(view.findViewById(R.id.createxml_layout_id) , "Do you want to save your write-up?" , Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("SAVE" , new Create_Java());
            snackbar.show();
        }
        if(v.getId()==R.id.fab_3)
        {
            final Snackbar snackbar=Snackbar.make(view.findViewById(R.id.createxml_layout_id) , "Do you want to publish your write-up?" , Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("PUBLISH" , new Create_Java()
            {
                @Override
                public void onClick(View v) {

                    title=createxml_title_id.getText().toString();
                    content=createxml_content_id.getText().toString();
                    boolean validation =validform();
                    if(validation==false)
                        return;

                    sendToDB.sendToTable(title, content);



                }

                private boolean validform()

            {
                boolean validation = true;

                int counter = 0;
                if ("".equals(title)) {
                    createxml_title_id.setError("Please Enter Title");
                    counter++;
                }
                if ("".equals(content)) {
                    createxml_content_id.setError("Please Enter Content");
                    counter++;
                }
                if(counter==0)
                    return true;
                return false;

            }
            });
            snackbar.show();
        }

        i++;
    }


    public interface SendToDB
    {
       public void sendToTable(String title , String content);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
        sendToDB=(SendToDB) activity;}
        catch (Exception e){}
    }

    public void emptyField()
    {
        createxml_title_id.setText("");
        createxml_content_id.setText("");
    }
}
