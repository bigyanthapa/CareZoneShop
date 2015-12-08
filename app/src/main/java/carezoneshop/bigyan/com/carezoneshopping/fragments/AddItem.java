package carezoneshop.bigyan.com.carezoneshopping.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import carezoneshop.bigyan.com.carezoneshopping.R;


public class AddItem extends Fragment {
    public AddItem(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_item, container, false);

        return rootView;
    }
}
