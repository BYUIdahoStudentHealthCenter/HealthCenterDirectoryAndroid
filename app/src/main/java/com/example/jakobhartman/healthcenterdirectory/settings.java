package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import localDatabase.DepartmentContact;


public class settings extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Firebase.setAndroidContext(this); // initialize firebase
        Firebase ref = new Firebase("https://boiling-fire-7455.firebaseio.com/");
                 ref.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         for(DataSnapshot data : dataSnapshot.getChildren()){
                             for (DataSnapshot contact : data.getChildren()){
                                 DepartmentContact newContact = new DepartmentContact();
                                 newContact.department = data.getName();
                                 newContact.name = contact.child("Name").getValue().toString();
                                 newContact.number = contact.child("Number").getValue().toString();
                                 newContact.save();
                             }
                         }
                     }

                     @Override
                     public void onCancelled(FirebaseError firebaseError) {

                     }
                 });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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
