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
    ArrayList<String> contactNames;
    ArrayList<String> phoneNumbers;
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contactNames = new ArrayList<String>();
        phoneNumbers = new ArrayList<String>();
        setContentView(R.layout.activity_center_details);
        list = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent(); //Gets the extra data passed in the intent

        String text = intent.getStringExtra("Contacts"); //Gets the string value extra labeled "Contacts"

        System.out.println(text);
        /**********************************************************************************
         * Local Database Fill page with  active android
         **********************************************************************************/

        DepartmentContact getDatabase = new DepartmentContact();
        List<DepartmentContact> contacts = getDatabase.getContacts(text);
        contactNames.add("Name");
        phoneNumbers.add("Number");

        for(DepartmentContact contact : contacts){
            System.out.println(contact.name);
            contactNames.add(contact.name);
            phoneNumbers.add(contact.number);
        }
        customListViewAdapter customList = new customListViewAdapter(this,contactNames,phoneNumbers);

        list.setAdapter(customList);
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
