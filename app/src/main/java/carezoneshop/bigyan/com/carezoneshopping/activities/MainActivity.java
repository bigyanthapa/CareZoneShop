package carezoneshop.bigyan.com.carezoneshopping.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carezoneshop.bigyan.com.carezoneshopping.R;
import carezoneshop.bigyan.com.carezoneshopping.adapter.ExpandListAdapter;
import carezoneshop.bigyan.com.carezoneshopping.app.AppController;
import carezoneshop.bigyan.com.carezoneshopping.model.Child;
import carezoneshop.bigyan.com.carezoneshopping.model.Items;

public class MainActivity extends Activity {

    private final String mTOKEN = "H75qYRieqfAxveQvReL5";
    private String myURL = "https://czshopper.herokuapp.com/items.json";


   //View Injection Using ButterKnife
    @InjectView(R.id.content_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.fab_add_item)
     FloatingActionButton fab_add_item;

    @InjectView(R.id.expandableList)
    ExpandableListView expListView;

    // Object Declaration
    private ExpandListAdapter expandListAdapter;
    private Intent mIntent;

    // Progress dialog
    private ProgressDialog pDialog;

    //List of Items
    ArrayList<Items> item_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading Items...");
        pDialog.setCancelable(false);


        //Get data from webservice and populate the list view
        makeJSONArrayRequest(myURL);

        //Add Item Activity is opened when FAB button is clicked
        fab_add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntent = new Intent(MainActivity.this, AddItem.class);
                startActivity(mIntent);


            }
        });

        //add action listener when any sub-item is clicked on the list
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int parentPosition, int childPosition, long l) {


                Items item = item_list.get(parentPosition);
                String category = item.getCategory();

                ArrayList<Child> child_list = item.getChildList();
                Child child = child_list.get(childPosition);
                String childName = child.getName();

               // Toast.makeText(MainActivity.this, ""+category+" "+childName,Toast.LENGTH_LONG).show();
                mIntent = new Intent(MainActivity.this, EditItem.class);
                mIntent.putExtra("parent", category);
                mIntent.putExtra("child", childName);
                startActivity(mIntent);

                return false;

            }
        });

        //implement swipe refresh listener
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);

                ( new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout.setRefreshing(false);
                        item_list.clear();
                        makeJSONArrayRequest(myURL);

                    }
                }, 3000);
            }
        });


    }

    // Call the Webservice and populate the Expandable List
    private void makeJSONArrayRequest(String url){
        showpDialog();
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        ArrayList<Child> ch_list;

                        Map<String, String> newMap = new HashMap<>();

                        String value ="";

                        try {
                            // Parsing json array response
                            // loop through each json object

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject item = (JSONObject)response.get(i);

                                if(newMap.containsKey(item.getString("category"))){
                                    value = newMap.get(item.getString("category")) +","+item.getString("name");
                                    newMap.put(item.getString("category"), value);
                                }else{
                                    newMap.put(item.getString("category"), item.getString("name"));
                                }


                            }//For Loop end


                            //now prepare data

                            for (Map.Entry<String, String> entry : newMap.entrySet()){
                                Items item2 = new Items();
                                Child child ;
                                String[] childArray = null;
                                ch_list = new ArrayList<>();

                                item2.setCategory(entry.getKey());

                                childArray = entry.getValue().split(",");
                                for(String s: childArray){
                                    child = new Child();
                                    child.setName(s);
                                    ch_list.add(child);
                                }
                                item2.setChildList(ch_list);
                                item_list.add(item2);

                            }

                            expandListAdapter = new ExpandListAdapter(
                                    MainActivity.this, item_list);
                            expListView.setAdapter(expandListAdapter);

                            hidepDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  headers = new HashMap<>();
                headers.put("X-CZ-Authorization", "H75qYRieqfAxveQvReL5");
                headers.put("Accept", "application/json");

                return headers;
            }

        };

        AppController.getInstance().addToRequestQueue(req);

    }

    private void showpDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
            mSwipeRefreshLayout.setVisibility(View.INVISIBLE);
        }
    }

    private void hidepDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);
        }
    }


}
