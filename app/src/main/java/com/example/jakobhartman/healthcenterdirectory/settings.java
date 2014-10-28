package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import localDatabase.DepartmentContact;
import localDatabase.EmployeeContact;


public class settings extends Activity {
    int i= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Firebase.setAndroidContext(this); // initialize firebase

    }

    public void syncWithDatabase(View v){
        Firebase ref = new Firebase("https://boiling-fire-7455.firebaseio.com/CenterNumbers");
        Firebase employees = new Firebase("https://boiling-fire-7455.firebaseio.com/Employee");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ActiveAndroid.beginTransaction();
                try {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        for (DataSnapshot contact : data.getChildren()){

                            DepartmentContact newContact = new DepartmentContact();
                            newContact.department = data.getName();
                            newContact.name = contact.child("Name").getValue().toString();
                            newContact.number = contact.child("Number").getValue().toString();
                            newContact.save();
                            i++;
                        }
                    }
                    ActiveAndroid.setTransactionSuccessful();
                    System.out.println(i);
                }
                finally {
                    ActiveAndroid.endTransaction();
                }
                TextView textView =(TextView) findViewById(R.id.textView);
                textView.setText("Found " + i + " Contacts");
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        employees.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ActiveAndroid.beginTransaction();
                try {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                            EmployeeContact newContact = new EmployeeContact();
                            newContact.departmentEmail = data.child("Department_Email").getValue().toString();
                            newContact.departmentNumber = data.child("Department_Number").getValue().toString();
                            newContact.firstName = data.child("First_Name").getValue().toString();
                            newContact.imageName = data.child("Image_Name").getValue().toString();
                            newContact.lastName = data.child("Last_Name").getValue().toString();
                            newContact.personalEmail = data.child("Personal_Email").getValue().toString();
                            newContact.phoneNumber = data.child("Phone_Number").getValue().toString();
                            newContact.position = data.child("Position").getValue().toString();
                            newContact.status = data.child("Status").getValue().toString();
                            newContact.tier = data.child("Tier").getValue().toString();
                            newContact.save();
                            i++;

                    }
                    System.out.println(i);
                    ActiveAndroid.setTransactionSuccessful();
                }
                finally {
                    ActiveAndroid.endTransaction();
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
