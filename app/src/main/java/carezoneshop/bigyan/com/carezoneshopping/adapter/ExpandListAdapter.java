package carezoneshop.bigyan.com.carezoneshopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import carezoneshop.bigyan.com.carezoneshopping.R;
import carezoneshop.bigyan.com.carezoneshopping.model.Child;
import carezoneshop.bigyan.com.carezoneshopping.model.Items;

/**
 * Created by bigyanthapa on 12/11/15.
 */
public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Items> items;

    public ExpandListAdapter(Context context, ArrayList<Items> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public int getChildrenCount(int itemPosition) {
        ArrayList<Child> chList = items.get(itemPosition).getChildList();
        return chList.size();
    }

    @Override
    public Object getGroup(int itemPosition) {
        return items.get(itemPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> chList = items.get(groupPosition).getChildList();
        return chList.get(childPosition);
    }

    @Override
    public long getGroupId(int itemPosition) {
        return itemPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int itemPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Items items = (Items) getGroup(itemPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.list_header, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.listHeader);
        tv.setText(items.getCategory());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Child child = (Child) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView listChild = (TextView) convertView
                .findViewById(R.id.listChild);

        listChild.setText(child.getName().toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int itemPosition, int childPosition) {
        return true;
    }
}
