package com.example.abhishek.likhopadd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener ,Create_Java.SendToDB {

    ProgressDialog progressDialog;
    ListView listview;
    DrawerLayout drawerLayout;
    ViewPager viewPager;
    MyPagerAdapter myPagerAdapter;

    String name , title, content;
    String[] listview_items;
    // TabLayout mTabLayout;
    CharSequence Title[]={"ABOUT" , "CONVERSATIONS" , "ACTIVITY" ,"ainvai"};
    int noOfTabs=4;
    ImageButton home_btm_person_id , home_btm_create_id;
    Toolbar toolbar;
    private ActionBarDrawerToggle drawerListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.home_top_tool_bar);
        setSupportActionBar(toolbar);

        //mTabLayout=(TabLayout)findViewById(R.id.tablayout);
        home_btm_person_id=(ImageButton)findViewById(R.id.home_btm_person_id);
        home_btm_create_id=(ImageButton)findViewById(R.id.home_btm_create_id);
        viewPager=(ViewPager)findViewById(R.id.pager);

        listview_items=getResources().getStringArray(R.array.drawer);
        listview=(ListView)findViewById(R.id.drawer_listview);
        listview.setAdapter(new ArrayAdapter<>(this , android.R.layout.simple_list_item_1 ,listview_items));
        listview.setOnItemClickListener(this);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        drawerListener = new ActionBarDrawerToggle(this , drawerLayout , toolbar ,R.string.drawer_open , R.string.drawer_close)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                Toast.makeText(MainActivity.this , "drawer Open" , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Toast.makeText(MainActivity.this , "drawer Close" , Toast.LENGTH_LONG).show();
            }
        };
        drawerLayout.setDrawerListener(drawerListener);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        home_btm_person_id.setOnClickListener(this);
        home_btm_create_id.setOnClickListener(this);

        name=getIntent().getExtras().getString("namekey");
        System.out.println(name);
        FragActivity1 obj = (FragActivity1) getSupportFragmentManager().findFragmentById(R.id.person_profile);
        System.out.println(obj);
        obj.changeName(name);
//        myPagerAdapter= new MyPagerAdapter(getSupportFragmentManager());
//        viewPager.setAdapter(myPagerAdapter);

//        mTabLayout.setTabsFromPagerAdapter(myPagerAdapter);
//
//        mTabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        Log.d("menu to be inflateddd" , "menu to be inflated");
        inflater.inflate(R.menu.menu_main, menu);
        Log.d("menu inflateddd" , "menu inflated");
       // ActionBar ab = getSupportActionBar();

        // Enable the Up button
       // ab.setDisplayHomeAsUpEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        // this method is called whenever there is a icon clicked on app bar.
        //then the system sends the menuitem object to indicate which icon or item is clicked
        // then by item.getItemId() the id of that item is taken and task is performed usind the id.
        int id = item.getItemId();
        if(drawerListener.onOptionsItemSelected(item))
            return true;

        //noinspection SimplifiableIfStatement

        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {


        if(v.getId()==R.id.home_btm_person_id)
        {
              myPagerAdapter = myPagerConnection(v.getId());
              viewPager.setAdapter(myPagerAdapter);
            getSupportActionBar().setTitle("Person");
        }

        if(v.getId()==R.id.home_btm_create_id)
        {
//            Intent intent =new Intent(MainActivity.this , CreateJava.class);
//            startActivity(intent);
            myPagerAdapter = myPagerConnection(v.getId());
            viewPager.setAdapter(myPagerAdapter);
            getSupportActionBar().setTitle("Writing Lab");
        }
    }

    private MyPagerAdapter myPagerConnection(int i)
    {
        return new MyPagerAdapter(getSupportFragmentManager() , i);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this , listview_items[position]+" selected" , Toast.LENGTH_LONG).show();
        selectItem(position);
    }

    public void selectItem(int position) {

       // listview.setItemChecked(position,true);
        setTitle(listview_items[position]);
    }

    public void setTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void sendToTable(String title, String content) {
        this.title=title;
        this.content=content;
        SendData sendData = new SendData();
        sendData.execute(this.title,this.content);
    }

    private class SendData extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(new MainActivity());
            progressDialog.setMessage("Please wait......");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            title=params[0];
            content=params[1];



            URL url= null;
            try {
                url = new URL("http://abhishekint.16mb.com/write/write_send.php");
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                //httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(title, "UTF-8")
                        + "&" +

                        URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                //httpURLConnection.connect();
                httpURLConnection.disconnect();
                return "Content successfully added";
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
            Toast.makeText(new MainActivity(),s, Toast.LENGTH_LONG).show();
            Create_Java create_java=(Create_Java)getSupportFragmentManager().findFragmentById(R.id.createxml_layout_id);

            create_java.emptyField();



        }
    }


//    public void changeMainName(String s)
//    {
//        FragActivity1 obj = (FragActivity1) getSupportFragmentManager().findFragmentById(R.id.person_profile);
//        obj.changeName(s);
//    }
}



