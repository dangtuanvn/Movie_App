package com.example.dangtuanvn.movie_app.viewmodel;

/**
 * Created by dangtuanvn on 12/15/16.
 */

public interface ItemBinder<T>
{
    int getLayoutRes(T model);
    int getBindingVariable(T model);
}