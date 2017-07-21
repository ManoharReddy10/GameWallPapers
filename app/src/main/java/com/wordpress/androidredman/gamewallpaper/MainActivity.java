package com.wordpress.androidredman.gamewallpaper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    List<String> urlList=new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseDatabase=FirebaseDatabase.getInstance();

        DatabaseReference reference=firebaseDatabase.getReferenceFromUrl("https://gamewallpapers-1bd95.firebaseio.com/Game");
        String data=reference.getKey();

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String mycs=dataSnapshot.toString();
                Iterable<DataSnapshot> images=dataSnapshot.getChildren();


                Log.d("DataCs",""+mycs);
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //Getting the data from snapshot
                    String imageUrl = postSnapshot.getValue(String.class);

                    urlList.add(imageUrl);
                    //Adding it to a string

                    //Displaying it on textview
                    Log.d("DataURL",""+imageUrl);
                }
                setAdapter();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };
        reference.addValueEventListener(valueEventListener);

        Log.d("Data",data);

        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));


    }
    private void setAdapter(){
        recyclerAdapter=new RecyclerAdapter(MainActivity.this,urlList);
        recyclerView.setAdapter(recyclerAdapter);

    }
}
