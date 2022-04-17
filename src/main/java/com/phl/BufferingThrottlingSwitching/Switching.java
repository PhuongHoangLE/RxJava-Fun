package com.phl.BufferingThrottlingSwitching;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.TestScheduler;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Switching {

    public static void main(String[] args) {

        //NOTE:
        // - flatMap: not preserving the order of items, works asynchronously
        // - switchMap: unsubscribing from previous observable after emitting new one
        // - concatMap: preserving the order of items, works synchronously
//        flatMap();
//        switchMap();
//        concatMap();
    }

    private static void flatMap() {
        //Note that FlatMap merges the emissions of these Observables, so that they may interleave.
        // So what actually happened here is operator flatMap does not care about the order of the items.
        final TestScheduler scheduler = new TestScheduler();

        Observable
            .fromIterable(List.of("a", "b", "c", "d", "e", "f"))
            .flatMap( s -> {
                final int delay = new Random().nextInt(10);
                return Observable
                    .just(s + delay)
                    .delay(delay, TimeUnit.SECONDS, scheduler);
            })
            .toList()
            .doOnSuccess(System.out::println)
            .subscribe();

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);
    }

    private static void switchMap() {
        //Note that whenever a new item is emitted by the source Observable, it will unsubscribe to and
        // stop mirroring the Observable that was generated from the previously-emitted item, and
        // begin only mirroring the current one.
        final TestScheduler scheduler = new TestScheduler();

        Observable
            .fromIterable(List.of("a", "b", "c", "d", "e", "f"))
            .switchMap( s -> {
                final int delay = new Random().nextInt(10);
                System.out.print(s + delay + " —•→ ");
                return Observable
                    .just(s + delay)
                    .delay(delay, TimeUnit.SECONDS, scheduler);
            })
            .toList()
            .doOnSuccess(e -> System.out.println("\n" + e))
            .subscribe();

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);
    }

    private static void concatMap() {
        //Note that ConcatMap works almost the same as flatMap, but preserves the order of items.
        // It must be used with caution because it will break asynchrony and make the whole process take longer.
        final TestScheduler scheduler = new TestScheduler();

        Observable
            .fromIterable(List.of("a", "b", "c", "d", "e", "f"))
            .concatMap( s -> {
                final int delay = new Random().nextInt(10);
                return Observable
                    .just(s + delay)
                    .delay(delay, TimeUnit.SECONDS, scheduler)
                    .doOnNext(e -> System.out.print(s + "(" + scheduler.now(TimeUnit.SECONDS) + "s) —•→ "));
            })
            .toList()
            .doOnSuccess(e -> System.out.println("\nTotal: " + scheduler.now(TimeUnit.SECONDS) + " seconds."))
            .subscribe();

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES);
    }
}
