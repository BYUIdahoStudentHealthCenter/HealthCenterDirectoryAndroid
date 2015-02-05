package com.example.jakobhartman.healthcenterdirectory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import localDatabase.EmployeeContact;


public class employeeDirectory extends Activity {
    Spinner spinner;
    ListView listView;
    Button searchButton;
    EditText searchText;
    customListViewAdapter listAdapter;
    ArrayList<String> filters;
    ArrayList<String> employeeList;
    ArrayList<String> position;
    ArrayList<String> personId;
    ArrayList<EmployeeContact> mainList = new ArrayList<EmployeeContact>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_directory);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        filters = new ArrayList<String>();
        employeeList = new ArrayList<String>();
        position = new ArrayList<String>();
        personId = new ArrayList<String>();

        spinner = (Spinner) findViewById(R.id.spinner);
        listView = (ListView) findViewById(R.id.listView);
        searchButton = (Button) findViewById(R.id.button2);
        searchText = (EditText) findViewById(R.id.editText);

        filters.add("All");
        employeeList.add("Contact");
        position.add("Position");

        listAdapter = new customListViewAdapter(this,employeeList,position);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, filters);


        EmployeeContact employees = new EmployeeContact();
        List<EmployeeContact> list = employees.getAllEmployees();
        for(EmployeeContact person: list){
            personId.add(person.getId().toString());

            //Populate the different departments
            filters.add(person.department);
            employeeList.add(person.firstName + " " + person.lastName);
            position.add(person.position);
            mainList.add(person);
        }

        filters = removeDuplicates(filters);

        spinner.setAdapter(adapter);
        listView.setAdapter(listAdapter);
        final Intent intent = new Intent(this,employeeDetails.class);
        intent.putExtra("ParentClassName","employeeDirectory");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EmployeeContact employ = new EmployeeContact();
                if (i != 0) {
                    String selected = filters.get(i);

                    //Results displayed based on selected category
                    List<EmployeeContact> getDepartmentList = employ.getContactsByDepartment(selected);
                    employeeList.clear();
                    position.clear();
                    mainList.clear(); //NEW
                    employeeList.add("Contact");
                    position.add("Position");

                    for (EmployeeContact person : getDepartmentList) {
                        employeeList.add(person.firstName + " " + person.lastName);
                        position.add(person.position);
                        mainList.add(person); //NEW
                        listAdapter.notifyDataSetChanged();
                    }
                } else {
                    //Else, if they select "Department" it will display everyone
                    List<EmployeeContact> getDepartmentList = employ.getAllEmployees();
                    employeeList.clear();
                    position.clear();
                    mainList.clear(); //NEW
                    employeeList.add("Contact");
                    position.add("Position");

                    for (EmployeeContact person : getDepartmentList) {
                        employeeList.add(person.firstName + " " + person.lastName);
                        position.add(person.position);
                        mainList.add(person); //NEW
                        listAdapter.notifyDataSetChanged();
                    }

                }

                //When someone selects a department, clear the text
                searchText.setText("");
                searchText.clearFocus();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //When the button is clicked, search by name
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String textSearch = searchText.getText().toString().toLowerCase();
                searchText.clearFocus();
                // Hide the onscreen keyboard when the search button is pressed
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchText.getWindowToken(), 0);

                Toast.makeText(employeeDirectory.this,searchText.getText().toString(),Toast.LENGTH_SHORT).show();
                EmployeeContact employ = new EmployeeContact();
                List<EmployeeContact> getDepartmentList = employ.getAllEmployees();

                employeeList.clear();
                position.clear();
                mainList.clear(); //NEW
                employeeList.add("Contact");
                position.add("Position");

                for(EmployeeContact person : getDepartmentList){
                    //Searches for letters in firstname
                    if (person.firstName.toLowerCase().startsWith(textSearch) || person.lastName.toLowerCase().startsWith(textSearch))
                    {
                        employeeList.add(person.firstName + " " + person.lastName);
                        position.add(person.position);
                        mainList.add(person); //NEW

                    }
                    listAdapter.notifyDataSetChanged();

                }

            }

        });

        //Opens new activity when name is clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position != 0) {
                    EmployeeContact details = mainList.get(position - 1);
                    intent.putExtra("department", details.department);
                    intent.putExtra("department_email", details.departmentEmail);
                    intent.putExtra("department_number", details.departmentNumber);
                    intent.putExtra("first_name", details.firstName);
                    intent.putExtra("last_name", details.lastName);
                    intent.putExtra("personal_email", details.personalEmail);
                    intent.putExtra("phone_number", details.phoneNumber);
                    intent.putExtra("position", details.position);
                    intent.putExtra("status", details.status);
                    intent.putExtra("tier", details.tier);

                    startActivity(intent);
                }
            }
        });
    }

    public ArrayList<String> removeDuplicates(ArrayList<String> l) {
        Set setItems = new LinkedHashSet(l);
        l.clear();
        l.addAll(setItems);
        return l;
    }


}
