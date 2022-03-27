package com.phl;

import com.phl.util.Lets;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SchedulerDemo {

    public static void main(String[] args) throws InterruptedException {

//        subscribeOn(Schedulers.computation());
//        subscribeOn(Schedulers.io());
//        subscribeOn(Schedulers.newThread());
//        subscribeOn(Schedulers.single());

        ExecutorService executor = Executors.newFixedThreadPool(10);
        subscribeOnExecutor(Schedulers.from(executor));
    }

    private static void subscribeOn(Scheduler scheduler) throws InterruptedException {

        Observable<String> source = Observable
            .just("🍝 pasta", "🍕 PIZZA", "🍟 fries", "🍛 CURRY", "🥗 salad")
            .subscribeOn(scheduler);

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available Processors: " + availableProcessors);

        Lets.disposeAfter(10000L, subscribe(source, availableProcessors));
    }

    private static void subscribeOnExecutor(Scheduler scheduler) throws InterruptedException {

        Observable<String> source = Observable
            .just("🍝 pasta", "🍕 PIZZA", "🍟 fries", "🍛 CURRY", "🥗 salad")
            .subscribeOn(scheduler)
            .doFinally(scheduler::shutdown);

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available Processors: " + availableProcessors);

        Lets.disposeAfter(10000, subscribe(source, availableProcessors));
    }

    private static Disposable[] subscribe(Observable<String> source, int availableProcessors) throws InterruptedException {
        int numOfThreads = availableProcessors + 1;
        Disposable[] disposables = new Disposable[numOfThreads];

        disposables[0] = source.subscribe(SchedulerDemo::compute);
//        Thread.sleep(1000);
        disposables[1] = source.subscribe(SchedulerDemo::compute);
        disposables[2] = source.subscribe(SchedulerDemo::compute);
        disposables[3] = source.subscribe(SchedulerDemo::compute);
        disposables[4] = source.subscribe(SchedulerDemo::compute);
        disposables[5] = source.subscribe(SchedulerDemo::compute);
        disposables[6] = source.subscribe(SchedulerDemo::compute);
        disposables[7] = source.subscribe(SchedulerDemo::compute);
        disposables[8] = source.subscribe(SchedulerDemo::compute);
        disposables[9] = source.subscribe(SchedulerDemo::compute);
        disposables[10] = source.subscribe(SchedulerDemo::compute);
        disposables[11] = source.subscribe(SchedulerDemo::compute);
        disposables[12] = source.subscribe(SchedulerDemo::compute);
        disposables[13] = source.subscribe(SchedulerDemo::compute);
        disposables[14] = source.subscribe(SchedulerDemo::compute);
        disposables[15] = source.subscribe(SchedulerDemo::compute);
        disposables[16] = source.subscribe(SchedulerDemo::compute);

        return disposables;
    }

    public static void compute(String e) throws InterruptedException {

//        Thread.sleep(new Random().nextInt(1000));
        Thread.sleep(100);
        System.out.println(e + "\t—•-> " + Thread.currentThread().getName());
    }
}
