package carezoneshop.bigyan.com.carezoneshopping.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import carezoneshop.bigyan.com.carezoneshopping.R;
import carezoneshop.bigyan.com.carezoneshopping.app.AppController;

public class AddItem extends Activity {


    Button addItemButton;

    Button cancelButton;

    EditText addItemCategoryInput;

    EditText addItemNameInput;

    // Progress dialog
    private ProgressDialog pDialog;

    //AppController
    private AppController appController;

    //URLs
    private final String mTOKEN = "H75qYRieqfAxveQvReL5";
    private String myURL = "https://czshopper.herokuapp.com/items.json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_item);

        pDialog = new ProgressDialog(AddItem.this);
        pDialog.setMessage("Loading Items...");
        pDialog.setCancelable(false);

        addItemButton = (Button)findViewById(R.id.addItemButton);
        cancelButton = (Button)findViewById(R.id.cancelItemButton);

        addItemCategoryInput = (EditText)findViewById(R.id.addItemCategoryEditText);
        addItemNameInput = (EditText) findViewById(R.id.addItemNameEditText);

        /**
         * Action Listener for addItem Buttn
         * */
        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String itemCategory = addItemCategoryInput.getText().toString();
                String itemName = addItemNameInput.getText().toString();

                //Create JSON Object
                createJSONobject(itemCategory, itemName);


            }
        });

        /**
         * Cancel button actionListener
         * */
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddItem.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    /**
     * Now create JSON Object and upload to the webservice
     * */
    public void createJSONobject(final String category, final String name){

        RequestQueue queue = Volley.newRequestQueue(AddItem.this);
        showpDialog();


       final StringRequest req = new StringRequest(Request.Method.POST, myURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String data) {

                        try {
                            // Parsing  data to json object
                            JSONObject response = new JSONObject(data);
                            try {
                                if ("true".equalsIgnoreCase(response.getString("status").toString())) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    //hide dialog
                                    hidepDialog();
                                    startActivity(intent);

                                } else {
                                    hidepDialog();
                                    Toast.makeText(getApplicationContext(), "Not able to post data.", Toast.LENGTH_SHORT).show();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                hidepDialog();
                            }


                        } catch (JSONException e) {
                            hidepDialog();
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        hidepDialog();
                        // Handle error
                        Toast.makeText(getApplicationContext(),
                                "Volley error: " + volleyError.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }) {
                // this is the relevant method

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("X-CZ-Authorization", "H75qYRieqfAxveQvReL5");
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

               @Override
               protected Map<String, String> getParams() {
                   Map<String, String> params = new HashMap<String, String>();

                   params.put("category", category);
                   params.put("created_at", Calendar.getInstance().getTime().toString());
                   params.put("id", String.valueOf(getId()));
                   params.put("name", name);
                   params.put("updated_at", Calendar.getInstance().getTime().toString());
                   params.put("user_id",String.valueOf(getUserId()));

                   return params;

               }
        };

        queue.add(req);

        /*appController = new AppController();
        showpDialog();

        JSONObject obj = new JSONObject();
        try
        {
            Date date = Calendar.getInstance().getTime();
            obj.put("category", category);
            obj.put("created_at", date);
            obj.put("id", String.valueOf(getId()));
            obj.put("name", name);
            obj.put("updated_at", date);
            obj.put("user_id", String.valueOf(getUserId()));

            RequestQueue queue = appController.getRequestQueue();
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST,myURL,obj,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        hidepDialog();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidepDialog();
                    }
                }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("X-CZ-Authorization", "H75qYRieqfAxveQvReL5");
                    headers.put("Accept", "application/json");
                    headers.put("Content-Type", "application/json");
                    return headers;
                }

            };

            queue.add(jsObjRequest);

        } catch (JSONException e) {
            e.printStackTrace();
        }*/

    }

    /**
     * Get a Random userId
     * */

    public int getUserId()
    {
        int randomUserId = 0;
        Random randomGenerator = new Random();
        randomUserId = randomGenerator.nextInt(100);

        return randomUserId;
    }

    /**
     * Get a Random Id
     * */

    public int getId(){
        int randomId = 0;
        Random randomGenerator = new Random();
        randomId = randomGenerator.nextInt(1000);

        return randomId;

    }

    /**
     * Methods to show and hide progress dialog
     * */

    private void showpDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hidepDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }


}
