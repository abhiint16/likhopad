package com.example.abhishek.likhopadd;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

/**
 * Created by abhishek on 10-08-2016.
 */
public class Name_Status extends AppCompatActivity implements View.OnClickListener {

    EditText name_change;
    RelativeLayout name_status_edit_field;
    String name;
    Button cancel_name_change , ok_name_change;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_status);
         DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.8),(int)(height*.3));
        name_change=(EditText)findViewById(R.id.change_name);
        name_status_edit_field=(RelativeLayout)findViewById(R.id.name_status_edit_field);

        cancel_name_change=(Button)findViewById(R.id.cancel_change_name);
        ok_name_change=(Button)findViewById(R.id.ok_change_name);
        ok_name_change.setOnClickListener(this);

       name_status_edit_field.clearFocus();
        name_change.setFocusable(true);
        //name_change.update();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.ok_change_name)

        {
            name = name_change.getText().toString();
            System.out.println(name);
            Log.d("entered", "entred");
            //MainActivity obj=new MainActivity();
            Intent intent=new Intent(Name_Status.this , MainActivity.class);
            intent.putExtra("namekey",name);
            startActivity(intent);
            Log.d("obj created", "obj created");
            //obj.changeMainName(name);
            Log.d("obj.changename", "obj.changename");
//                   Intent intent=new Intent();
//                   Log.d("intenthsjjsjs","intent hxjjx");
//                   intent.setClass(Name_Status.this , MainActivity.class);
//                   startActivity(intent);
//               }
        }
    }
}
