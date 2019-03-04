package com.genioussolutions.joaomoura.bookstoreflagproject.model;

import io.realm.RealmObject;

public class Favorito extends RealmObject {

    private boolean isFavorite;

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
