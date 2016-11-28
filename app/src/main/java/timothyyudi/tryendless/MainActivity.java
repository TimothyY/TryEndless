package timothyyudi.tryendless;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<DummyModel> mDummyList;
    MyCustomAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    EndlessRecyclerViewScrollListener scrollListener;
    SwipeRefreshLayout swipeRefreshLayout;

    //for new items when load old data
    int dummyUpperCount=99;
    int dummyLowerCount=90;
    //for new items when load new data
    int dummyUpperCount2=109;
    int dummyLowerCount2=100;
    String TAG = "TryEndless";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        handleIntent(getIntent());

        //scroll up then pull to refresh
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNextNewDataFromApi(0,0);
            }
        });
        //end of scroll up then pull to refresh

        mDummyList = new ArrayList<>();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new MyCustomAdapter(this,mDummyList);
        mRecyclerView.setAdapter(mAdapter);

        // Retain an instance so that you can call `resetState()` for fresh searches
        scrollListener = new EndlessRecyclerViewScrollListener(mLinearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextOldDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(scrollListener);

        //load initial data
        loadInitialDataFromApi();
    }

    public  void loadInitialDataFromApi(){
        mDummyList.add(new DummyModel("Initial Data","InitialData@gmail.com"));
        mAdapter.notifyItemInserted(mDummyList.size());
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextOldDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        ArrayList<DummyModel> tempDummyList = new ArrayList<>();
        for(int i = dummyUpperCount; i>=dummyLowerCount;i--){
            tempDummyList.add(new DummyModel("Alibaba"+i,"alibaba"+i+"@gmail.com"));
            Log.v(TAG,"Alibaba"+i);
        }
        dummyLowerCount-=10;
        dummyUpperCount-=10;
        mDummyList.addAll(mDummyList.size(), tempDummyList);
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        mAdapter.notifyItemInserted(mDummyList.size());
    }

    public void loadNextNewDataFromApi(int offset,int secondaryOffset){
        ArrayList<DummyModel> tempDummyList = new ArrayList<>();
        for(int i = dummyUpperCount2; i>=dummyLowerCount2;i--){
            tempDummyList.add(new DummyModel("Alibaba"+i,"alibaba"+i+"@gmail.com"));
            Log.v(TAG,"Alibaba"+i);
        }
        dummyLowerCount2+=10;
        dummyUpperCount2+=10;
        mDummyList.addAll(0, tempDummyList);
        mAdapter.notifyItemRangeInserted(0,tempDummyList.size());
        mRecyclerView.scrollToPosition(0);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
}
