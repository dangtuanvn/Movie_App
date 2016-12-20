package com.example.dangtuanvn.movie_app;

import android.content.Context;
import android.util.Log;

import com.android.databinding.library.baseAdapters.*;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dangtuanvn.movie_app.datastore.SingletonQueue;
import com.example.dangtuanvn.movie_app.model.News;
import com.example.dangtuanvn.movie_app.model.converter.NewsDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    //    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    private PublishSubject<String> subjectObservable;
    private String abc = "543";

    //    @Test
    public void testPublishSubject() {
        subjectObservable = PublishSubject.create();
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String string) {
                System.out.println(string);
//                unsubscribe();
            }
        };

        subjectObservable.asObservable().subscribe(subscriber);

        changeSubjectVariable();
        changeSubjectVariable();
        changeSubjectVariable();
        changeSubjectVariable();
    }

    public void changeSubjectVariable() {
        abc += "2";
        subjectObservable.onNext(abc);
    }


    private Observable<String> observable;
    private String qwe = "987";
    Subscriber<String> subscriber;

    //    @Test
    public void testObservable() {
        observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(qwe);

            }
        });

        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String string) {
                System.out.println(string);
            }
        };
        observable.asObservable().subscribe(subscriber);

        changeVariable();
        changeVariable();
        changeVariable();
        changeVariable();
    }

    public void changeVariable() {
        qwe += "2";
//        subscriber.onNext(qwe);
        observable.subscribe(subscriber);

    }


    private Observable<String> StringObservable;
    private String[] asdqwe = {"1414", "2323", "3535"};
    Subscriber<String> StringSubscriber;

    //    @Test
    public void testStringObservable() {

        StringObservable = Observable.from(asdqwe);
//        StringObservable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext(qwe);
//            }
//        });

        StringSubscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String string) {
                System.out.println(string);
            }
        };
        StringObservable.subscribe(StringSubscriber);

//        changeStringVariable();
//        changeStringVariable();
//        changeStringVariable();
//        changeStringVariable();
    }

    public void changeStringVariable() {
//        asdqwe += "2";
//        subscriber.onNext(qwe);
    }


    public int returnDouble(int x) {
        if (x == 2) {
            return 100;
        }
        return 1;
    }


    //    @Test
    public void startChain() {
        Observable<Integer> observable1 = Observable.range(0, 10);

//        observable1.subscribe(
//                // onNext
//                (Integer x) -> {
//                x = returnDouble(x);
//                    System.out.println(x);
//                })
//
//                // onError
//
//                // onCompleted
//                ;

//        Observable<String> observable2 = changeFormat(observable1);

//        Observable<Double> observable2 = observable1.map(number -> {
//            number += 5;
//            return number.doubleValue();
//        });
//
//        Observable<Float> observable3 = observable2.map(number -> {
//            number *= 2;
//            return number.floatValue();
//        });
//
////        Observable<String> observable4 = observable3.map(number -> "String: " + number.toString());
//        Observable<String> observable4 = observable3.map(number -> {
//            return "String: " + number.toString   ();
//        });
//
//        observable4.subscribe(System.out::println);

        Observable<Double> observable2 = observable1.map(new Func1<Integer, Double>() {
            @Override
            public Double call(Integer integer) {
                Double a = integer.doubleValue();
                a += 5;
                return a;
            }
        });

        Observable<Float> observable3 = observable2.map(new Func1<Double, Float>() {
            @Override
            public Float call(Double d) {
                Float a = d.floatValue();
                a *= 2;
                return a;
            }
        });

        Observable<String> observable4 = observable3.map(new Func1<Float, String>() {
            @Override
            public String call(Float o) {
                return o.toString();
            }
        });


        Subscriber<String> subscriber1 = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String text) {
                System.out.println(text);
            }
        };

        observable4.subscribe(subscriber1);
    }

    public Observable changeFormat1(Observable<Integer> intObs) {
        return intObs.map(new Func1<Integer, String>() {
            @Override
            public String call(Integer i) {
                i += 5;
                return i.toString();
            }
        });
    }
}

//
//    var numberObsevable: Variable<Int> = Variable(1)
//        var number: Int {
//        get {
//             return numberObsevable.value
//        }
//
//        set{
//             numberObsevable.value = newValue
//           }
//        }
//
//        let stringObservable = changeObservable(observable: numberObsevable.asObservable())
//        stringObservable.subscribe(onNext: { (message) in
//              print(message)
//           }, onError: nil, onCompleted: nil, onDisposed: nil)
//        .addDisposableTo(disposeBag)
//
//@IBAction func increaseClicked(_ sender: Any) {
//        number += 2
//        }
//
//        func changeObservable(observable: Observable<Int>) -> Observable<String> {
//        return observable.map({ (number) -> String in
//        return "String: \(number)"
//        })
//        }