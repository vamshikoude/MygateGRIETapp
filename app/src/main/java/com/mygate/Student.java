package com.mygate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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


public class Student extends AppCompatActivity {
    TextView reg;
    EditText unm, pwd;
    String unm1, pwd1;
    private ProgressDialog pDialog;

    boolean sts;
    private static final String TAG_SUCCESS = "success";


    PostServer server = new PostServer();

    private static String url_login = "https://ctcorphyd.com/MyGate/student_login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.student );

        unm = (EditText) findViewById( R.id.username );
        pwd = (EditText) findViewById( R.id.password );


        reg = (TextView) findViewById( R.id.signupText );

        reg.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Student.this, Student_Registration.class );

                startActivity( i );

            }
        } );

    }

    public void logincheck(View view) {

        unm1 = unm.getText().toString();
        pwd1 = pwd.getText().toString();

        if (null == unm1 || unm1.trim().length() == 0) {
            unm.setError( "Enter Roll number" );
            unm.requestFocus();
        } else if (null == pwd1 || pwd1.trim().length() == 0) {
            pwd.setError( "Enter Password" );
            pwd.requestFocus();
        } else {
            new UserLogin().execute();

            return;
        }

    }



    class UserLogin extends AsyncTask<String, String, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Student.this);
            pDialog.setMessage("Login Verification...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("unm",unm1));
            params.add(new BasicNameValuePair("pwd",pwd1));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = server.makeHttpRequest(url_login,"POST",params);

            // check log cat for response
            Log.d("Result for Login", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product

                   Intent i=new Intent(Student.this,Student_Dashboard.class);
                    i.putExtra("unm",unm1);
                    startActivity(i);
                    finish();
                    sts=true;
                } else {
                    sts=false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }


        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done

            if(!sts){
                Toast.makeText(Student.this,"Invalid Credentials or HOD not yet Accepted.",Toast.LENGTH_SHORT).show();
            }
            pDialog.dismiss();
        }

    }

    }