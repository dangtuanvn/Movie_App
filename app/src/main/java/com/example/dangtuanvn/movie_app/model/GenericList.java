package com.example.dangtuanvn.movie_app.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dangtuanvn on 11/7/16.
 */

public class GenericList<T>  {
    List<T> list = new ArrayList<T>();
    public GenericList(){

    }
    public void populate(T t){
        list.add(t);
    }

    public List<T> getList(){
        return list;
    }
}
