package com.mygate;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class StatusRequestListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<RequestsView> Items;


	public StatusRequestListAdapter(Activity activity, List<RequestsView> Items) {
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
			convertView = inflater.inflate(R.layout.status_request_list, null);

		TextView uid = (TextView) convertView.findViewById(R.id.uid);
		TextView date = (TextView) convertView.findViewById(R.id.date);
		TextView fstatus = (TextView) convertView.findViewById(R.id.fstatus);
		TextView hstatus = (TextView) convertView.findViewById(R.id.hstatus);
		TextView reason = (TextView) convertView.findViewById(R.id.reason);

		RequestsView m = Items.get(position);

		uid.setText( m.getUserid() );
		date.setText(m.getDate());
		fstatus.setText(m.getFact_status());
		hstatus.setText(m.getHod_status());
		reason.setText( m.getReason() );


		return convertView;
	}

}