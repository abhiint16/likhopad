package com.example.abhishek.likhopadd;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by abhishek on 02-08-2016.
 */
public class FragActivity1 extends Fragment implements View.OnClickListener {

    View view;
    ImageButton name_change__btn, status_change_btn, profile_change_btn;
    ImageView person_dp;
    TextView status_field , name_field;
    //    TextView name_field, status_field;
//    String userChoosenTask;
//    int REQUEST_CAMERA, SELECT_FILE;
    private  static final int PICK_IMAGE=100;
    Uri imageUri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("fragclass3", "fragclass3");
        //super.onCreateView(inflater, container, savedInstanceState);
        view=inflater.inflate(R.layout.person_profile, container, false);
        Log.d("view set" , "view set");
        profile_change_btn = (ImageButton) view.findViewById(R.id.profile_change_btn);
        name_change__btn=(ImageButton)view.findViewById(R.id.name_change__btn);

        Log.d("btn set" , "btn set");



        status_field = (TextView)view.findViewById(R.id.status_field);
        name_field = (TextView)view.findViewById(R.id.name_field);
        name_change__btn.setOnClickListener(this);
        Log.d("name_field setonclick " , " name_fieldsetOnclickListener");
        profile_change_btn.setOnClickListener(this);
        Log.d("setOnclickListener sSET" , "setOnclickListener sSET");
        return view;


        //Log.d("RetUrNed" , "ReTuRned");
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.profile_change_btn) {
            Log.d("btn clicked ----" , "btn clicked-----");
            selectImage();
            Log.d("selctimage method done" , "selct image method done");
        }
        if(v.getId()==R.id.name_change__btn)
        {
            Log.d("name_field btn clicked" , "nmaejdsajsetOnclickListener sSET");
            Intent intent = new Intent(getActivity() , Name_Status.class);

            startActivity(intent);

        }
    }

    public void changeName(String s)
    {
//        Often you will want one Fragment to communicate with another, for example to change the content based on a user
//        event. All Fragment-to-Fragment communication is done through the associated Activity. Two Fragments should never
//        communicate directly
        Log.d("changename entry" ,"changename netry");
        System.out.println(s);
        System.out.println(name_field.getText().toString());
           name_field.setText(s);
        System.out.println(name_field.getText());
    }

    /////////////////////////////////////////////////////////////////////////////////

    private void selectImage()

    {
        Log.d("seletimage method enter" , "seletimage meythod enter");
//        final CharSequence[] items = {"Take Photo", "Choose from Library",
//                "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext().getApplicationContext());
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                boolean result = Utility.checkPermission(getContext().getApplicationContext());
//                if (items[item].equals("Take Photo")) {
//                    userChoosenTask = "Take Photo";
//                    if (result)
//                        cameraIntent();
//                } else if (items[item].equals("Choose from Library")) {
//                    userChoosenTask = "Choose from Library";
//                    if (result)
//                        galleryIntent();
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//
//            public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//                switch (requestCode) {
//                    case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
//                        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                            if (userChoosenTask.equals("Take Photo"))
//                                cameraIntent();
//                            else if (userChoosenTask.equals("Choose from Library"))
//                                galleryIntent();
//                        } else {
////code for deny
//                        }
//                        break;
//                }
//            }
//
//            private void cameraIntent() {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent, REQUEST_CAMERA);
//
//            }
//
//            private void galleryIntent() {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);//
//                startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
//            }
//
//        });
//        builder.show();

        Intent gallery=new Intent(Intent.ACTION_PICK , MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        Log.d("gallery opened" , "galleryyyy opend");
        startActivityForResult(gallery , PICK_IMAGE);

        Log.d("image pickdddd" , "image pickedddd");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Onactivityresult entred", "oncjhjaskkhkh");
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
//            Log.d("if enteredjks" , "if jhaskjksakk");
//            imageUri = data.getData();
            //   && requestCode==PICK_IMAGE
//            Log.d("image saved in var" , "image saved in var");
//            person_dp.setImageURI(imageUri);
//            Log.d("image set gtgg" , "image setgjjkjkjk");
            onSelectFromGalleryResult(data);
//            if (requestCode == SELECT_FILE)
//                onSelectFromGalleryResult(data);
//            else if (requestCode == REQUEST_CAMERA)
//                onCaptureImageResult(data);
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;

        imageUri=data.getData();
        if (data != null) {

            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getApplicationContext().getContentResolver(),imageUri );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        person_dp.setImageBitmap(bm);
    }
//
//    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        person_dp.setImageBitmap(thumbnail);
//
//
//    }
}





