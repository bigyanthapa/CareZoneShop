package carezoneshop.bigyan.com.carezoneshopping.activities;

import java.util.List;

import carezoneshop.bigyan.com.carezoneshopping.model.ItemModel;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by bigyanthapa on 12/9/15.
 */
public interface CarezoneService {

    // Asynchronous declaration

    @GET("/items.json")
    Call<List<ItemModel>> items (
            @Path("category") String category,
            @Path("name") String name
    );



}
