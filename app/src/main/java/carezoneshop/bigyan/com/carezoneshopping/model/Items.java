package carezoneshop.bigyan.com.carezoneshopping.model;

import java.util.ArrayList;

/**
 * Created by bigyanthapa on 12/10/15.
 */
public class Items {

    public Items(){}

    private String category;
    private ArrayList<Child> childList;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<Child> getChildList() {
        return childList;
    }

    public void setChildList(ArrayList<Child> childList) {
        this.childList = childList;
    }
}
