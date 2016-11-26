package timothyyudi.tryendless;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<DummyModel> mDummyList;
    MyCustomAdapter mAdapter;
    LinearLayoutManager mLinearLayoutManager;
    EndlessRecyclerViewScrollListener scrollListener;

    int dummyUpperCount=99;
    int dummyLowerCount=90;
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
                mDummyList.addAll(mDummyList.size(), addBulkOldDummyDataList());
                mAdapter.notifyItemInserted(mDummyList.size());
            }
        });

        //scroll up then pull to refresh
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Your refresh code here
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
//                loadNextDataFromApi(page);
                addBulkOldDummyDataList();
            }
        };
        // Adds the scroll listener to RecyclerView
        mRecyclerView.addOnScrollListener(scrollListener);

    }

    public ArrayList<DummyModel> addBulkOldDummyDataList(){
        ArrayList<DummyModel> tempDummyList = new ArrayList<>();
        for(int i = dummyUpperCount; i>=dummyLowerCount;i--){
            tempDummyList.add(new DummyModel("Alibaba"+i,"alibaba"+i+"@gmail.com"));
            Log.v(TAG,"Alibaba"+i);
        }
        dummyLowerCount-=10;
        dummyUpperCount-=10;
        return tempDummyList;
    }

    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
    }
}
