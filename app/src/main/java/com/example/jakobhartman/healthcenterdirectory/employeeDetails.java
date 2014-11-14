package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import localDatabase.EmployeeContact;

/**
 * Created by chad on 11/13/14.
 */
public class employeeDetails extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        ArrayList<String> description = new ArrayList<String>();
        ArrayList<String> details = new ArrayList<String>();
        ListView list = (ListView) findViewById(R.id.listView);
        Intent intent = getIntent(); //Gets the extra data passed in the intent

        description.add("First Name");
        description.add("Last Name");
        description.add("Department");
        description.add("Department Email");
        description.add("Department Number");
        description.add("Personal Email");
        description.add("Phone Number");
        description.add("Position");
        description.add("Status");
        description.add("Tier");

        details.add(intent.getStringExtra("first_name"));
        details.add(intent.getStringExtra("last_name"));
        details.add(intent.getStringExtra("department"));
        details.add(intent.getStringExtra("department_email"));
        details.add(intent.getStringExtra("department_number"));
        details.add(intent.getStringExtra("personal_email"));
        details.add(intent.getStringExtra("phone_number"));
        details.add(intent.getStringExtra("position"));
        details.add(intent.getStringExtra("status"));
        details.add(intent.getStringExtra("tier"));

        customListViewAdapter customList = new customListViewAdapter(this,description,details);

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
