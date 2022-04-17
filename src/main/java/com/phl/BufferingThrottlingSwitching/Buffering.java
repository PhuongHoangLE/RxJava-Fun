package com.phl.BufferingThrottlingSwitching;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Buffering {

    public static void main(String[] args) throws InterruptedException {

//        Observable
//            .range(1, 30)
//            .buffer(4, 6)
//            .subscribe(System.out::println)
//            .dispose();

//        timeSpan();
//        boundaryIndicator();
//        window();
    }

    private static void timeSpan() throws InterruptedException {
        Disposable disposable = Observable
            .interval(300, TimeUnit.MILLISECONDS)
            .buffer(1, TimeUnit.SECONDS, 2)
            .subscribe(System.out::println);

        Lets.disposeAfter(6000, disposable);
    }

    private static void boundaryIndicator() throws InterruptedException {
        Disposable disposable = Observable
            .interval(300, TimeUnit.MILLISECONDS)
            .buffer(Observable.interval(1, TimeUnit.SECONDS))
            .subscribe(System.out::println);

        Lets.disposeAfter(6000, disposable);
    }

    private static void window() throws InterruptedException {
        Disposable disposable = Observable
            .range(1, 30)
            .window(4, 6)
            .subscribe(o -> {
                o.subscribe(e -> System.out.print(e + " "));
                System.out.println();
            });

        Lets.disposeAfter(6000, disposable);
    }
}
