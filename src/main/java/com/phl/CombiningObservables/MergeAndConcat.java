package com.phl.CombiningObservables;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class MergeAndConcat {

    public static void main(String[] args) throws InterruptedException {

        Observable<String> source1 = Observable.interval(500, TimeUnit.MILLISECONDS).map(Object::toString).take(3);
        Observable<String> source2 = Observable.interval(1000, TimeUnit.MILLISECONDS).map(e -> "------------- " + e);
        Disposable disposable = Observable
//            .merge(source1, source2)
            .concat(source1, source2)
            .subscribe(System.out::println);

        Lets.disposeAfter(10000, disposable);
    }
}
