package com.example.jakobhartman.healthcenterdirectory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Calendar;
import java.util.GregorianCalendar;

import localDatabase.DepartmentContact;
import localDatabase.EmployeeContact;
import localDatabase.Pictures;
import localDatabase.Tier;
import localDatabase.loginInfo;


public class settings extends Activity {
    int i= 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Firebase.setAndroidContext(this); // initialize firebase

    }

    public void syncWithDatabase(){
        Firebase ref = new Firebase("https://boiling-fire-7455.firebaseio.com/CenterNumbers");
        Firebase employees = new Firebase("https://boiling-fire-7455.firebaseio.com/Employee");
        Firebase tiers = new Firebase ("https://boiling-fire-7455.firebaseio.com/Tier");
        Firebase picRef = new Firebase ("https://boiling-fire-7455.firebaseio.com/Images");
        new Delete().from(DepartmentContact.class).execute();
        new Delete().from(EmployeeContact.class).execute();
        new Delete().from(Tier.class).execute();
        new Delete().from(Pictures.class).execute();

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
                        newContact.imageName = "none";//data.child("Image_Name").getValue().toString();
                        newContact.lastName = data.child("Last_Name").getValue().toString();
                        newContact.personalEmail = data.child("Personal_Email").getValue().toString();
                        newContact.phoneNumber = data.child("Personal_Number").getValue().toString();
                        newContact.position = data.child("Position").getValue().toString();
                        newContact.status = data.child("Status").getValue().toString();
                        newContact.tier = data.child("Tier").getValue().toString();
                        newContact.save();
                        i++;
                        Log.i("Employee ", newContact.firstName);
                    }
                    ActiveAndroid.setTransactionSuccessful();
                    TextView textView =(TextView) findViewById(R.id.textView);
                    textView.setText("Found " + i + " Contacts");
                }
                catch (Exception e)
                {
                    System.out.println("There was an error saving contacts to local database");
                }
                finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        picRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ActiveAndroid.beginTransaction();
                try {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Pictures pictures = new Pictures();
                        pictures.name = data.child("Name").getValue().toString();
                        pictures.image = data.child("imageData").getValue().toString();
                        pictures.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                }
                catch (Exception e) {
                    System.out.println("There was an error saving pictures to local database");
                }
                finally {
                    ActiveAndroid.endTransaction();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        tiers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                /*********************************
                 *  Add statements to insert to Database
                 ***********************************/

                ActiveAndroid.beginTransaction();
                try {
                    DataSnapshot start = dataSnapshot.child("1");
                    Tier myTier = new Tier();
                    myTier.parent = "";
                    myTier.child = start.getName();

                    String parent = "1";
                    String child = "1";
                    myTier.save();

                    for(DataSnapshot tier2 : start.getChildren()){
                        Tier tierTable1 = new Tier();
                        child = tier2.getName();
                        //System.out.println("Parent = "+parent+"Child = "+child);
                        tierTable1.parent = parent.toString();
                        tierTable1.child = child.toString();

                        parent = child;
                        for(DataSnapshot tier3 : tier2.getChildren()){
                            Tier tierTable2 = new Tier();
                            child = tier3.getName();
                            //System.out.println("Parent = "+parent+"Child = "+child);
                            tierTable2.parent = parent.toString();
                            tierTable2.child = child.toString();

                            parent = child;
                            for(DataSnapshot tier4 : tier3.getChildren()){
                                Tier tierTable3 = new Tier();
                                child = tier4.getName();
                                //System.out.println("Parent = "+parent+"Child = "+child);
                                tierTable3.parent = parent.toString();
                                tierTable3.child = child.toString();

                                parent = child;
                                if (tier4.hasChildren())
                                {
                                    parent = child;
                                    for(DataSnapshot tier5 : tier4.getChildren()){
                                        Tier tierTable4 = new Tier();
                                        child = tier5.getName();
                                        //System.out.println("Parent = "+parent+"Child = "+child);
                                        tierTable4.parent = parent.toString();
                                        tierTable4.child = child.toString();

                                        parent = child;
                                        if (tier5.hasChildren())
                                        {
                                            for(DataSnapshot tier6 : tier5.getChildren()){
                                                Tier tierTable5 = new Tier();
                                                child = tier6.getName();
                                                //System.out.println("Parent = "+parent+"Child = "+child);
                                                tierTable5.parent = parent.toString();
                                                tierTable5.child = child.toString();

                                                parent = child;
                                                if (tier6.hasChildren())
                                                {
                                                    for (DataSnapshot tier7:tier6.getChildren())
                                                    {
                                                        Tier tierTable6 = new Tier();
                                                        child = tier7.getName();
                                                        //System.out.println("Parent = "+parent+"Child = "+child);
                                                        tierTable6.parent = parent.toString();
                                                        tierTable6.child = child.toString();

                                                        parent = child;
                                                        tierTable6.save();
                                                    }
                                                    parent = tier5.getName();
                                                }
                                                tierTable5.save();
                                            }
                                            parent = tier4.getName();
                                        }
                                        tierTable4.save();
                                    }
                                    parent = tier3.getName();
                                }
                                tierTable3.save();
                            }
                            parent = tier2.getName();
                            tierTable2.save();
                        }
                        parent = "1";
                        tierTable1.save();
                    }

                    ActiveAndroid.setTransactionSuccessful();
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
                new Delete().from(loginInfo.class).execute();
                loginInfo storeUsername = new loginInfo();
                storeUsername.username = username;
                storeUsername.password = password;
                Calendar expDate = new GregorianCalendar();
                expDate.add(Calendar.DAY_OF_MONTH,30);
                storeUsername.lastLogIn = expDate;
                storeUsername.save();
                syncWithDatabase();
                //finish();

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

    @Override
    public Intent  getParentActivityIntent()  {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName");

        Intent newIntent = null;
        try {
            newIntent = new Intent(settings.this,Class.forName("com.example.jakobhartman.healthcenterdirectory."+className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}