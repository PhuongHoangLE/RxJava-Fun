package com.phl;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Ambiguous {

    public static void main(String[] args) throws InterruptedException {

        Observable<String> source1 = Observable.interval(1, TimeUnit.SECONDS).take(10).map(e -> "Source 1: " + e);
        Observable<String> source2 = Observable.interval(10, TimeUnit.MILLISECONDS).take(10).map(e -> "Source 2: " + e);
        Disposable disposable = Observable
            .amb(Arrays.asList(source1, source2))
            .subscribe(System.out::println);

        Lets.disposeAfter(10000, disposable);
    }
}
