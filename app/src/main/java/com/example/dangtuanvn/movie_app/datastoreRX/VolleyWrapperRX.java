package com.example.dangtuanvn.movie_app.datastoreRX;

import rx.Observable;

/**
 * Created by dangtuanvn on 12/21/16.
 */

public interface VolleyWrapperRX {
    Observable<String> createStringRequest(String url);

}
