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
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import localDatabase.EmployeeContact;
import localDatabase.Tier;
import localDatabase.loginInfo;


public class emergencyList extends Activity {
    List<EmployeeContact> people = null;
    ArrayList<List<EmployeeContact>> mainList = new ArrayList<List<EmployeeContact>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);

        /************************************************************
         * Set up
         ***********************************************************/
        ListView listview = (ListView) findViewById(R.id.listView);
        ArrayList<String> toContact;
        ArrayAdapter<String> adapter;
        loginInfo storeUsername = new loginInfo();
        Tier tierTable = new Tier();
        EmployeeContact getDatabase = new EmployeeContact();

        /************************************************************
         * Get username and tier associated with it
         ***********************************************************/
        List<loginInfo> username = storeUsername.getUsername();
        System.out.println(username.get(0).username);
        List<EmployeeContact> parentTier = getDatabase.getTier(username.get(0).username);
        System.out.println(parentTier.get(0).tier.toString());
        String parentString = parentTier.get(0).tier.toString();

        /************************************************************
         * Get tiers to contact
         ***********************************************************/
        List<Tier> children = tierTable.getChild(parentString);
        System.out.println(children.size() + " entries in the database.");
        toContact = new ArrayList<String>();
        if (children.size() == 0)
        {
            toContact.add("You have no one you need to call.");
        }
        /************************************************************
         * Set to display contact name, then click to open up details
         ***********************************************************/

        for (Tier myTier : children) {
            people = getDatabase.getContactsByTier(myTier.child.toString());
            mainList.add(people);
            for (EmployeeContact person:people)
            {
                toContact.add(person.firstName + " " + person.lastName);
                System.out.println("Person: " + person.firstName + person.lastName);
            }
        }
        /************************************************************
         * Display the list
         ***********************************************************/
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1,toContact );
        listview.setAdapter(adapter);

        /************************************************************
         * Listener for clickable list
         ***********************************************************/
        final Intent intent = new Intent(this,employeeDetails.class);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.i("clicked on: ", ""+position);
                EmployeeContact details = mainList.get(position).get(0); // BUG, doesn't account for filtered searches
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.emergency_list, menu);
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
