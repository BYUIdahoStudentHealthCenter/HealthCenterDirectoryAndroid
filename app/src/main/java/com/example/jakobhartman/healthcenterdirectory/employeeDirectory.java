package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                EmployeeContact employ = new EmployeeContact();
                if(i != 0){
                    String selected = filters.get(i);

                    //Results displayed based on selected category
                    List<EmployeeContact> getDepartmentList = employ.getContactsByDepartment(selected);
                    employeeList.clear();
                    position.clear();
                    mainList.clear(); //NEW
                    employeeList.add("Contact");
                    position.add("Position");

                    for(EmployeeContact person : getDepartmentList){
                        employeeList.add(person.firstName + " " + person.lastName);
                        position.add(person.position);
                        mainList.add(person); //NEW
                        listAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    //Else, if they select "Department" it will display everyone
                    List<EmployeeContact> getDepartmentList = employ.getAllEmployees();
                    employeeList.clear();
                    position.clear();
                    mainList.clear(); //NEW
                    employeeList.add("Contact");
                    position.add("Position");

                    for(EmployeeContact person : getDepartmentList){
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
                Toast.makeText(employeeDirectory.this,textSearch,Toast.LENGTH_SHORT).show();
                EmployeeContact employ = new EmployeeContact();
                List<EmployeeContact> getDepartmentList = employ.getAllEmployees();

                employeeList.clear();
                position.clear();
                mainList.clear(); //NEW
                employeeList.add("Contact");
                position.add("Position");

                for(EmployeeContact person : getDepartmentList){

                    if (person.firstName.toLowerCase().startsWith(textSearch))
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
                EmployeeContact details = mainList.get(position-1); //doesn't account for filtered searches
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
