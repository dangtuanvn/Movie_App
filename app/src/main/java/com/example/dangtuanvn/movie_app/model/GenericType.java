package com.example.dangtuanvn.movie_app.model;

import java.io.Serializable;

/**
 * Created by dangtuanvn on 11/7/16.
 */

public class GenericType<T> implements Serializable{
    protected T value;

    public void setValue (T value) {
        this.value = value;
    }

    public T getValue () {
        return value;
    }
}
