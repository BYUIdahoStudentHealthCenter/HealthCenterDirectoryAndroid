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

import localDatabase.EmployeeContact;
import localDatabase.Pictures;

//This class was an experiment for using threads. Doesn't really help split up the work
public class photoDirectory extends Activity{
    private GridView gridView;
    private GridViewAdapter customGridAdapter;
    private Thread photoThread;
    private String threadName = "photo thread";
    ArrayList<ProPhoto> pictures = new ArrayList<ProPhoto>();

    // This will store the employee pictures and their names
    Pictures picturedb = new Pictures();

    // Employee database object
    EmployeeContact employeeMatch = new EmployeeContact();
    List<EmployeeContact> matchList = new ArrayList<EmployeeContact>();

    List<Pictures> pictureData = picturedb.getPictures();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_directory);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        gridView = (GridView) findViewById(R.id.gridView);

        Log.i("Thread", "Started Running thread");
        for (Pictures pic : pictureData) {
            ProPhoto photo = new ProPhoto();
            photo.setImage(pic.image);
            photo.setName(pic.name);
            pictures.add(photo);
            String[] name = pic.name.split("\\s+");
            Log.i("Split name", name[0]);
            Log.i("Split name", name[1]);
            // Add that same employee to the match list to get their info
            matchList.add(employeeMatch.getContactByFullName(name[0], name[1]));
        }

        customGridAdapter = new GridViewAdapter(this, R.layout.row_grid, pictures);
        gridView.setAdapter(customGridAdapter);


        final Intent intent = new Intent(this,employeeDetails.class);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // A way to identify who was clicked
                EmployeeContact details = matchList.get(position);

                // Then attach that persons info to the new intent

                intent.putExtra("department", details.department);
                intent.putExtra("department_email", details.departmentEmail);
                intent.putExtra("department_number", details.departmentNumber);
                intent.putExtra("first_name", details.firstName);
                intent.putExtra("last_name", details.lastName);
                intent.putExtra("personal_email", details.personalEmail);
                intent.putExtra("phone_number", details.phoneNumber);
                intent.putExtra("position", details.position);
                intent.putExtra("status", details.status);
                intent.putExtra("tier", details.tier);
                intent.putExtra("ParentClassName","photoDirectory");

                // Start the employeeDetails activity
                startActivity(intent);

            }
        });

        // Remnants of thread setup
        //this.start();
    }

    // When the thread runs
   // Remnants of thread set up public void run() {}



/*
    public void start() {
        if (photoThread == null)
        {
            photoThread = new Thread (this, threadName);
            photoThread.start();
        }
    }
*/
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
