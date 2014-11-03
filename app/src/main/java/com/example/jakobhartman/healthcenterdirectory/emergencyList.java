package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import localDatabase.DepartmentContact;
import localDatabase.EmployeeContact;
import localDatabase.loginInfo;


public class emergencyList extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_list);

        loginInfo storeUsername = new loginInfo();
        long i = 1;
        String username = storeUsername.load(loginInfo.class,i).username;

        // Get the contact's tier
        EmployeeContact getDatabase = new EmployeeContact();
        String tier = getDatabase.getTier(username).toString();

        // Search for tier in Tier table, get children and grandchildren.







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
