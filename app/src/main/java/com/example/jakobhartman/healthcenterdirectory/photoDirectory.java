package com.example.jakobhartman.healthcenterdirectory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import localDatabase.Pictures;


public class photoDirectory extends Activity implements Runnable{
    private GridView gridView;
    private GridViewAdapter customGridAdapter;
    private Thread photoThread;
    private String threadName = "photo thread";
    ArrayList<ProPhoto> pictures = new ArrayList<ProPhoto>();
    Pictures picturedb = new Pictures();
    List<Pictures> pictureData = picturedb.getPictures();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_directory);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        gridView = (GridView) findViewById(R.id.gridView);

        this.start();

    }

    // When the thread runs
    public void run() {
        Log.i("Thread", "Started Running thread");
        for (Pictures pic : pictureData) {
            ProPhoto photo = new ProPhoto();
            photo.setImage(pic.image);
            photo.setName(pic.name);
            pictures.add(photo);
        }

        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid,pictures);
        gridView.setAdapter(customGridAdapter);

        final Intent intent = new Intent(this,employeeDetails.class);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

            }
        });
    }

    public void start() {
        if (photoThread == null)
        {
            photoThread = new Thread (this, threadName);
            photoThread.start();
        }
    }


    @Override
    public Intent  getParentActivityIntent()  {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName");

        Intent newIntent = null;
        try {
            newIntent = new Intent(photoDirectory.this,Class.forName("com.example.jakobhartman.healthcenterdirectory."+className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }

}
