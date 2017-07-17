package com.example.abhishek.likhopadd;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;

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
 * Created by abhishek on 02-08-2016.
 */
public class Register extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog progressDialog;
    EditText editname, editpassword, editusername, editemail;
    Button btn;
    TextView textlogin;
    String string_name, string_email, string_password, string_username;
    ImageButton facebook_login_btn , google_login_btn ;
    Context mContext = Register.this;
    AccountManager mAccountManager;
    String token;
    int serverCode;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        editname = (EditText) findViewById(R.id.register_etname);
        editemail = (EditText) findViewById(R.id.register_etemail);
        editusername = (EditText) findViewById(R.id.register_etusername);
        editpassword = (EditText) findViewById(R.id.register_etpassword);
        google_login_btn=(ImageButton)findViewById(R.id.google_login_btn);

        btn = (Button) findViewById(R.id.register_btn);
        textlogin = (TextView) findViewById(R.id.register_login);

        btn.setOnClickListener(this);
        textlogin.setOnClickListener(this);
        google_login_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.register_login) {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        }

        if(v.getId() == R.id.google_login_btn)
        {
            syncGoogleAccount();

        }

        if (v.getId() == R.id.register_btn) {
            string_name = editname.getText().toString();
            string_email = editemail.getText().toString();
            string_username = editusername.getText().toString();
            string_password = editpassword.getText().toString();

            //no one can send the blank form
            boolean validation = validform();

            if (validation == false) {
                return;
            }

            //create class to send the data
            SendData sendData = new SendData();
            sendData.execute(string_name, string_email, string_username, string_password);
        }

    }

    private boolean validform() {

        boolean validation = true;

        int counter = 0;
        if ("".equals(string_name)) {
            editname.setError("Please Enter Name");
            counter++;
        }
        if ("".equals(string_email)) {
            editemail.setError("Please Enter Email");
            counter++;
        }
        if ("".equals(string_password)) {
            editpassword.setError("Please Enter Password");
            counter++;
        }
        if ("".equals(string_username)) {
            editusername.setError("Please Enter Username");
            counter++;
        }
        if (counter == 0)
            return true;

        return false;
    }




    private class SendData extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(Register.this);
            progressDialog.setMessage("Please wait......");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            string_name=params[0];
            string_email=params[1];
            string_username=params[2];
            string_password=params[3];


            URL url= null;
            try {
                url = new URL("http://abhishekint.16mb.com/register/send.php");
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(string_name, "UTF-8")
                        + "&" +

                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(string_email, "UTF-8")
                        + "&" +
                        URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(string_username, "UTF-8")
                        + "&" +
                        URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(string_password, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "Registered Successfully...!";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG).show();
            editname.setText("");
            editemail.setText("");
            editusername.setText("");
            editpassword.setText("");

            Intent intent =new Intent(Register.this , MainActivity.class);
            startActivity(intent);
        }
    }


    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager
                .getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    private AbstractGetNameTask getTask(Register activity, String email,
                                        String scope) {
        return new GetNameInForeground(activity, email, scope);

    }

    public void syncGoogleAccount() {
        if (isNetworkAvailable() == true) {
            String[] accountarrs = getAccountNames();
            if (accountarrs.length > 0) {
                //you can set here account for login
                getTask(Register.this, accountarrs[0], SCOPE).execute();
            } else {
                Toast.makeText(Register.this , "No Google Account has been Synchronized!",
                        Toast.LENGTH_SHORT).show();

            }
        } else {
            Toast.makeText(Register.this, "No Network Service!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public boolean isNetworkAvailable() {

        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.e("Network Testing", "***Available***");
            return true;
        }
        Log.e("Network Testing", "***Not Available***");
        return false;
    }
}









