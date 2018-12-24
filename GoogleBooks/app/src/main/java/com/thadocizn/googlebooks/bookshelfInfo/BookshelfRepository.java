package com.thadocizn.googlebooks.bookshelfInfo;

import android.arch.lifecycle.MutableLiveData;

import com.thadocizn.googlebooks.sqlObjects.SqlDbDao;

import java.util.ArrayList;

public class BookshelfRepository {

    ArrayList<Bookshelf> bookshelves;
    MutableLiveData<ArrayList<Bookshelf>> liveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Bookshelf>> getBookShelves(){
        liveData = new MutableLiveData<>();
        liveData.setValue(getBookshelfs());
        new Thread(new Runnable() {
            @Override
            public void run() {
                bookshelves = SqlDbDao.getBookshelves();
                liveData.postValue(bookshelves);
            }
        }).start();

        return liveData;
    }


    public void createBookshelf(final String bookshelf) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                long id =   SqlDbDao.createBookshelf(bookshelf);
                liveData.postValue(getBookshelfs());
            }
        }).start();

    }

    private ArrayList<Bookshelf> getBookshelfs(){
        return SqlDbDao.getBookshelves();
    }

    public void deleteBookshelf(final Bookshelf bookshelf) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SqlDbDao.deleteBookshelf(bookshelf);
                liveData.postValue(getBookshelfs());
            }
        }).start();
    }

    public void updateBookshelf(final Bookshelf bookshelf) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SqlDbDao.updateBookshelfName(bookshelf);
                liveData.postValue(getBookshelfs());
            }
        }).start();
    }
}