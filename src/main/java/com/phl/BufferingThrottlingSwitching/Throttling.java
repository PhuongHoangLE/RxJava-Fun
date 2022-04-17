package com.phl.BufferingThrottlingSwitching;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class Throttling {

    public static void main(String[] args) throws InterruptedException {

        Disposable disposable = Observable
            .interval(200, TimeUnit.MILLISECONDS)
            .throttleFirst(1, TimeUnit.SECONDS)
            .subscribe(System.out::println);

        Lets.disposeAfter(6000, disposable);
    }
}
