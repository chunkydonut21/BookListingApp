package com.example.android.booklistingapp;

public class Books {

    private String mBookName;
    private String mAuthorName;
    private String mPublisherName;


    public Books(String bookName, String authorName, String publisherName) {
        mAuthorName = authorName;
        mBookName = bookName;
        mPublisherName = publisherName;
    }

    public String getBookName() {
        return mBookName;
    }

    public String getAuthorName() {
        return mAuthorName;
    }

    public String getPublisherName() {
        return mPublisherName;
    }
}
