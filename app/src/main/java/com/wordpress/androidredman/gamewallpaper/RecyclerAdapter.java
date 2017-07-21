package com.wordpress.androidredman.gamewallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Redman on 7/18/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyviewHolder>{

    Context mContext;
    List<String> imageUrlList=new ArrayList<>();

    List<Bitmap> bitmapList=new ArrayList<>();

    Bitmap[] bitmapArray;

    RecyclerAdapter(Context ctx, List<String> imagesList){
        mContext=ctx;
        imageUrlList=imagesList;
        Log.d("imagesList","imagesList Size "+imageUrlList.size());
        bitmapArray=new Bitmap[imageUrlList.size()];
    }

    @Override
    public MyviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_recycler,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyviewHolder holder, final int position) {


      Glide.with(mContext)
                .asBitmap()
              .load(imageUrlList.get(position))
//              .into(new BitmapImageViewTarget(holder.wallpaperImage){
//                  @Override
//                  protected void setResource(Bitmap resource) {
//                      bitmapList.add(resource);
//                      super.setResource(resource);
//                  }
//              });
              .into(new SimpleTarget<Bitmap>() {
                  @Override
                  public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                   holder.wallpaperImage.setImageBitmap(resource);
                      bitmapList.add(resource);
                      bitmapArray[position]=resource;
                  }
              });



        holder.wallpaperImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showAlert(position);
                return false;
            }
        });

        holder.wallpaperImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Bitmap bmp=bitmapList.get(position);
                if(bitmapArray[position]!=null) {
                    Bitmap bmp = bitmapArray[position];


                    Intent intent = new Intent(mContext, ImageViewActivity.class);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imagebytes = stream.toByteArray();
                    intent.putExtra("img_byte", imagebytes);
                    mContext.startActivity(intent);
                }
                else
                    Toast.makeText(mContext.getApplicationContext(), "Please Wait Loading Image", Toast.LENGTH_SHORT).show();

              /*  try {
                    //Write file
                    String filename = "bitmap.png";
                    FileOutputStream stream = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    //Cleanup
                    stream.close();
                    bmp.recycle();

                    //Pop intent
                    Intent in1 = new Intent(mContext, ImageViewActivity.class);
                    in1.putExtra("image", filename);
                    mContext.startActivity(in1);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }
        });

    }

    @Override
    public int getItemCount() {
        return imageUrlList.size();
    }

    public class MyviewHolder extends RecyclerView.ViewHolder {
        ImageView wallpaperImage;

        public MyviewHolder(View itemView) {
            super(itemView);
            wallpaperImage=itemView.findViewById(R.id.iv_wallpaperImage);
        }
    }

private void showAlert(final int pos){
    AlertDialog.Builder builder=new AlertDialog.Builder(mContext)
            .setMessage("Do you want to set this Image as wallpaper?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                  setWallpaper(pos);

                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
}

private void setWallpaper(int pos){
    WallpaperManager wallpaperManager= WallpaperManager.getInstance(mContext);
    try {
        wallpaperManager.setBitmap(bitmapList.get(pos));
        Toast.makeText(mContext, "Wallpaper set successfully", Toast.LENGTH_SHORT).show();

    } catch (IOException e) {
        e.printStackTrace();
        Toast.makeText(mContext, "Unable to set wallpaper", Toast.LENGTH_SHORT).show();
    }
}
}
