package com.ibs.android.ibssmarthome.Activity;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.ibs.android.ibssmarthome.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import pub.devrel.easypermissions.EasyPermissions;

public class RequestComposeActivity extends AppCompatActivity {
    private ImageButton ibBack,ibAcctach,ibSend;
    private PopupMenu popup;
    private String fileName,fullPath;
    private Uri imageUri;
    private ChipGroup chipGroup;

    private final int IMAGE_REQUEST_CODE=100;
    private final int PICK_IMAGE=101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_compose);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        ibAcctach   = findViewById(R.id.imagebutton_request_attach);
        ibBack      = findViewById(R.id.imagebutton_request_backtorequestmain);
        ibSend      = findViewById(R.id.imagebutton_request_send);
        chipGroup   = findViewById(R.id.chip_group_request);

        popup = new PopupMenu(RequestComposeActivity.this,ibAcctach);
        popup.getMenuInflater().inflate(R.menu.popup_requestattach_menu, popup.getMenu());

        ibAcctach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.show();
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item_request_new_photo:
                        CapturePhoto();
                        break;
                    case R.id.item_request_load_photo:
                        LoadPhoto();
                        break;
                }
                return true;
            }
        });
    }

    private void CapturePhoto(){
        Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File image_file=GenFilepath();
        Uri image_uri=Uri.fromFile(image_file);
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(camera_intent,IMAGE_REQUEST_CODE);
    }
    private void LoadPhoto(){
        Intent gallery= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        gallery.setType("image/*");
        startActivityForResult(gallery,PICK_IMAGE);
    }

    public File GenFilepath(){
        fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        fileName = fileName+".JPG";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_DCIM);

        File image_file=new File(storageDir,fileName);
        return image_file;
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case IMAGE_REQUEST_CODE:
                if (resultCode==RESULT_OK){
                    Toast.makeText(RequestComposeActivity.this,"Image captured",Toast.LENGTH_SHORT).show();
                    fullPath = getExternalFilesDir(Environment.DIRECTORY_DCIM)+"/"+fileName;
                    File imgFile = new  File(fullPath);
                    if(imgFile.exists()){
                        Log.d("SmartHome","RequestComposeActivity: path ="+fullPath);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        Drawable drawable = new BitmapDrawable(getResources(),myBitmap);
                        final Chip entryChip = getChip(chipGroup, fileName,drawable);
                        chipGroup.addView(entryChip);
                    }
                }
                else{
                    Toast.makeText(RequestComposeActivity.this,"Image capture failed",Toast.LENGTH_SHORT).show();
                }
                break;
            case PICK_IMAGE:


                if (resultCode==RESULT_OK){
                    imageUri = data.getData();
                    fullPath = getPath(imageUri);
                    fileName = fullPath.substring(fullPath.lastIndexOf('/') + 1);

                    String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                    if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
                        File imgFile = new  File(fullPath);
                        if(imgFile.exists()){
                            Log.d("SmartHome","RequestComposeActivity: path ="+fullPath);
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            Drawable drawable = new BitmapDrawable(getResources(),myBitmap);
                            final Chip entryChip = getChip(chipGroup, fileName,drawable);
                            chipGroup.addView(entryChip);
                        }
                    } else {
                        EasyPermissions.requestPermissions(this, "Access for storage",
                                101, galleryPermissions);
                    }

                }
                break;
        }
    }
    private Chip getChip(final ChipGroup entryChipGroup, String text,Drawable drawable) {
        final Chip chip = new Chip(this);
        chip.setChipDrawable(ChipDrawable.createFromResource(this, R.xml.request_chip));
        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 50,
                getResources().getDisplayMetrics()
        );
        int iconSizeDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 100,
                getResources().getDisplayMetrics()
        );
        chip.setChipIconSize(iconSizeDp);
        chip.setMinHeight(iconSizeDp);
        //chip.setChipIcon(ContextCompat.getDrawable(this,R.drawable.living_room));

        chip.setChipIcon(drawable);
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        //chip.setText(text);
        chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryChipGroup.removeView(chip);
            }
        });
        return chip;
    }


}
