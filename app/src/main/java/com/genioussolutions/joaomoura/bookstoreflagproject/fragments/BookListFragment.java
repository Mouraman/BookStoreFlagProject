package com.genioussolutions.joaomoura.bookstoreflagproject.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genioussolutions.joaomoura.bookstoreflagproject.R;
import com.genioussolutions.joaomoura.bookstoreflagproject.adapter.BookAdapter;
import com.genioussolutions.joaomoura.bookstoreflagproject.interfaces.AdapterCallBack;
import com.genioussolutions.joaomoura.bookstoreflagproject.interfaces.GetBookService;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Item;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment implements AdapterCallBack {
private RecyclerView bookRecyclerView;
private BookAdapter bookAdapter;
private RecyclerView.LayoutManager layoutManager;
GetBookCallBack getBookCallBack;


    public BookListFragment() {
        // Required empty public constructor
    }
    public void connect(Activity activity){
        getBookCallBack = (GetBookCallBack) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_book_list, container, false);
        bookRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler);
        bookRecyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(getActivity());
        bookRecyclerView.setLayoutManager(layoutManager);

        getBook();
        return view;
    }
    private void getBook(){
        Retrofit retrofit = new Retrofit.
                Builder().
                baseUrl("https://www.googleapis.com/books/v1/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        GetBookService service = retrofit.create(GetBookService.class);
        Call<Result> bookCall = service.getBooks("Android");


        bookCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.v("RETROFIT","OK");
                /*if(response.isSuccessful()){
                    bookAdapter = new BookAdapter(response.body())
                    bookRecyclerView.setAdapter(bookAdapter);
                }*/
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Log.v("RETROFIT","NOK");
                Log.v("RETROFIT",t.getMessage());
            }
        });
    }
    public interface GetBookCallBack{
        void getBook (String id);

    }

    @Override
    public void selectBookOnClick(String id) {
        getBookCallBack.getBook(id);
    }


}
