package org.kered.contactlensfinder;

import static org.kered.dko.SQLFunction.LOWER;

import java.util.List;

import org.kered.contactlensfinder.dko.Fact;
import org.kered.contactlensfinder.dko.Product;
import org.kered.dko.Query;
import org.kered.dko.datasource.SingleConnectionDataSource;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ViewProductsActivity extends ActionBarActivity {

	private static final String TAG = "ViewManufacturersActivity";
	private ListView listView;
	private SingleConnectionDataSource ds;
	private Context context;
	private LayoutInflater mInflater;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_manufacturers);
		Bundle extras = getIntent().getExtras();
		ActionBar supportActionBar = getSupportActionBar();
		supportActionBar.setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		context = getApplicationContext();
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		listView = (ListView) findViewById(R.id.view_manufacturers);

		// Here we set up the base query.  No SQL execution yet.
		Query<Product> query = Product.ALL
				.where(Product.NAME.neq(""))
				.orderBy(LOWER(Product.NAME));
		
		if (extras!=null && extras.containsKey("manufacturer_id")) {
			int manufacturerId = extras.getInt("manufacturer_id");
			// We want to show only products from a specific manufacturer, so add a where
			// clause here.  Notice this where is added after the orderBy() call above.
			// That's OK.  Notice also that this is the second call to where().  That's
			// OK too.  Multiple calls to where() are ANDed together.  Finally, notice
			// that we assign the query back to itself.  Queries are immutable, so every
			// where(), orderBy(), etc. always returns a new query.
			query = query.where(Product.MANUFACTURER_ID.eq(manufacturerId));
		}
		
		if (extras!=null && extras.containsKey("property_id")) {
			int propertyId = extras.getInt("property_id");
			// A Fact has a foreign key to a Product, represented by:
			// Fact.FK_PRODUCT_SCRAPER_PRODUCT  (generally: FK_{COLUMNNAME}_{TABLENAME})
			// We want to only show products that have a fact of a certain property type.
			query = query.with(Fact.FK_PRODUCT_SCRAPER_PRODUCT)
					.where(Fact.PROP_ID.eq(propertyId));
			// Calling with() joins the two tables on their FK relationship, and the
			// where() call filters.
		}
		
		// The query is executed here!
		final List<String> names = query.asList(Product.NAME);

		final int resource = R.layout.basic_list_item;

		// Set the adapter for the list view
		listView.setAdapter(new ArrayAdapter<String>(this, resource, names));
		// Set the list's click listener
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == 1) {
					Intent k = new Intent(ViewProductsActivity.this, ViewProductsActivity.class);
					startActivity(k);
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.options_menu, menu);

		return true;
	}

}
