package com.mygate;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatusRequestsList extends AppCompatActivity implements AdapterView.OnItemClickListener {

	private ProgressDialog pDialog;
	String reqid;
	private List<RequestsView> cList = new ArrayList<RequestsView>();
	private ListView listView;
	PostServer server = new PostServer();
	PostServer server2 = new PostServer();
	JSONArray json1 = null;
	private StatusRequestListAdapter adapter;
	String unm;

	TextView tv;
	boolean sts=false;
	int st;
	private static String url_stsreq ="https://ctcorphyd.com/MyGate/status_requests_list.php";

	private static String url_aceptrequest ="https://ctcorphyd.com/MyGate/accept_request2.php";

	private static String url_rejectrequest ="https://ctcorphyd.com/MyGate/reject_request2.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_requests);
		tv=(TextView)findViewById(R.id.textView);
		tv.setVisibility( View.GONE);
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);

		Intent intent=getIntent();
		Bundle b=intent.getExtras();
		if(b!=null) {

			unm = b.getString("unm");
		}


		setTitle("Requests Status");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		new registerreq_php().execute();
			}


	class registerreq_php extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog( StatusRequestsList.this);
			pDialog.setMessage("Loading ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting Inbox JSON
		 */
		protected String doInBackground(String... args) {
			// Building Parameters
			String s = null;
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("unm",unm));

			// getting JSON string from URL
			JSONObject json = server.makeHttpRequest(url_stsreq, "POST", params);

			// Check your log cat for JSON reponse
			Log.d("JSON: ", json.toString());

			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					sts=true;
					cList.clear();
					json1 = json.getJSONArray( "details" );
					// looping through All messages
					for (int i = 0; i < json1.length(); i++) {
						JSONObject c = json1.getJSONObject( i );
						// Storing each json item in variable
						RequestsView sv = new RequestsView();
						sv.setReqid(c.getString( "req_id"));
						sv.setUserid("Roll number: "+c.getString( "userid" ) );
						sv.setDate("Date: "+c.getString( "date" ) );
						sv.setFact_status("Faculty_status: "+ c.getString( "faculty_status" ) );
						sv.setHod_status("Hod_status: "+c.getString( "hod_status" ) );
						sv.setReason( "Reason: "+ c.getString( "reason" ));

						cList.add( sv );
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 **/
		protected void onPostExecute(String file_url) {
			if(sts) {
				adapter = new StatusRequestListAdapter( StatusRequestsList.this, cList );
				listView.setAdapter( adapter );
			}

else{
				tv.setVisibility(View.VISIBLE);
				tv.setText("No Records Found");

			}
			pDialog.dismiss();
		}
	}




	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {

		RequestsView sv=(RequestsView)cList.get(position);

		reqid=sv.getReqid();
		String rollno=sv.getUserid();
		String date=sv.getDate();

		String fact_sts=sv.getFact_status().split( "Faculty_status: " )[1];

		String hod_sts=sv.getHod_status().split( "Hod_status: " )[1];

		if(fact_sts.equals( "accepted" )&& hod_sts.equals( "accepted" )){


			Intent i = new Intent( StatusRequestsList.this, GatePass.class );

			i.putExtra( "rno",rollno );
			i.putExtra( "date",date );

			startActivity( i );

		}else{

			Toast.makeText( this,"Not Accepted" ,Toast.LENGTH_LONG).show();

		}







	}







	/**
	 * Launching the drop-down menu bar for
	 * displaying the  view cart and logout menuitems.
	 * @param menu
	 * @return
	 */





	/**
	 * Executing the specific operations based on chosen of particular switch case
	 * @param item
	 * @return
	 */

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
