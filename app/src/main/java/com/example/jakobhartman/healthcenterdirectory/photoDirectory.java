package com.example.jakobhartman.healthcenterdirectory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import localDatabase.Pictures;


public class photoDirectory extends Activity {
    private GridView gridView;
    private GridViewAdapter customGridAdapter;

    //Our own public function
    public void goToSettings(MenuItem item){
        final Intent intent4 = new Intent(this, settings.class);
        startActivity(intent4);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_directory);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        gridView = (GridView) findViewById(R.id.gridView);

        ArrayList<ProPhoto> pictures = new ArrayList<ProPhoto>();

        Pictures picturedb = new Pictures();
        List<Pictures> pictureData = picturedb.getPictures();


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_directory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
