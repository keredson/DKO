package org.kered.contactlensfinder;

import static org.kered.dko.SQLFunction.LOWER;

import java.util.ArrayList;
import java.util.List;

import org.kered.contactlensfinder.dko.Property;
import org.kered.dko.datasource.SingleConnectionDataSource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewPropertiesActivity extends ActionBarActivity {

	private static final String TAG = "ViewPropertiesActivity";
	private ListView listView;
	private SingleConnectionDataSource ds;
	private Context context;
	private LayoutInflater mInflater;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_manufacturers);
		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		context = getApplicationContext();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listView = (ListView) findViewById(R.id.view_manufacturers);

		final List<Property> properties = Property.ALL
				.where(Property.DISPLAY.isTrue())
				.orderBy(LOWER(Property.DISPLAY_NAME), LOWER(Property.NAME))
				.asList();

		final List<String> propertyNames = new ArrayList<String>();
		for (Property property : properties) {
			String displayName = property.getDisplayName();
			String toDisplay = displayName!=null && displayName.length()>0 
					? displayName : property.getName();
			propertyNames.add(toDisplay);
		}

		final int resource = R.layout.basic_list_item;

		// Set the adapter for the list view
		listView.setAdapter(new ArrayAdapter<String>(this, resource, propertyNames));
		// Set the list's click listener
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent k = new Intent(ViewPropertiesActivity.this, ViewProductsActivity.class);
				k.putExtra("property_id", properties.get(arg2).getId());
				startActivity(k);
			}
		});
	}

}
