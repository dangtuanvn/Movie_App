package com.example.dangtuanvn.movie_app.datastoreRX;

import com.android.annotations.Nullable;

import rx.Observable;

/**
 * Created by dangtuanvn on 12/21/16.
 */

public interface VolleyWrapperRX {
    Observable<Object> getDataObservable();
}
