package com.phl;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;

public class Concurrency {

    public static void main(String[] args) throws InterruptedException {

        Observable<Object> source = Observable.create(emitter -> {
            Runnable actualSource = () -> {
                emitter.onNext("Hello");
                emitter.onNext("RxJava");
            };
            new Thread(actualSource).start();
        });

        Lets.disposeAfter(1000,
            source.subscribe(Concurrency::printElementAndThread),
            source.subscribe(Concurrency::printElementAndThread)
        );
    }

    private static void printElementAndThread(Object e) {
        System.out.println(e + "\t—•-> " + Thread.currentThread().getName());
    }
}
