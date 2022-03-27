package com.phl;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class MergeAndConcat {

    public static void main(String[] args) throws InterruptedException {

        Observable<String> source1 = Observable.interval(500, TimeUnit.MILLISECONDS).map(e -> "src 1: " + e);
        Observable<String> source2 = Observable.interval(1000, TimeUnit.MILLISECONDS).map(e -> "src 2: " + e);
        Disposable disposable = Observable
            .merge(source1, source2)
            .subscribe(System.out::println);

        Lets.disposeAfter(10000, disposable);
    }
}
