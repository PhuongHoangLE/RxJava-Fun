package com.phl.CombiningObservables;

import io.reactivex.rxjava3.core.Observable;

import java.util.List;

public class FlatMapAndConcatMap {

    public static void main(String[] args) {

        Observable
            .fromIterable(List.of("Hello", "Reactive", "Programming"))
//            .flatMap(e -> Observable.fromArray(e.split("")))
            .concatMap(e -> Observable.fromArray(e.split("")))
            .subscribe(e -> System.out.print(e + "_"))
            .dispose();
    }
}
