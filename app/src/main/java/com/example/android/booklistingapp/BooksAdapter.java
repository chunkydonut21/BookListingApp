package com.example.android.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BooksAdapter extends ArrayAdapter<Books> {
    public BooksAdapter(Context context, ArrayList<Books> books) {

        super(context, 0, books);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.display_view, parent, false);
        }
        Books books1 = getItem(position);


        TextView tv1 = (TextView) listItemView.findViewById(R.id.name);
        TextView tv2 = (TextView) listItemView.findViewById(R.id.author);
        TextView tv3 = (TextView) listItemView.findViewById(R.id.pub);


        tv1.setText(books1.getBookName());
        tv2.setText(books1.getAuthorName());
        tv3.setText(books1.getPublisherName());

        return listItemView;
    }
}
