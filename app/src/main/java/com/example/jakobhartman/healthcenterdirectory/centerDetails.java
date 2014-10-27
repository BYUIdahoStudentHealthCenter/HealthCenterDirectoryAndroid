package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import localDatabase.DepartmentContact;


public class centerDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_details);

        Intent intent = getIntent(); //Gets the extra data passed in the intent

        String text = intent.getStringExtra("Contacts"); //Gets the string value extra labeled "Contacts"
        TextView textView = (TextView) findViewById(R.id.textOut);
                textView.setText(text.toString());

        /**********************************************************************************
         * Local Database Fill page with  active android
         **********************************************************************************/

        DepartmentContact
        List<DepartmentContact> depContact = select.all().from(DepartmentContact.class).execute();

        for (DepartmentContact details:depContact) {
            textView.append(details.toString());
        }




        /*****************************************************************************
         * Test FireBase
         *****************************************************************************/
        // Connect to the firebase database
        Firebase.setAndroidContext(this); // initialize firebase
        Firebase ref = new Firebase("https://boiling-fire-7455.firebaseio.com/CenterNumbers");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){ // Gets the department
                    for (DataSnapshot contact : data.getChildren()){ // Gets the children of Department

                        String department = data.getName().toString(); //This returns "centerNumbers"
                        Log.i("database:department",department);
                        String name = contact.child("Name").getValue().toString();
                        Log.i("database:name", name);
                        String number = contact.child("Number").getValue().toString();
                        Log.i("database:number", number);
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
        getMenuInflater().inflate(R.menu.center_details, menu);

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
