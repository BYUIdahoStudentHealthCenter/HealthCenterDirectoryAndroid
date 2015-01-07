package com.example.jakobhartman.healthcenterdirectory;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import localDatabase.Pictures;

/**
 * Created by chad on 11/13/14.
 */
public class employeeDetails extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);



        ArrayList<String> description = new ArrayList<String>();
        ArrayList<String> details = new ArrayList<String>();
        ListView list = (ListView) findViewById(R.id.listView);
        ImageView profilePic = (ImageView) findViewById(R.id.imageView);
        TextView label = (TextView) findViewById(R.id.textView2);

        Pictures picturedb = new Pictures();


        Intent intent = getIntent(); //Gets the extra data passed in the intent
        String fullname = intent.getStringExtra("first_name") + " "+ intent.getStringExtra("last_name");


        Pictures pic = picturedb.getProfilePic(fullname);

        if (pic != null) {
            Log.i("Picture string", pic.image); //test to see what pic is
            try {
                byte[] decodedString = Base64.decode(pic.image, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                profilePic.setImageBitmap(decodedByte);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        label.setText(fullname);
       // description.add("Name");
        description.add("Department");
        description.add("Department Email");
        description.add("Department Number");
        description.add("Personal Email");
        description.add("Phone Number");
        description.add("Position");
        description.add("Status");
        description.add("Tier");

        //details.add(fullname);
        details.add(intent.getStringExtra("department"));
        details.add(intent.getStringExtra("department_email"));
        details.add(intent.getStringExtra("department_number"));
        details.add(intent.getStringExtra("personal_email"));
        details.add(intent.getStringExtra("phone_number"));
        details.add(intent.getStringExtra("position"));
        details.add(intent.getStringExtra("status"));
        details.add(intent.getStringExtra("tier"));

        customListViewAdapter customList = new customListViewAdapter(this,description,details);

        list.setAdapter(customList);

        final Intent call = new Intent(Intent.ACTION_DIAL);
        call.setData(Uri.parse("tel:"+details.get(4)));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               if (position == 4) {
                   try {
                       startActivity(call);
                       finish();
                   } catch (ActivityNotFoundException ex) {
                       Toast.makeText(employeeDetails.this,"Call Failed",Toast.LENGTH_SHORT).show();
                       System.out.print(ex.getMessage());
                   }
               }
            }
        });
    }

    @Override
    public Intent  getParentActivityIntent()  {
        Intent parentIntent = getIntent();
        String className = parentIntent.getStringExtra("ParentClassName");

        Intent newIntent = null;
        try {
            newIntent = new Intent(employeeDetails.this,Class.forName("com.example.jakobhartman.healthcenterdirectory."+className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return newIntent;
    }
}
