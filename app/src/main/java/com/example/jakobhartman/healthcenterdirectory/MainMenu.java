package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.activeandroid.query.Delete;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import localDatabase.DepartmentContact;
import localDatabase.EmployeeContact;
import localDatabase.Pictures;
import localDatabase.Tier;
import localDatabase.loginInfo;

import static android.widget.Toast.*;


public class MainMenu extends Activity {
    ListView listView ;
    List<DepartmentContact> departments;
    List<EmployeeContact> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu); //Set the view

        try{
            // Get syncDate
            loginInfo user = new loginInfo();
            List<loginInfo> userList = user.getAll();
            Calendar expDate = userList.get(0).lastLogIn;


            // Get current date
            Calendar today = new GregorianCalendar();


            if (expDate.compareTo(today) < 0) //returns less than 0 if first date is before second date
            {
                //Alert the user that they need to sync to get their data back
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setMessage("You need to sync");
                builder1.setCancelable(true);
                builder1.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert1 = builder1.create();
                alert1.show();

                new Delete().from(DepartmentContact.class).execute();
                new Delete().from(EmployeeContact.class).execute();
                new Delete().from(Pictures.class).execute();
            }



            Log.i("Expire month", " " + expDate.get(Calendar.MONTH));
            Log.i("Expire day", " " + expDate.get(Calendar.DAY_OF_MONTH));
            Log.i("Expire Year", " " + expDate.get(Calendar.YEAR));

        } catch (Exception e){

        }


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
           // values.add("Needs to sync...");
        }
        else{
            values.add("Employee Directory");
            values.add("Emergency Calling Tree");
           // values.add("Photo Directory");
        }
        // Defined Array values to show in ListView
        values.add("Sync");

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
                        startActivity(intent4);
                        break;
                }
            }

        });
    }

}
