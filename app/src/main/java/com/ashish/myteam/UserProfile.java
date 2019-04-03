package com.ashish.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UserProfile extends AppCompatActivity {
    TextView nameuser, walletuser, review, network, plugins, myapps, mainmenus,
            walletuser1, textViewnknm, textViewdob, textViewmob, textViewblgrp, textViewfood, textViewpan, textViewaadhar;
    Button btnguide, addImg;
    Animation atg, atgtwo, atgthree;
    ImageView imageView2;
    String TempHolder, nam, niknam, tnm, Umail, pan, aadhar, dob, food, blgrp,mob;
    byte[] userimg;
    final int REQUEST_CODE_GALLERY = 888;
    Dialog dialog, updateDialog;
    String editnkm, editdob, editmob, editblgrp, editfood, editpan, editaadhar;

    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        dialog = new Dialog(this);
        updateDialog = new Dialog(this);

        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        atgtwo = AnimationUtils.loadAnimation(this, R.anim.atgtwo);
        atgthree = AnimationUtils.loadAnimation(this, R.anim.atgthree);

        imageView2 = findViewById(R.id.imageView2);
        nameuser = findViewById(R.id.nameuser);
        addImg = findViewById(R.id.profile_img_btn);
        walletuser = findViewById(R.id.walletuser);
        walletuser1 = findViewById(R.id.walletuser1);
        review = findViewById(R.id.review);
        network = findViewById(R.id.network);
        plugins = findViewById(R.id.plugins);
        myapps = findViewById(R.id.myapps);
        mainmenus = findViewById(R.id.welcomeNote);
        btnguide = findViewById(R.id.btnguide);

        textViewnknm = findViewById(R.id.textViewnknm);
        textViewdob = findViewById(R.id.textViewdob);
        textViewmob = findViewById(R.id.textViewmob);
        textViewblgrp = findViewById(R.id.textViewblgrp);
        textViewfood = findViewById(R.id.textViewfood);
        textViewpan = findViewById(R.id.textViewpan);
        textViewaadhar = findViewById(R.id.textViewaadhar);

        //imageView3.startAnimation(atg);
        //pagetitle.startAnimation(atgtwo);
        //pagesubtitle.startAnimation(atgtwo);
        //name_edit_text.startAnimation(atgtwo);
        btnguide.startAnimation(atg);

        TempHolder = getIntent().getStringExtra("User_Email");

        //geting Data

        Cursor cursor = MainActivity.db.getuserAlldetails(TempHolder);
        if (cursor.getCount() == 0) {
            Toast.makeText(UserProfile.this, "Database Error", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToFirst();
            Umail = cursor.getString(cursor.getColumnIndex("email"));
            nam = cursor.getString(cursor.getColumnIndex("name"));
            niknam = cursor.getString(cursor.getColumnIndex("nick_name"));
            tnm = cursor.getString(cursor.getColumnIndex("team"));
            userimg = cursor.getBlob(cursor.getColumnIndex("image"));
            aadhar = cursor.getString(cursor.getColumnIndex("aadhaar"));
            dob = cursor.getString(cursor.getColumnIndex("dob"));
            food = cursor.getString(cursor.getColumnIndex("food"));
            mob = cursor.getString(cursor.getColumnIndex("mobile"));
            blgrp = cursor.getString(cursor.getColumnIndex("bl_grp"));
            pan = cursor.getString(cursor.getColumnIndex("pan_no"));
        }

        nameuser.setText(nam);
        mainmenus.setText("Welcome to " + niknam + "'s Profile");
        walletuser.setText(tnm);
        walletuser1.setText(Umail);
        textViewnknm.setText(niknam);
        textViewdob.setText(dob);
        textViewmob.setText(mob);
        textViewblgrp.setText(blgrp);
        textViewfood.setText(food);
        textViewpan.setText(pan);
        textViewaadhar.setText(aadhar);

        Bitmap bitmap = BitmapFactory.decodeByteArray(userimg, 0, userimg.length);
        imageView2.setImageBitmap(bitmap);

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogUpdate(UserProfile.this, TempHolder);
            }
        });

        btnguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogProfileUpdate(UserProfile.this, TempHolder);
            }
        });
    }

    ImageView uploadImg;
    Button uploadBtn;

    private void showDialogUpdate(Activity activity, final String mail) {

        dialog.setContentView(R.layout.upload_image);
        uploadImg = (ImageView) dialog.findViewById(R.id.UpimageView);
        uploadBtn = (Button) dialog.findViewById(R.id.uploadbutton);

        int width1 = (int) (activity.getResources().getDisplayMetrics().widthPixels * .67);
        int height1 = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.57);
        dialog.getWindow().setLayout(width1, height1);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        UserProfile.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MainActivity.db.updateImg(
                            mail,
                            MainActivity.imageViewToByte(uploadImg)
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Updated successfully!!!", Toast.LENGTH_SHORT).show();
                } catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateUserData();
            }
        });
    }

    TextInputEditText editNknm, editDob, editMob, editBgrp, editFood, editPan, editAadhar;
    Button updatebtn;

    private void DialogProfileUpdate(Activity activity, final String mail) {

        updateDialog.setContentView(R.layout.label_text_view);
        editNknm = (TextInputEditText) updateDialog.findViewById(R.id.name_edit_text1);
        editDob = (TextInputEditText) updateDialog.findViewById(R.id.name_edit_text2);
        editMob = (TextInputEditText) updateDialog.findViewById(R.id.name_edit_text3);
        editBgrp = (TextInputEditText) updateDialog.findViewById(R.id.name_edit_text4);
        editFood = (TextInputEditText) updateDialog.findViewById(R.id.name_edit_text5);
        editPan = (TextInputEditText) updateDialog.findViewById(R.id.name_edit_text6);
        editAadhar = (TextInputEditText) updateDialog.findViewById(R.id.name_edit_text7);
        updatebtn = (Button) updateDialog.findViewById(R.id.ProfileUpdatebtn);

        int width1 = (int) (activity.getResources().getDisplayMetrics().widthPixels * .83);
        int height1 = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.87);
        updateDialog.getWindow().setLayout(width1, height1);
        updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        updateDialog.show();

        //getting all data from db

        Cursor cursorUser = MainActivity.db.getuserAlldetails(mail);
        if (cursorUser.getCount() == 0) {
            Toast.makeText(UserProfile.this, "No Data", Toast.LENGTH_SHORT).show();
        } else {
            cursorUser.moveToFirst();
            editnkm = cursorUser.getString(cursorUser.getColumnIndex("nick_name"));
            editNknm.setText(editnkm);
            editdob = cursorUser.getString(cursorUser.getColumnIndex("dob"));
            editDob.setText(editdob);
            editfood = cursorUser.getString(cursorUser.getColumnIndex("food"));
            editFood.setText(editfood);
            editmob = cursorUser.getString(cursorUser.getColumnIndex("mobile"));
            editMob.setText(editmob);
            editblgrp = cursorUser.getString(cursorUser.getColumnIndex("bl_grp"));
            editBgrp.setText(editblgrp);
            editpan = cursorUser.getString(cursorUser.getColumnIndex("pan_no"));
            editPan.setText(editpan);
            editaadhar = cursorUser.getString(cursorUser.getColumnIndex("aadhaar"));
            editAadhar.setText(editaadhar);
        }

        editDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dobPicker = new DatePickerDialog(
                        UserProfile.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dobPicker.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dobPicker.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date;
                String dayS = String.valueOf(day);
                String monthS = String.valueOf(month);
                if(month < 10 )
                {
                    monthS = "0" + monthS;
                }
                if(day < 10 )
                {
                    dayS = "0" + dayS;
                }

                date = year + "-" + monthS + "-" + dayS;
                editDob.setText(date);
            }
        };

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserDetails(mail);
            }
        });


    }

    public void updateUserDetails(String mailid)
    {
        String nkname = editNknm.getText().toString().trim();
        String dob = editDob.getText().toString().trim();
        String mob = editMob.getText().toString().trim();
        String blgp = editBgrp.getText().toString().trim();
        String food = editFood.getText().toString().trim();
        String pan = editPan.getText().toString().trim();
        String aadhar = editAadhar.getText().toString().trim();

        if(nkname.length() >= 3 && dob.length() != 0 && mob.length() == 10 && blgp.length() >=2 && food.length() >=3 && pan.length() ==10 && aadhar.length() == 12 )
        {
            try {
                    MainActivity.db.updateUserDetails(
                            mailid,
                            Signup.capitailizeWord(nkname),
                            dob,
                            mob,
                            blgp,
                            Signup.capitailizeWord(food),
                            pan.toUpperCase(),
                            aadhar
                );
                updateDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Updated successfully!!!", Toast.LENGTH_SHORT).show();
            } catch (Exception error) {
                Log.e("Update error", error.getMessage());
            }
            updateUserData();
        }
        else if(nkname.length() <3)
        {
            Toast.makeText(UserProfile.this, "Nick name should have atleast 3 characters", Toast.LENGTH_SHORT).show();
        }
        else if (dob.length() ==0)
        {
            Toast.makeText(UserProfile.this, "DOB should not be null", Toast.LENGTH_SHORT).show();
        }
        else if (mob.length() <10)
        {
            Toast.makeText(UserProfile.this, "Mobile no should contain 10 digits", Toast.LENGTH_SHORT).show();
        }
        else if (blgp.length() < 2)
        {
            Toast.makeText(UserProfile.this, "Blood Grp needs atleast 2 characters", Toast.LENGTH_SHORT).show();
        }
        else if (food.length() < 3)
        {
            Toast.makeText(UserProfile.this, "Min 3 characters (Veg/Nonveg)", Toast.LENGTH_SHORT).show();
        }
        else if (pan.length() < 10)
        {
            Toast.makeText(UserProfile.this, "Pan contains 10 characters", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(UserProfile.this, "Aadhaar contains 12 digits", Toast.LENGTH_SHORT).show();
        }
    }


    private void updateUserData() {
        // get all data from sqlite
        Cursor cursor = MainActivity.db.getuserAlldetails(TempHolder);
        if (cursor.getCount() == 0) {
            Toast.makeText(UserProfile.this, "Database Error", Toast.LENGTH_SHORT).show();
        } else {
            cursor.moveToFirst();
            Umail = cursor.getString(cursor.getColumnIndex("email"));
            nam = cursor.getString(cursor.getColumnIndex("name"));
            niknam = cursor.getString(cursor.getColumnIndex("nick_name"));
            tnm = cursor.getString(cursor.getColumnIndex("team"));
            userimg = cursor.getBlob(cursor.getColumnIndex("image"));
            aadhar = cursor.getString(cursor.getColumnIndex("aadhaar"));
            dob = cursor.getString(cursor.getColumnIndex("dob"));
            food = cursor.getString(cursor.getColumnIndex("food"));
            mob = cursor.getString(cursor.getColumnIndex("mobile"));
            blgrp = cursor.getString(cursor.getColumnIndex("bl_grp"));
            pan = cursor.getString(cursor.getColumnIndex("pan_no"));
        }

        nameuser.setText(nam);
        mainmenus.setText("Welcome to " + niknam + "'s Profile");
        walletuser.setText(tnm);
        walletuser1.setText(Umail);
        textViewnknm.setText(niknam);
        textViewdob.setText(dob);
        textViewmob.setText(mob);
        textViewblgrp.setText(blgrp);
        textViewfood.setText(food);
        textViewpan.setText(pan);
        textViewaadhar.setText(aadhar);

        Bitmap bitmap = BitmapFactory.decodeByteArray(userimg, 0, userimg.length);
        imageView2.setImageBitmap(bitmap);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            CropImage.activity(uri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setRequestedSize(512, 512, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(resultUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    uploadImg.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }
/*
    @Override
    public void onBackPressed() {
        Intent setIntent = new Intent(UserProfile.this, list_test.class);
        startActivity(setIntent);
        finish();
    } */

}
