package com.phl.ObservableAndObserver;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class DisposingResources {

    public static void main(String[] args) throws InterruptedException {

//        disposeBefore();
        disposeAfter();
//        compositeDisposable();
    }

    private static void disposeBefore() throws InterruptedException {
        Observable
            .interval(1, TimeUnit.SECONDS)
            .subscribe(System.out::println)
            .dispose();

        Thread.sleep(3000);
    }

    private static void disposeAfter() throws InterruptedException {
        Disposable disposable = Observable
            .interval(1, TimeUnit.SECONDS)
            .subscribe(System.out::println);

        Thread.sleep(3000);
        disposable.dispose();
    }

    private static void compositeDisposable() throws InterruptedException {
        Observable<Long> source = Observable.interval(1, TimeUnit.SECONDS);
        Disposable d1 = source.subscribe(System.out::println);
        Disposable d2 = source.subscribe(System.out::println);
        Thread.sleep(3000);
        CompositeDisposable cd = new CompositeDisposable();
        cd.addAll(d1, d2);
        cd.dispose();
        Thread.sleep(2000);
    }
}
