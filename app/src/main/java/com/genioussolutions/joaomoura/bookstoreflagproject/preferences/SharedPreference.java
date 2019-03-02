package com.genioussolutions.joaomoura.bookstoreflagproject.preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.genioussolutions.joaomoura.bookstoreflagproject.model.Item;
import com.google.gson.Gson;

public class SharedPreference {

    public static final String PREFS_NAME = "PRODUCT_APP";
    public static final String FAVORITES = "Product_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<Item> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, Item item) {
        List<Item> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<Item>();
        favorites.add(item);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, Item item) {
        ArrayList<Item> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(item);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<Item> getFavorites(Context context) {
        SharedPreferences settings;
        List<Item> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            Item[] favoriteItems = gson.fromJson(jsonFavorites,
                    Item[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<Item>(favorites);
        } else
            return null;

        return (ArrayList<Item>) favorites;
    }
}