package com.phl;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class ObservableVariants {

    public static void main(String[] args) {

//        single();
//        maybe();
//        completable();
    }

    private static void completable() {
        //NOTE: a completable does a deferred computation without returning any value (ie returning void)
        //      and then notifies the subscribers the completion or error.
        Completable.complete().subscribe(() -> System.out.println("Completed")).dispose();
        Completable
            .fromRunnable(() -> System.out.println("Do something..."))
            .subscribe(() -> System.out.println("Completed"))
            .dispose();
    }

    private static void maybe() {
        Observable.empty()
            .firstElement()
            .subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("Completed")
            )
            .dispose();
    }

    private static void single() {
        //NOTE: Subscribe to a single element (the first one)
        Observable<String> source = Observable.just("Alex", "Justin", "Jack");
        source
            .first("Name") //NOTE: "Name" will be printed if the observable is empty
            .subscribe(System.out::println)
            .dispose();

        //NOTE: Same result using Single
        Single.just("Alex").subscribe(System.out::println).dispose();
    }
}
