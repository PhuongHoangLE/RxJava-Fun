package com.phl;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class ConnectableObservable {

    public static void main(String[] args) throws InterruptedException {

        var observable = Observable.interval(1000, TimeUnit.MILLISECONDS).publish(); //NOTE: 🔥 Hot observable
        observable.connect();

        Disposable d1 = observable.subscribe(item -> System.out.println("Subscriber 1: " + item));
        Thread.sleep(2000);
        Disposable d2 = observable.subscribe(item -> System.out.println("Subscriber 2: " + item));
        Thread.sleep(2000);

        Lets.dispose(d1, d2);
    }
}
