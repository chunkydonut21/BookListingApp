package com.example.android.booklistingapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.android.booklistingapp.MainActivity.logMessage;

public class Utils {

    private static URL createUrl(String stringUrl) throws MalformedURLException {
        URL url = new URL(stringUrl);
        return url;
    }

    private static String makeHttpConnection(URL url) throws IOException {
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String jsonResponse = "";

        try {
            urlConnection = (HttpURLConnection) url.openConnection();


            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(logMessage, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(logMessage, "Problem retrieving the Books JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Books> extractFromJson(String booksJSON) {
        List<Books> booksArray = new ArrayList<>();
        try {

            String authorNames= "";
            String bookTitle;
            String publisher;
            JSONObject bookObject = new JSONObject(booksJSON);
            JSONArray bookArray = bookObject.getJSONArray("items");

            for (int i = 0; i < bookArray.length(); i++) {
                JSONObject volumeObject = bookArray.getJSONObject(i).getJSONObject("volumeInfo");


                if (volumeObject.has("title")){
                    bookTitle = volumeObject.getString("title");
                }
                else {
                    bookTitle = "";
                }
                if(volumeObject.has("publisher")){
                    publisher = volumeObject.getString("publisher");
                }
                else {
                    publisher = "";
                }

                if (volumeObject.has("authors")) {
                    JSONArray authorArray = volumeObject.getJSONArray("authors");


                    for (int j = 0; j < authorArray.length(); j++) {
                        String authorName = authorArray.getString(j);

                        authorNames = authorName;
                    }
                } else {
                    authorNames = "";
                }

                Books books = new Books(bookTitle, authorNames, publisher);
                booksArray.add(books);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return booksArray;

    }


    public static List<Books> fetchBooksData(String booksUrl) throws MalformedURLException {

        URL url = createUrl(booksUrl);

        String response = null;
        try {
            response = makeHttpConnection(url);
        } catch (IOException e) {
            Log.e(logMessage, "Problem making the HTTP request.", e);
        }

        List<Books> booksList = extractFromJson(response);

        return booksList;


    }
}
