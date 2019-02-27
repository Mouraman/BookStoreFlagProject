package com.genioussolutions.joaomoura.bookstoreflagproject;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.genioussolutions.joaomoura.bookstoreflagproject.adapter.BookAdapter;
import com.genioussolutions.joaomoura.bookstoreflagproject.fragments.BookListFragment;
import com.genioussolutions.joaomoura.bookstoreflagproject.fragments.DetailFragment;

public class MainActivity extends AppCompatActivity implements BookListFragment.GetBookCallBack {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BookListFragment bookListFragment = new BookListFragment();
        bookListFragment.connect(this);
        fragmentTransaction.add(R.id.container, bookListFragment, "list");
        fragmentTransaction.commit();


    }

    @Override
    public void getBook(String id) {
        DetailFragment detailFragment = new DetailFragment();

        Bundle args= new Bundle();
        args.putString("id",id);

        detailFragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.container,detailFragment,"DETAIL");
        fragmentTransaction.addToBackStack("list");
        fragmentTransaction.commit();


    }
}
