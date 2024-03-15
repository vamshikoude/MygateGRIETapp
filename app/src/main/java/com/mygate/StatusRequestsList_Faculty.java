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

public class StatusRequestsList_Faculty extends AppCompatActivity implements AdapterView.OnItemClickListener {

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
	private static String url_stsreq ="https://ctcorphyd.com/MyGate/status_requests_list2.php";

	private static String url_aceptrequest ="https://ctcorphyd.com/MyGate/fact_accept_request.php";

	private static String url_rejectrequest ="https://ctcorphyd.com/MyGate/fact_reject_request.php";
	private static final String TAG_SUCCESS = "success";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_requests);
		tv=(TextView)findViewById(R.id.textView);
		tv.setVisibility( View.GONE);
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);




		setTitle("Student Requests");

		new studentreq_php().execute();
			}


	class studentreq_php extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog( StatusRequestsList_Faculty.this);
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
				adapter = new StatusRequestListAdapter( StatusRequestsList_Faculty.this, cList );
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

		AlertDialog.Builder alertDialog = new AlertDialog.Builder( StatusRequestsList_Faculty.this);
		// Setting Dialog Title

		// Setting Dialog Message
		alertDialog.setMessage("Do you want accept this Request?");
		alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				new AcceptRequest().execute(  );

			}
		});
		// Setting Negative "NO" Button
		alertDialog.setNegativeButton("Reject", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				new RejectRequest().execute(  );
			}
		});
		alertDialog.show();





	}

	class AcceptRequest extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog( StatusRequestsList_Faculty.this);
			pDialog.setMessage("Processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("reqid",reqid));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = server2.makeHttpRequest(url_aceptrequest,
					"POST", params);

			// check log cat for response
			Log.d("Response for Register=", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");


				if (success == 1) {

					st=1;

				} else {
					st=2;
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
			if(st==1) {

				Toast.makeText(getApplicationContext(), "Accepted,Student Request ..!", Toast.LENGTH_SHORT).show();
				Intent i = new Intent( StatusRequestsList_Faculty.this, StatusRequestsList_Faculty.class);
				startActivity(i);
				finish();
			}

			pDialog.dismiss();

		}

	}



	class RejectRequest extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog( StatusRequestsList_Faculty.this);
			pDialog.setMessage("Processing...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("reqid",reqid));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = server2.makeHttpRequest(url_rejectrequest,
					"POST", params);

			// check log cat for response
			Log.d("Response for Register=", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");


				if (success == 1) {

					st=1;

				} else {
					st=2;
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
			if(st==1) {

				Toast.makeText(getApplicationContext(), "Rejected,Student Request ..!", Toast.LENGTH_SHORT).show();
				Intent i = new Intent( StatusRequestsList_Faculty.this, StatusRequestsList_Faculty.class);
				startActivity(i);
				finish();
			}

			pDialog.dismiss();

		}

	}


	/**
	 * Launching the drop-down menu bar for
	 * displaying the  view cart and logout menuitems.
	 * @param menu
	 * @return
	 */






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
