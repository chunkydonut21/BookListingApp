package com.example.android.booklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>> {

    private static final String googleApiUrlBasic =   "https://www.googleapis.com/books/v1/volumes?q=";
    private String googleApiUrl;

    private static final int BOOKS_LOADER_ID = 1;
    public static BooksAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        String userSearch = getIntent().getExtras().getString("search");
        googleApiUrl = googleApiUrlBasic + userSearch + "&maxResults=20";

        ListView lv = (ListView) findViewById(R.id.list);

        mAdapter = new BooksAdapter(this, new ArrayList<Books>());
        lv.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        lv.setEmptyView(mEmptyStateTextView);


        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOKS_LOADER_ID, null, this);


        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.noconnection);


        }
    }

    @Override
    public Loader<List<Books>> onCreateLoader(int id, Bundle args) {
        return new BooksLoader(this, googleApiUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Books>> loader, List<Books> books) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        //Set empty state text to display "No Books found."
        mEmptyStateTextView.setText(R.string.nf);

        // Clear the adapter of previous books data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }

    }


    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        mAdapter.clear();
    }

}