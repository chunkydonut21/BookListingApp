package com.example.android.booklistingapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.io.IOException;
import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<Books>> {

    private String mStringUrl;

    public BooksLoader(Context context, String stringUrl) {
        super(context);
        mStringUrl = stringUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Books> loadInBackground() {
        if (mStringUrl == null) {
            return null;
        }

        List<Books> booksList= null;
        try {
             booksList = Utils.fetchBooksData(mStringUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return booksList ;
    }

}
