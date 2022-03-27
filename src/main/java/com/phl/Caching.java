package com.phl;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Caching {

    public static void main(String[] args) throws InterruptedException {

        Observable<Long> source = Observable
            .interval(500, TimeUnit.MILLISECONDS)
            .cache();
        Disposable d1 = source.subscribe(e -> System.out.println("ONE: " + e));
        Thread.sleep(3000);
        Disposable d2 = source.subscribe(e -> System.out.println("TWOOOOOO: " + e));
        Thread.sleep(2000);

        Lets.dispose(d1, d2);
    }
}
