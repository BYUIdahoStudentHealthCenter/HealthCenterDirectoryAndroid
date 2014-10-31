package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import localDatabase.EmployeeContact;


public class employeeDirectory extends Activity {
    Spinner spinner;
    ArrayList filters;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_directory);

        filters = new ArrayList();
        spinner = (Spinner) findViewById(R.id.spinner);
        filters.add("Positions");
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, filters);


        EmployeeContact employees = new EmployeeContact();
        List<EmployeeContact> list = employees.getAllEmployees();
        for(EmployeeContact person: list){
            filters.add(person.position);
        }
        filters = removeDuplicates(filters);

        spinner.setAdapter(adapter);
    }

    public ArrayList<String> removeDuplicates(ArrayList<String> l) {
        Set setItems = new LinkedHashSet(l);
        l.clear();
        l.addAll(setItems);
        return l;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.employee_directory, menu);
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
