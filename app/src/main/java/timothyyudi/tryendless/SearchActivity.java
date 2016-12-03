package timothyyudi.tryendless;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    MenuItem searchMenuItem;
    SearchView searchView;
    ArrayList<DummyModel> mDummyList;
    TextView tvHint;
    RecyclerView rvResults;
    MyRecyclerViewAdapter mAdapter;
    ArrayList<DummyModel> mSearchResults;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do something here, such as start an Intent to the parent activity.
                finish();
            }
        });
        setSupportActionBar(toolbar);

        tvHint = (TextView) findViewById(R.id.tvSearchPlaceholder);
        rvResults = (RecyclerView) findViewById(R.id.my_recycler_view);
        mSearchResults = new ArrayList<>();
        mAdapter = new MyRecyclerViewAdapter(this,mSearchResults);
        mLinearLayoutManager = new LinearLayoutManager(this);
        rvResults.setLayoutManager(mLinearLayoutManager);
        rvResults.setAdapter(mAdapter);

        //handle any intent sent to this activity
        handleIntent(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }
        });
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchMenuItem.expandActionView();

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        intent.putParcelableArrayListExtra("dataList",mDummyList);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        mDummyList=intent.getParcelableArrayListExtra("dataList");
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mSearchResults.clear();
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            for(DummyModel d : mDummyList){
                if(d.title != null && d.title.contains(query)){
                    if(tvHint.getVisibility()!=View.GONE)tvHint.setVisibility(View.GONE);
                    mSearchResults.add(d);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

}
