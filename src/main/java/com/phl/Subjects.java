package com.phl;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

public class Subjects {

    public static void main(String[] args) throws InterruptedException {

//        subject();
        hotAndCold();
    }

    private static void subject() throws InterruptedException {
        Observable<Integer> source1 = Observable.just(1, 2, 3, 4, 5).subscribeOn(Schedulers.computation());
        Observable<Integer> source2 = Observable.just(100000, 200000, 300000, 400000, 500000).subscribeOn(Schedulers.computation());

        Subject<Object> subject = PublishSubject.create();
        Disposable d1 = subject.subscribe(System.out::println);
        Disposable d2 = subject.subscribe(e -> System.out.println("————— " + e));

        source1.subscribe(subject);
        source2.subscribe(subject);

        Lets.disposeAfter(10000, d1, d2);
    }

    private static void hotAndCold() throws InterruptedException {
        Subject<Integer> subject = PublishSubject.create();
//        Subject<Integer> serialized = subject.toSerialized();
        Disposable disposable = subject.subscribe(System.out::println);

        subject.onNext(1);
        subject.onNext(2);
        subject.onComplete();
        subject.onNext(3);

        Lets.disposeAfter(5000, disposable);

//        Observable.create(emitter -> {
//            emitter.onNext(1);
//            emitter.onNext(2);
//            emitter.onComplete();
//            emitter.onNext(3);
//        })
//            .subscribe(System.out::println)
//            .dispose();
    }
}
