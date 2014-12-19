package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import localDatabase.DepartmentContact;
import localDatabase.EmployeeContact;
import localDatabase.Tier;
import localDatabase.loginInfo;

import static android.widget.Toast.*;


public class MainMenu extends Activity {
    ListView listView ;
    List<DepartmentContact> departments;
    List<EmployeeContact> employees;

    //Our own public function
    public void goToSettings(MenuItem item){
        final Intent intent4 = new Intent(this, settings.class);
        intent4.putExtra("ParentClassName","MainMenu");
        startActivity(intent4);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu); //Set the view

        loginInfo user = new loginInfo();
        List<loginInfo> userList = user.getUsername();

        Date lastSync = userList.get(0).lastLogIn;
        Calendar syncDate = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        syncDate.setTime(lastSync);

        int todayMonth = today.get(Calendar.MONTH);
        int syncMonth = syncDate.get(Calendar.MONTH);


        Log.i("SyncMonth", " "+syncMonth);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);
        DepartmentContact center = new DepartmentContact();
        EmployeeContact persons = new EmployeeContact();


         departments = center.getAllDepartments();
         employees = persons.getAllEmployees();
        ArrayList<String> values = new ArrayList<String>();
        if(departments.size() < 1){
            values.add("Needs to sync...");

        }
        else{
            values.add("Department Directory");
        }
        if(employees.size() < 1){
            values.add("Needs to sync...");
            values.add("Needs to sync...");
            values.add("Needs to sync...");
        }
        else{
            values.add("Employee Directory");
            values.add("Emergency Calling Tree");
            values.add("Photo Directory");
        }
        // Defined Array values to show in ListView
        values.add("Sign In/Sync");

        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);


        // Assign adapter to ListView
        listView.setAdapter(adapter);

        final Intent intent0 = new Intent(this, centerDirectory.class);
        intent0.putExtra("ParentClassName","MainMenu");
        final Intent intent1 = new Intent(this, employeeDirectory.class);
        intent1.putExtra("ParentClassName","MainMenu");
        final Intent intent2 = new Intent(this, emergencyList.class);
        intent2.putExtra("ParentClassName","MainMenu");
        final Intent intent3 = new Intent(this, photoDirectory.class);
        intent3.putExtra("ParentClassName","MainMenu");
        final Intent intent4 = new Intent(this, settings.class);
        intent4.putExtra("ParentClassName","MainMenu");

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemP = position;

                // Show Alert
                switch (itemP){
                    case 0:
                        if(departments.size() > 0){
                            startActivity(intent0);
                        }

                        break;
                    case 1:
                        if(employees.size() > 0){
                            startActivity(intent1);
                        }

                        break;
                    case 2:
                        if(employees.size() > 0){
                            startActivity(intent2);
                        }

                        break;
                    case 3:
                        if(employees.size() > 0) {
                            startActivity(intent3);
                        }
                        break;
                    case 4:
                        startActivity(intent4);
                        break;
                }
            }

        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
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
