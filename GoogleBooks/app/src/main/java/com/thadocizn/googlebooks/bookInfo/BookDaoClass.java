package com.thadocizn.googlebooks.bookInfo;

import android.graphics.Bitmap;

import com.thadocizn.googlebooks.adapters.NetworkAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookDaoClass {
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes/";
    private static final String QUERY = "?q=";
    private static final String SEARCH_URL = BASE_URL + QUERY;

    public static ArrayList<BookClass> findBooks(String bookSearch) {

        BookClass book = null;
        ArrayList<BookClass> books = new ArrayList<>();
        String url = SEARCH_URL + bookSearch;
        final String result = NetworkAdapter.httpRequest(url, NetworkAdapter.GET);

        try {

            JSONObject topLevel = new JSONObject(result);
            JSONArray json = topLevel.getJSONArray("items");

            for (int i = 0; i < json.length(); ++i) {
                book = new BookClass(json.getJSONObject(i));
                Bitmap bitmap = NetworkAdapter.httpImageRequest(((BookClass) book).getBookImageUrl());

                books.add(book);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static Bitmap getBitmap(BookClass book){
        return NetworkAdapter.httpImageRequest(book.getBookImageUrl());
    }


}
