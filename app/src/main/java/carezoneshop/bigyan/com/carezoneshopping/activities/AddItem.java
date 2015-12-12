package carezoneshop.bigyan.com.carezoneshopping.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import carezoneshop.bigyan.com.carezoneshopping.R;

public class AddItem extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_item);




    }

}
