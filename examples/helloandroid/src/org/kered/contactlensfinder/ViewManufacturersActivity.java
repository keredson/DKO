package org.kered.contactlensfinder;

import static org.kered.dko.SQLFunction.LOWER;

import java.util.List;

import org.kered.contactlensfinder.dko.Manufacturer;
import org.kered.dko.Query;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewManufacturersActivity extends ActionBarActivity {

	private ListView listView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_manufacturers);

		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		listView = (ListView) findViewById(R.id.view_manufacturers);

		// Define the query (note the case-insensitive order-by using the SQL LOWER() function).
		Query<Manufacturer> query = Manufacturer.ALL.orderBy(LOWER(Manufacturer.NAME));
		
		// The method asList() is a terminal operation (it doesn't return another query), 
		// so this is where the database is actually called.  Note that Query objects 
		// are Iterable, so if you're in a loop the asList() is unnecessary (in fact 
		// it's counter-productive, as DKOs will steam when used as an Iterable).
		final List<String> names = query.asList(Manufacturer.NAME);

		// The above calls are split out to highlight the types.  However it 
		// would be more common to chain them all together as such:
		
		//final String[] names = Manufacturer.ALL
		//		.orderBy(LOWER(Manufacturer.NAME))
		//		.asList(Manufacturer.NAME);

		final int resource = R.layout.basic_list_item;

		// Set the adapter for the list view
		listView.setAdapter(new ArrayAdapter<String>(this, resource, names));
		// Set the list's click listener
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent k = new Intent(ViewManufacturersActivity.this, ViewProductsActivity.class);
				String name = names.get(arg2);
				
				// Looks up the manufacturer.  If more than one record is returned, get()
				// will throw a RuntimeException.
				Manufacturer m = Manufacturer.ALL.get(Manufacturer.NAME.eq(name));
				
				k.putExtra("manufacturer_id", m.getId());
				startActivity(k);
			}
		});
	}

}
