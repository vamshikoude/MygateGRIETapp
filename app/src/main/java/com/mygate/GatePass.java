package com.mygate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GatePass extends AppCompatActivity {

    TextView rno, date;
    String rno1, date1;
    private ProgressDialog pDialog;

    boolean sts;
    private static final String TAG_SUCCESS = "success";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.gate_pass );


        setTitle("Gate Pass");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rno = (TextView) findViewById( R.id.rno );

        date = (TextView) findViewById( R.id.date );

        Intent intent=getIntent();
        Bundle b=intent.getExtras();
        if(b!=null) {

            rno1 = b.getString("rno");
            date1 = b.getString("date");
        }
        rno.setText( rno1 );

        date.setText( date1 );


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();


            default:
                break;

        }
        return true;
    }



    }