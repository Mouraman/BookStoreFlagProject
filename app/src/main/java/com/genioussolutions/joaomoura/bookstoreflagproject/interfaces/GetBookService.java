package com.genioussolutions.joaomoura.bookstoreflagproject.interfaces;

import com.genioussolutions.joaomoura.bookstoreflagproject.model.Item;
import com.genioussolutions.joaomoura.bookstoreflagproject.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetBookService {
    @GET ("volumes/")
    Call<Result> getBooks(@Query("q") String id);

    @GET("volumes/{id}")
    Call<Item> getBook(@Path("id") String id);



}
