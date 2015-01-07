package com.example.jakobhartman.healthcenterdirectory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
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

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        contactNames = new ArrayList<String>();
        phoneNumbers = new ArrayList<String>();
        setContentView(R.layout.activity_center_details);
        list = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent(); //Gets the extra data passed in the intent

        String text = intent.getStringExtra("Contacts"); //Gets the string value extra labeled "Contacts"
        setTitle(text);

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
    public Intent  getParentActivityIntent()  {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName");

        Intent newIntent = null;
        try {
            newIntent = new Intent(centerDetails.this,Class.forName("com.example.jakobhartman.healthcenterdirectory."+className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
