package carezoneshop.bigyan.com.carezoneshopping.model;

/**
 * Created by bigyanthapa on 12/15/15.
 */
public class ItemModel {

    private String id;

    private String category;

    private String updated_at;

    private String name;

    private String created_at;

    private String user_id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", category = "+category+", updated_at = "+updated_at+", name = "+name+", created_at = "+created_at+", user_id = "+user_id+"]";
    }
}
