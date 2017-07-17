package com.example.abhishek.likhopadd;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by abhishek on 02-08-2016.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {

    //Implementation of PagerAdapter that represents
    // each page as a Fragment that is persistently kept in the fragment manager as long as
    // the user can return to the page.

    // For larger sets of pages, consider FragmentStatePagerAdapter.
    //When using FragmentPagerAdapter the host ViewPager must have a valid ID set.

    //Subclasses only need to implement getItem(int) and getCount() to have a working adapter.
    Context context;
    //    int noOfTabs;
    CharSequence[][] Title={{"ABOUT" , "CONVERSATION" , "ACTIVITY" , "AINVAI"},{"CREATE"}};      //can also be written aas CharSequence Title[]

    int home_btn_click;

    public MyPagerAdapter(FragmentManager fm , int i) {

        super(fm);
//        //this.context = context;        //constructer
//        this.noOfTabs=noOfTabs;
//        this.Title=Title;
        this.home_btn_click=i;
    }


    public int getCount() {

        Log.d("getcount","getcount1");
        if(home_btn_click==R.id.home_btm_person_id)
            return 3;                       //getCount()
        if(home_btn_click==R.id.home_btm_create_id)
            return 1;



        else return 2;

    }


    @Override
    public Fragment getItem(int position) {

        if(home_btn_click== R.id.home_btm_person_id)
        {
            if(position==0)           //viewpager asks the adapter to show the item for position no 0.
            {
                Log.d("frag1","frag1");
                FragActivity2 f2=new FragActivity2();
                return f2;
            }

            if(position==1)
            {
                Log.d("frag2","frag2");
                FragActivity1 f1=new FragActivity1();
                return f1;
            }

            if(position==2)
            {
                Log.d("frag2","frag2");
                FragActivity3 f3=new FragActivity3();
                return f3;
            }
            else
            {
                FragActivity4 f4=new FragActivity4();
                return f4;
            }

        }
        ///////////////////////////////////////////////////////////////////////
        if(home_btn_click==R.id.home_btm_create_id) {
            if (position == 0) {
                Create_Java create_Java = new Create_Java();
                return create_Java;
            }
        }

         return null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        Log.d("title","title");
        if(home_btn_click==R.id.home_btm_person_id)
        {
            if(position==0)
            {
                return Title[0][0];
            }
            if(position==1)
            {
                return Title[0][1];
            }
            if(position==2)
                return Title[0][2];
            else
                return Title[0][3];
        }

        if(home_btn_click==R.id.home_btm_create_id)
        {
            if(position==0)
            {
                return Title[1][0];
            }
        }

         return null;
    }

//    @Override
//    public boolean isViewFromObject(View view, Object object)
//    {                                                                      //isViewFromObject()
//        Log.d("isView","isView");
//        return false;
    //The PagerAdapter method isViewFromObject helps the pager know which view belongs
    // to which key. If you're just returning the view as the key object, you can implement
    // this method trivially as:

    //public boolean isViewFromObject(View view, Object object) {
    //return view == object;}

}


//    public Object instantiateItem(View collection, int position) {
//
//        LayoutInflater inflater = (LayoutInflater)collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
////in case of "view container" as the parameter we write below one liner in place of above statement.
//// layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
//
//        //instantiateItem()
//        int resId = 0;
//        /*switch (position)
//        {
//            case 0  :  resId = showFrag1();
//                break;
//
//            case 1  :  resId = showFrag2();
//                break;
////            case 2:
////                resId = showHotelMap();
////                break;
//        }
//*/
//        View view = inflater.inflate(resId, null);
//        ((ViewPager) collection).addView(view, 0);
//
//        //Adding the view to the container is actually what makes it
//        // appear on-screen. The object returned by instantiateItem is just a key/identifier;
//
//        return view;
//    }

//methods

//    public int showHotelMap() {
//        int resId;
//        resId = R.layout.hotelmap;
//        return resId;
//    }

  /*  public int showFrag1() {

        int resId;
        resId = R.layout.activity_frag;
        return resId;
    }

    public int showFrag2() {
        int resId;
        resId = R.layout.activity_frag2;
        return resId;
    }*/




