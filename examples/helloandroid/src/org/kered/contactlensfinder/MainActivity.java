package org.kered.contactlensfinder;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/*
 * Two DKO-related actions happen here:
 * 
 * 1) DB.getReady(getApplicationContext()) in onCreate()

 * Since Android apps have to handle their own DB setups, we call DB.getReady()
 * handle that setup (copying a sqlite3 file out of assets/).  It also calls
 * DKO's Context.setDataSource(), which controls which database queries are 
 * routed to.
 * 
 * 2) System.gc() in onDestroy()

 * DKO writes out column usage information after all the objects created by the library
 * are GCed.  If an app exits before garbage collection has happened, a shutdown hook
 * handles the outstanding query usage monitors.  However: Android doesn't reliably
 * run shutdown hooks, so small apps like this can literally never see usage stats being
 * persisted.  Calling System.gc() when you main activity is destroyed fixes that.
 * 
 */
public class MainActivity extends ActionBarActivity {

	protected static final String TAG = "MainActivity";
	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mTitle;
    private CharSequence mDrawerTitle;
	private ListView mDrawerList;
	private String[] drawerOptions = {"Search","Manufacturers","Products","Properties"};


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

		DB.getReady(getApplicationContext());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
            	getSupportActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, drawerOptions ));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (arg2==1) {
					Intent k = new Intent(MainActivity.this, ViewManufacturersActivity.class);
					startActivity(k);
				}
				if (arg2==2) {
					Intent k = new Intent(MainActivity.this, ViewProductsActivity.class);
					startActivity(k);
				}
				if (arg2==3) {
					Intent k = new Intent(MainActivity.this, ViewPropertiesActivity.class);
					startActivity(k);
				}
			}});

    }

    
	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
	

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// help DKO query optimizations get persisted, since System shutdown 
		// hooks are unreliable on android.
		System.gc();
		Log.v(TAG, "destroyed!");
	}

}
