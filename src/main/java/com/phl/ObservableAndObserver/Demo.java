package com.phl.ObservableAndObserver;

import com.phl.util.Lets;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.operators.observable.ObservableCreate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Demo {

    public static void main(String[] args) throws InterruptedException {

        observable().subscribe(observer());
        Observable.just(1, 2, 3).subscribe(System.out::println).dispose();
        Observable.fromIterable(List.of("A", "B", "C")).subscribe(System.out::println).dispose();
        Observable.range(1, 3).subscribe(System.out::println).dispose();
        interval();
        Observable.empty().subscribe(System.out::println).dispose();
        never();
        error();
        defer();
        fromCallable();
    }

    private static void interval() throws InterruptedException {
        Disposable disposable = Observable.interval(1, TimeUnit.SECONDS).subscribe(System.out::println);
        Lets.disposeAfter(3000, disposable);
    }

    private static void never() throws InterruptedException {
        Disposable disposable = Observable.never().subscribe(System.out::println);
        Lets.disposeAfter(3000, disposable);
    }

    private static void error() {
        Observable.error(new Exception("(～￣▽￣)～")).subscribe(
                System.out::println,
                error -> System.out.println(error.getMessage())
            )
            .dispose();
    }

    private static void defer() {
        Observable<Integer> source = Observable.defer(() -> Observable.just(new Random().nextInt(10)));
        source.subscribe(System.out::println).dispose();
        source.subscribe(System.out::println).dispose();
    }

    private static void fromCallable() throws InterruptedException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");

        System.out.println("Now: " + LocalDateTime.now().format(formatter));
        //NOTE: Time is calculated when the observable is created
        Single<String> just = Single.just(LocalDateTime.now().format(formatter));
        Thread.sleep(2000);
        just
            .subscribe(time -> System.out.println("Just: " + time))
            .dispose();

        System.out.println("Now: " + LocalDateTime.now().format(formatter));
        //NOTE: Time is calculated when the observable is subscribed
        Observable<String> fromCallable = Observable.fromCallable(() -> LocalDateTime.now().format(formatter));
        Thread.sleep(2000);
        fromCallable
            .subscribe(time -> System.out.println("fromCallable: " + time))
            .dispose();
    }

    private static Observable<Integer> observable() {
        return new ObservableCreate<>(emitter -> {
            try {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            } catch (Throwable t) {
                emitter.onError(t);
            }
        });
    }

    private static Observer<Integer> observer() {
        return new Observer<>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("🚀 Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                System.out.println("➡️ On next: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("👋 Completed");
            }
        };
    }
}
