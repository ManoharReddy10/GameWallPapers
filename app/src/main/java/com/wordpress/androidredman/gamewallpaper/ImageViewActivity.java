package com.wordpress.androidredman.gamewallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;

import java.io.IOException;

public class ImageViewActivity extends AppCompatActivity {


    Bitmap bitmap;
    FloatingActionButton fab;
    CropImageView cropImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        byte[] imageByte = getIntent().getByteArrayExtra("img_byte");

        bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);

//        bitmap=getIntent().getParcelableExtra("img_byte");


//        DisplayMetrics displayMetrics=new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int width=displayMetrics.widthPixels;
//        int height=displayMetrics.heightPixels;

        cropImageView = (CropImageView) findViewById(R.id.CropImageView);
        cropImageView.setImageBitmap(bitmap);
        cropImageView.setAspectRatio(5, 4);
        cropImageView.setFixedAspectRatio(false);
        cropImageView.setGuidelines(1);





       /* String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            wallpaper.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showAlert();
                setasWallpaper();
            }
        });

    }

/*    private void showAlert(){
        AlertDialog.Builder builder=new AlertDialog.Builder(ImageViewActivity.this)
                .setTitle("Wallpaper")
                .setIcon(R.mipmap.ic_launcher)
                .setMessage("Do you want to set this image as wallpaper ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setasWallpaper();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setNeutralButton("Crop", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Crop action", Toast.LENGTH_SHORT).show();
                    }
                });
        builder.show();
    }*/

    private void setasWallpaper() {
        WallpaperManager manager = WallpaperManager.getInstance(ImageViewActivity.this);

        Bitmap croppedbitmap = cropImageView.getCroppedImage();
        try {
            manager.setBitmap(croppedbitmap);
            Toast.makeText(getApplicationContext(), "Wallpaper set successfully", Toast.LENGTH_SHORT).show();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error setting wallpaper", Toast.LENGTH_SHORT).show();
        }
    }
}
