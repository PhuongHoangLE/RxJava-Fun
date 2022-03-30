package com.phl.Operators;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Operators {

    public static void main(String[] args) throws InterruptedException {

        suppressing();
//        transforming();
//        reducing();
//        collection();
//        recovery();
//        actions();
    }

    private static void actions() {
        Observable
            .just(1.0, 2, 3.0)
            .cast(Double.class)
            .doOnSubscribe((disposable) -> {
                System.out.println("Subscribed");
            })
            .doOnNext((element) -> {
                System.out.println("NEXT: " + element);
            })
            .doOnError((error) -> {
                System.out.println("ERROR: " + error.getMessage());
            })
            .onErrorComplete()
            .doOnComplete(() -> {
                System.out.println("Completed");
            })
            .subscribe()
            .dispose();
    }

    private static void recovery() {
//        onErrorReturnItem();
//        onErrorReturn();
//        onErrorResumeNext();
        retry();
    }

    private static void retry() {
        Observable
            .just(1.0, 2, 3.0)
            .cast(Double.class)
            .retry(2, t -> {
                System.out.println("Retrying...");
                return true;
            })
            .onErrorReturnItem(0.0)
            .subscribe(System.out::println)
            .dispose();
    }

    private static void onErrorResumeNext() {
        Observable
            .just(1.0, 2, 3.0)
            .cast(Double.class)
            .onErrorResumeNext(t -> {
                t.printStackTrace();
                return Observable.just(5.0, 6.0);
            })
            .subscribe(System.out::println)
            .dispose();
    }

    private static void onErrorReturn() {
        Observable
            .just(1.0, 2, 3.0)
            .cast(Double.class)
            .onErrorReturn(t -> {
                t.printStackTrace();
                return 0.0;
            })
            .subscribe(System.out::println)
            .dispose();
    }

    private static void onErrorReturnItem() {
        Observable
            .just(1.0, 2, 3.0)
            .cast(Double.class)
            .onErrorReturnItem(0.0)
            .subscribe(System.out::println)
            .dispose();
    }

    private static void collection() {
        Observable.just(1, 2, 3).toList().subscribe(System.out::println).dispose();
        Observable.just(1, 3, 2).toSortedList().subscribe(System.out::println).dispose();
        Observable.just(1, 2, 3).toMap(e -> e, e -> e * 2).subscribe(System.out::println).dispose();
        Observable.just(1, 2, 2).collect(Collectors.toSet()).subscribe(System.out::println).dispose();
    }

    private static void reducing() throws InterruptedException {
        //NOTE: Observable<T> -> Single<T>
        Observable.just(1, 2, 3).count().subscribe(System.out::println).dispose();
        Observable.just(1, 2, 3).reduce(Integer::sum).subscribe(System.out::println).dispose();
        Observable.just(1, 2, 3).contains(5).subscribe(System.out::println).dispose();
        Observable.just(1, 2, 3).all(e -> e < 5).subscribe(System.out::println).dispose();
        Observable.just(1, 2, 3).any(e -> e < 2).subscribe(System.out::println).dispose();

        Thread.sleep(5000);
    }

    private static void transforming() throws InterruptedException {
        Disposable d1 = Observable.interval(1, TimeUnit.SECONDS).subscribe(System.out::println);
        Disposable d2 = Observable
            .just("ONE", "TWO", "THREE")
            .map(e -> "→ " + e)
            .cast(String.class)
            .delay(1, TimeUnit.SECONDS)
            .delaySubscription(1, TimeUnit.SECONDS)
            .sorted()
            .scan((x, y) -> x + " " + y)
            .repeat(2)
            .subscribe(System.out::println);

        Lets.disposeAfter(10000, d1, d2);
    }

    private static void suppressing() {
        filter();
//        Observable
//            .just(1, 2, 3, 4, 4, 8, 6, 7, 5, 2)
//            .take(5)
//            .skip(1)
//            .distinct()
//            .elementAt(1)
//            .subscribe(System.out::println)
//            .dispose();
    }

    private static void filter() {
        Observable
            .just(1, 9, 4, 3, 8, 6, 7, 5, 2)
            .filter(e -> e > 6)
            .sorted(Comparator.reverseOrder())
            .subscribe(System.out::println)
            .dispose();
        // —🔴—🔻—🟨—|→
        // filter {🔴}
        // —🔴—————|→
    }
}
