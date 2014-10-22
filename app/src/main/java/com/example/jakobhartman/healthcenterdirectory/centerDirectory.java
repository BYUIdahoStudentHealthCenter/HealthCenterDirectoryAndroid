package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Logger;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


public class centerDirectory extends Activity {
    ListView listView ;


    //Our own public function
    public void goToSettings(MenuItem item){
        final Intent intent3 = new Intent(this, settings.class);
        startActivity(intent3);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_directory);

        Firebase.setAndroidContext(this); // initialize firebase
        Firebase ref = new Firebase("https://boiling-fire-7455.firebaseio.com/");
        final ArrayList<String> contacts = new ArrayList<String>();
        final ArrayList<String> numbers = new ArrayList<String>();


        final customListViewAdapter adapter = new customListViewAdapter(this,contacts,numbers);
        //ref.child("message").setValue("This is a test message!"); //writes to database

        ref.child("CenterNumbers").addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data : dataSnapshot.getChildren()){

                    for(DataSnapshot contact: data.getChildren()){
                        contacts.add(contact.child("Name").getValue().toString());
                        numbers.add(contact.child("Number").getValue().toString());
                    }
                }
               
               adapter.notifyDataSetChanged();
           }

           @Override
           public void onCancelled(FirebaseError firebaseError) {

           }
        });



        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // Show Alert
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.center_directory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
