package carezoneshop.bigyan.com.carezoneshopping.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carezoneshop.bigyan.com.carezoneshopping.R;
import carezoneshop.bigyan.com.carezoneshopping.adapter.ExpandableListAdapter;
import carezoneshop.bigyan.com.carezoneshopping.fragments.AddItem;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.content_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.myFAB)
     FloatingActionButton myFab;

    boolean isLoading = true;

    Fragment fragment = null;

    ExpandableListAdapter listAdapter;

    @InjectView(R.id.expandableList)
    ExpandableListView expListView;

    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);

        hideProgress();

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        //Display Add ItemModel fragment when this floating button is clicked
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                fragment = new AddItem();
                if (fragment != null) {
                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.animator.fade_in,
                            android.R.animator.fade_out);

                    fragmentTransaction.replace(R.id.content_swipe_refresh, fragment);

                    fragmentTransaction.commit();
                } else {
                    // error in creating fragment
                    Log.e("MainActivity", "Error in creating fragment");
                }
            }
        });
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mSwipeRefreshLayout.setVisibility(View.INVISIBLE);
    }

    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mSwipeRefreshLayout.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
