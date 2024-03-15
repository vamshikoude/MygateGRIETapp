package com.mygate;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputEditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HOD_Dashboard extends AppCompatActivity {


        CardView aprvfact,aprvstdnt,students_req;
        Intent i1,i2,i3,i4;

    TextInputEditText query;

    String qtn;
    int sts=0;

    private ProgressDialog pDialog;
    PostServer parser = new PostServer();


    String unm;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.hod_home);
            setTitle( "HOD Dashboard" );

            aprvfact= (CardView) findViewById(R.id.aprvfact);
            i1 = new Intent(this,Faculty_RequestsList.class);
            aprvfact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(i1);
                }
            });


            aprvstdnt= (CardView) findViewById(R.id.aprvstdnt);
            i2 = new Intent(this,Student_RequestsList.class);
            aprvstdnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(i2);
                }
            });

            students_req= (CardView) findViewById(R.id.students_req);
            i3 = new Intent(this,StatusRequestsList_HOD.class);
            students_req.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(i3);
                }
            });



        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Executing the specific operations based on chosen of particular switch case
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;

            default:
                break;

        }


        return true;
    }




}
