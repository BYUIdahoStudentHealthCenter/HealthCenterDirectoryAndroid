package com.example.jakobhartman.healthcenterdirectory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Logger;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import localDatabase.DepartmentContact;


public class centerDirectory extends Activity {
    ListView listView ;
    ArrayList<String> contacts;
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_directory);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        contacts = new ArrayList<String>();
        contacts.add("Department");
        contacts.add("General");
        contacts.add("Front Desk Lines");
        contacts.add("Student Management");
        contacts.add("Other Student Employees");
        contacts.add("Providers");
        contacts.add("Professional Staff");
        contacts.add("Fax Numbers");
        contacts.add("Campus Contacts");
        contacts.add("Provo Contacts");
        contacts.add("DMBA");

        listView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, contacts);
        // Assign adapter to ListView
        listView.setAdapter(adapter);



        // Get ListView object from xml
        final Intent intent = new Intent(this,centerDetails.class);
        intent.putExtra("ParentClassName","centerDirectory");

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position != 0){
                    String text = contacts.get(position);
                    intent.putExtra("Contacts",text);
                    startActivity(intent);
                }
            }

        });
    }


}
