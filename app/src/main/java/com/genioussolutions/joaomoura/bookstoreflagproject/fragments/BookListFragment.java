package com.genioussolutions.joaomoura.bookstoreflagproject.fragments;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.genioussolutions.joaomoura.bookstoreflagproject.R;
import com.genioussolutions.joaomoura.bookstoreflagproject.adapter.BookAdapter;
import com.genioussolutions.joaomoura.bookstoreflagproject.interfaces.AdapterCallBack;
import com.genioussolutions.joaomoura.bookstoreflagproject.interfaces.GetBookService;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Item;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Result;
import com.google.gson.Gson;

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
private EditText etProcuraLivro;
private String livroProcurado ;
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

        etProcuraLivro =(EditText) view.findViewById(R.id.et_procuraLivros);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                etProcuraLivro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        livroProcurado = etProcuraLivro.getText().toString();
                        if (livroProcurado!=null){
                            if (livroProcurado.isEmpty()){
                                livroProcurado="android";
                            }
                        }
                        InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(etProcuraLivro.getWindowToken(), 0);
                        getBook();
                    }
                });


            }
        };

        etProcuraLivro.addTextChangedListener(textWatcher);
        bookRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler);
        bookRecyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(getActivity());
        bookRecyclerView.setLayoutManager(layoutManager);



        return view;
    }
    private void getBook(){
        Retrofit retrofit = new Retrofit.
                Builder().
                baseUrl("https://www.googleapis.com/books/v1/").
                addConverterFactory(GsonConverterFactory.create())
                .build();

        GetBookService service = retrofit.create(GetBookService.class);

        Call<Result> bookCall = service.getBooks(livroProcurado);


        bookCall.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Log.v("RETROFIT","OK");

                bookAdapter = new BookAdapter(response.body().getItems(), getActivity(), BookListFragment.this);
                bookRecyclerView.setAdapter(bookAdapter);
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
