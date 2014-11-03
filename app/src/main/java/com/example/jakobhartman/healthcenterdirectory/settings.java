package com.example.jakobhartman.healthcenterdirectory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import localDatabase.DepartmentContact;
import localDatabase.EmployeeContact;
import localDatabase.loginInfo;


public class settings extends Activity {
    int i= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Firebase.setAndroidContext(this); // initialize firebase

    }

    public void syncWithDatabase(){
        Firebase ref = new Firebase("https://boiling-fire-7455.firebaseio.com/CenterNumbers");
        Firebase employees = new Firebase("https://boiling-fire-7455.firebaseio.com/Employee");
        new Delete().from(DepartmentContact.class).execute();
        new Delete().from(EmployeeContact.class).execute();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ActiveAndroid.beginTransaction();
                try {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                        for (DataSnapshot contact : data.getChildren()){

                            DepartmentContact newContact = new DepartmentContact();
                            newContact.department = data.getName();
                            newContact.name = contact.child("Name").getValue().toString();
                            newContact.number = contact.child("Number").getValue().toString();
                            newContact.save();
                            i++;
                        }
                    }
                    ActiveAndroid.setTransactionSuccessful();
                    System.out.println(i);
                }
                finally {
                    ActiveAndroid.endTransaction();
                }
                TextView textView =(TextView) findViewById(R.id.textView);
                textView.setText("Found " + i + " Contacts");
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        employees.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ActiveAndroid.beginTransaction();
                try {
                    for(DataSnapshot data : dataSnapshot.getChildren()){
                            EmployeeContact newContact = new EmployeeContact();
                            newContact.department = data.child("Department").getValue().toString();
                            newContact.departmentEmail = data.child("Department_Email").getValue().toString();
                            newContact.departmentNumber = data.child("Department_Number").getValue().toString();
                            newContact.firstName = data.child("First_Name").getValue().toString();
                            newContact.imageName = data.child("Image_Name").getValue().toString();
                            newContact.lastName = data.child("Last_Name").getValue().toString();
                            newContact.personalEmail = data.child("Personal_Email").getValue().toString();
                            newContact.phoneNumber = data.child("Phone_Number").getValue().toString();
                            newContact.position = data.child("Position").getValue().toString();
                            newContact.status = data.child("Status").getValue().toString();
                            newContact.tier = data.child("Tier").getValue().toString();
                            newContact.save();
                            i++;
                    }
                    ActiveAndroid.setTransactionSuccessful();
                    TextView textView =(TextView) findViewById(R.id.textView);
                    textView.setText("Found " + i + " Contacts");
                }
                finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }
    int p = 0;
    public void loginAndSync(View v){
        EditText usernameText = (EditText) findViewById(R.id.editText1);
        final String username = usernameText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.editText2);
        final String password = passwordText.getText().toString();

      Firebase  ref = new Firebase("https://boiling-fire-7455.firebaseio.com");
                ref.authWithPassword(username,password,new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        syncWithDatabase();
                        loginInfo storeUsername = new loginInfo();
                        new Delete().from(loginInfo.class).execute();
                        storeUsername.username = username;
                        storeUsername.password = password;
                        storeUsername.save();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        TextView textView =(TextView) findViewById(R.id.textView);
                        p++;
                        if(p == 3){
                            new Delete().from(DepartmentContact.class).execute();
                            new Delete().from(EmployeeContact.class).execute();

                            textView.setText("Data Has Been Wiped - Please try again in 5 minutes");
                        }
                        int o = 3 - p;
                        if(o > 0){
                            textView.setText( firebaseError.getMessage() + " - " + o + " Attempts Left" );
                        }

                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,MainMenu.class);
        startActivity(intent);
    }
}
