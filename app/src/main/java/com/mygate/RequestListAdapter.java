package com.mygate;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

public class RequestListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<RequestsView> Items;


	public RequestListAdapter(Activity activity, List<RequestsView> Items) {
		this.activity = activity;
		this.Items = Items;
	}

	@Override
	public int getCount() {
		return Items.size();
	}

	@Override
	public Object getItem(int location) {
		return Items.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.request_list, null);



		TextView uid = (TextView) convertView.findViewById(R.id.uid);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView contact = (TextView) convertView.findViewById(R.id.mno);
		TextView email = (TextView) convertView.findViewById(R.id.email);

		RequestsView m = Items.get(position);

		uid.setText( m.getUserid() );
		name.setText(m.getName());
		contact.setText(m.getContact());
		email.setText(m.getEmail());


		return convertView;
	}

}