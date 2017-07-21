package com.wordpress.androidredman.gamewallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.FileInputStream;

public class ImageViewActivity extends AppCompatActivity {

    ImageView wallpaper;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);
        wallpaper=(ImageView)findViewById(R.id.iv_fullWallpaper);

        byte[] imageByte=getIntent().getByteArrayExtra("img_byte");

        bitmap= BitmapFactory.decodeByteArray(imageByte,0,imageByte.length);

//        bitmap=getIntent().getParcelableExtra("img_byte");
        if(bitmap!=null)
            wallpaper.setImageBitmap(bitmap);






       /* String filename = getIntent().getStringExtra("image");
        try {
            FileInputStream is = this.openFileInput(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            wallpaper.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}
