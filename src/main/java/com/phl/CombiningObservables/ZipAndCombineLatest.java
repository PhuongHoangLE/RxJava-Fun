package com.phl.CombiningObservables;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ZipAndCombineLatest {

    public static void main(String[] args) throws InterruptedException {

        Disposable disposable = Observable
            .zip(
//            .combineLatest(
                Observable.interval(500, TimeUnit.MILLISECONDS),
                Observable.interval(1, TimeUnit.SECONDS),
                (e1, e2) -> String.format("[%s, %s]", e1, e2)
            )
            .subscribe(System.out::println);

        Lets.disposeAfter(10000, disposable);
    }
}
