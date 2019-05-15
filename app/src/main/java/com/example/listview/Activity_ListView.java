package com.example.listview;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class Activity_ListView extends AppCompatActivity {

	private ListView my_listview;
	private ImageView refresh;
	private ArrayAdapter<String> myAdapter;
	private WebImageView_KP Bike;
	private SharedPreferences.OnSharedPreferenceChangeListener listener = null;
	private SharedPreferences myPreference;
	private List<BikeData> myList = new ArrayList<BikeData>();
	private String urlMain = "http://www.pcs.cnu.edu/~kperkins/bikes/bikes.json";
	Spinner spinner;
	CustomAdapter myCustomAdapter;
	private final String PICTURES_BIKES ="http://www.pcs.cnu.edu/~kperkins/bikes/";
	private final String PICTURES_BIKES_2 ="http://www.tetonsoftware.com/bikes/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//listview that you will operate on
		Bike = (WebImageView_KP)findViewById((R.id.imageView1));
		my_listview = (ListView)findViewById(R.id.lv);
		refresh = (ImageView)findViewById(R.id.refresh);
		myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		//toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();
		setupSimpleSpinner();
		//TODO call a thread to get the JSON list of bikes
		//TODO when it returns it should process this data with bindData
		refresh(Activity_ListView.this.getCurrentFocus());
		//set the listview onclick listener
		setupListViewOnClickListener();
	}




	private void setupListViewOnClickListener() {
		//TODO you want to call my_listviews setOnItemClickListener with a new instance of android.widget.AdapterView.OnItemClickListener() {
		my_listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_ListView.this);
                builder.setMessage(myList.get(position).toString());
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
			}

		});
	}

	/**
	 * Takes the string of bikes, parses it using JSONHelper
	 * Sets the adapter with this list using a custom row layout and an instance of the CustomAdapter
	 * binds the adapter to the Listview using setAdapter
	 *
	 * @param JSONString  complete string of all bikes
	 */
	private void bindData(String JSONString) {
		myList = JSONHelper.parseAll(JSONString);
		myCustomAdapter = new CustomAdapter(this,myList);
		my_listview.setAdapter(myCustomAdapter);
	}
	public void onPost(String JSONString){
	    bindData(JSONString);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				if (ConnectivityCheck.isNetworkReachable(getApplicationContext())) {
					myCustomAdapter.sortList(pos);
					my_listview.setAdapter(myCustomAdapter);
				} else {
					setContentView(R.layout.no_network);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	}


	/**
	 * create a data adapter to fill above spinner with choices(Company,Location and Price),
	 * bind it to the spinner
	 * Also create a OnItemSelectedListener for this spinner so
	 * when a user clicks the spinner the list of bikes is resorted according to selection
	 * dontforget to bind the listener to the spinner with setOnItemSelectedListener!
	 */
	private void setupSimpleSpinner() {
		spinner = (Spinner) findViewById((R.id.spinner));
		myAdapter = new ArrayAdapter<String>(this,R.layout.spinner_item,
				getResources().getStringArray(R.array.sortable_fields));
		spinner.setAdapter(myAdapter);
	}
	public void setup(){
		//listview that you will operate on
		my_listview = (ListView)findViewById(R.id.lv);
		refresh = (ImageView)findViewById(R.id.refresh);
		myPreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		//toolbar
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		android.support.v7.app.ActionBar actionBar = getSupportActionBar();

		setupSimpleSpinner();

		//set the listview onclick listener
		setupListViewOnClickListener();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				View view = getCurrentFocus();
				showPreferenceActivity(view);
				break;


		default:
			break;
		}
		return true;
	}
	public void showPreferenceActivity(View view) {
		//start the pref activity with the embedded pref fragment
		Intent myIntent = new Intent(this, activityPreference.class);
		startActivity(myIntent);
		setPreferenceChangeListener(view);
	}

	public void setPreferenceChangeListener(View view) {
		//if not created yet then do so
		if (listener == null) {
			listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
				@Override
				public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

					switch(key){
						case "info":

							ArrayList<String> names = new ArrayList<String>();
							Toast.makeText(getApplication(), "Handling which data to be loaded", Toast.LENGTH_SHORT).show();

							myPreference.getString(key,"");
							myPreference.registerOnSharedPreferenceChangeListener(listener);
							urlMain = myPreference.getString(key,"");

							myAdapter.notifyDataSetChanged();
							try {
								refresh(Activity_ListView.this.getCurrentFocus());
							} catch (Exception e) {
								e.printStackTrace();
							}
							break;
						default:
							Toast.makeText(Activity_ListView.this, "Yikes, handle this unknown key", Toast.LENGTH_SHORT).show();
					}
				}
			};
		}
		myPreference.registerOnSharedPreferenceChangeListener(listener);
	}
	public String  getUrl (){
		if(urlMain.equals(getResources().getString(R.string.pcsURL))){
			return PICTURES_BIKES;
		}
		else{
			return PICTURES_BIKES_2;
		}
	}
	public void refresh(View view){
		if(ConnectivityCheck.isNetworkReachable(getApplicationContext())) {
			DownloadTask process = new DownloadTask(this);
			process.execute(urlMain);
		}
		else
		{
			setContentView(R.layout.no_network);
		}
	}
}
