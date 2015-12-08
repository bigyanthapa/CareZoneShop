package carezoneshop.bigyan.com.carezoneshopping.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import carezoneshop.bigyan.com.carezoneshopping.R;


public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.content_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @InjectView(R.id.content_recycler)
    RecyclerView mRecyclerView;

    @InjectView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    boolean isLoading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        //initializeAdapter();

        initializeRefreshLayout();
        initializeOnScrollForRecyclerView();
    }

//    public void initializeAdapter() {
//        mPostAdapter = new PostAdapter(this, new ArrayList<Post>());
//        mRecyclerView.setAdapter(mPostAdapter);
//    }

    public void initializeRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //mPostPresenter.loadPosts(true);
            }
        });
    }

    public void initializeOnScrollForRecyclerView() {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int visibleItemCount = recyclerView.getLayoutManager().getChildCount();
                int totalItemCount = recyclerView.getLayoutManager().getItemCount();
                int pastVisiblesItems = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (!isLoading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        isLoading = true;
                        //mPostPresenter.loadPosts(false);
                    }
                }
            }
        });
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
