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
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Logger;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


public class centerDirectory extends Activity {
    ListView listView ;
    ArrayList<String> numbers;
    ArrayList<String> contacts;
    ArrayList<String> departments;
    customListViewAdapter adapter;

    //Our own public function
    public void goToSettings(MenuItem item){
        final Intent intent3 = new Intent(this, settings.class);
        startActivity(intent3);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_directory);
        contacts = new ArrayList<String>();
        numbers = new ArrayList<String>();
        departments = new ArrayList<String>();
        contacts.add("Department");
        departments.add("General");
        departments.add("Front Desk Lines");
        departments.add("Student Management");
        departments.add("Other Student Employees");
        departments.add("Providers");
        departments.add("Professional Staff");
        departments.add("Fax Numbers");
        departments.add("Campus Contacts");
        departments.add("Provo Numbers");
        departments.add("DMBA");

        contacts.addAll(departments);
        for(int i = 0;i < 9;i++){
            numbers.add("");
        }
        numbers.add("");
        listView = (ListView) findViewById(R.id.list);
        adapter = new customListViewAdapter(this,contacts,numbers);
        // Assign adapter to ListView
        listView.setAdapter(adapter);


        //ref.child("message").setValue("This is a test message!"); //writes to database



        // Get ListView object from xml


        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if(position != 0){
                    TextView textview = (TextView) listView.findViewById(R.id.txt1);
                    String text = textview.getText().toString();
                    System.out.println(text);
                }
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
