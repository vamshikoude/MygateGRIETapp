package com.mygate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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

public class Student_Dashboard extends AppCompatActivity {


        CardView sreq,viewsts;
        Intent i1,i2,i3,i4;

    TextInputEditText reason;

    String resn;
    int sts=0;

    private ProgressDialog pDialog;
    PostServer parser = new PostServer();

    private static String url_sendreq = "https://ctcorphyd.com/MyGate/reasons.php";
    String unm;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.student_home);
            setTitle( "Student Dashboard" );

            Intent intent=getIntent();
            Bundle b=intent.getExtras();
            if(b!=null) {

                unm = b.getString("unm");
            }



            sreq= (CardView) findViewById(R.id.sreq);
            i1 = new Intent(this,Faculty_RequestsList.class);
            sreq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Student_Dashboard.this);
                    builder.setTitle("Reason");

                    // set the custom layout
                    final View customLayout = getLayoutInflater().inflate(R.layout.reasons, null);
                    builder.setView(customLayout);

                    // add a button
                    builder.setPositiveButton("OK", (dialog, which) -> {
                        // send data from the AlertDialog to the Activity
                        reason = customLayout.findViewById(R.id.reason);
                        resn=reason.getText().toString();

                        if (null == resn || resn.trim().length() == 0) {
                            Toast.makeText( Student_Dashboard.this,"Enter Reason",Toast.LENGTH_LONG ).show();
                        }else{

                            new sendrequest(  ).execute();

                        }


                    });
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            });


            viewsts= (CardView) findViewById(R.id.viewsts);
            i2 = new Intent(this,StatusRequestsList.class);
            viewsts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    i2.putExtra( "unm",unm );
                    startActivity(i2);
                }
            });


        }


    class sendrequest extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Student_Dashboard.this);
            pDialog.setMessage("Processing...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            // Building Parameters

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("reason",resn));
            params.add(new BasicNameValuePair("unm",unm));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = parser.makeHttpRequest(url_sendreq,
                    "POST", params);

            // check log cat for response
            Log.d("Response for Register=", json.toString());

            // check for success tag
            try {
                int success = json.getInt("success");


                if (success == 1) {

                    sts=1;
                } else {
                    sts=2;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if(sts==1) {

                Toast.makeText(getApplicationContext(), "Request Sent Successfully..!", Toast.LENGTH_SHORT).show();


            }

            pDialog.dismiss();
        }

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
