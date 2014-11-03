package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import localDatabase.EmployeeContact;


public class employeeDirectory extends Activity {
    Spinner spinner;
    ListView listView;
    customListViewAdapter listAdapter;
    ArrayList<String> filters;
    ArrayList<String> employeeList;
    ArrayList<String> position;
    ArrayList<String> personId;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_directory);

        filters = new ArrayList<String>();
        employeeList = new ArrayList<String>();
        position = new ArrayList<String>();
        personId = new ArrayList<String>();

        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.listView);

        filters.add("Department");
        employeeList.add("Contact");
        position.add("Position");

        listAdapter = new customListViewAdapter(this,employeeList,position);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, filters);


        EmployeeContact employees = new EmployeeContact();
        List<EmployeeContact> list = employees.getAllEmployees();
        for(EmployeeContact person: list){
            personId.add(person.getId().toString());
            filters.add(person.department);
            employeeList.add(person.firstName + " " + person.lastName);
            position.add(person.position);
        }

        filters = removeDuplicates(filters);

        spinner.setAdapter(adapter);
        listView.setAdapter(listAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EmployeeContact employ = new EmployeeContact();
                if(i != 0){
                    String selected = filters.get(i);

                    List<EmployeeContact> getDepartmentList = employ.getContactsByDepartment(selected);
                    employeeList.clear();
                    position.clear();
                    employeeList.add("Contact");
                    position.add("Position");
                    for(EmployeeContact person : getDepartmentList){

                        employeeList.add(person.firstName + " " + person.lastName);
                        position.add(person.position);
                        listAdapter.notifyDataSetChanged();
                    }
                }
                else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
