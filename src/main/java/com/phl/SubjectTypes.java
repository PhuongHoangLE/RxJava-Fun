package com.phl;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.Subject;

import java.util.function.Supplier;

public class SubjectTypes {

    public static void main(String[] args) throws InterruptedException {

//        subject(PublishSubject::create);    //NOTE: Television broadcasting
//        subject(ReplaySubject::create);     //NOTE: On-demand streaming e.g. YouTube
//        subject(BehaviorSubject::create);   //NOTE: Most recent item + subsequent items
//        subject(AsyncSubject::create);      //NOTE: Only last item e.g. last episode of a series
//        subject(UnicastSubject::create);    //NOTE: Buffered items + subsequent items
    }

    private static <T extends Subject<String>> void subject(Supplier<T> subjectSupplier) throws InterruptedException {
        Subject<String> subject = subjectSupplier.get();

        Disposable d1 = subject.subscribe(System.out::println);

        subject.onNext("1");
        subject.onNext("2");
        subject.onNext("3");

        Disposable d2 = subject.subscribe(e -> System.out.println("————— " + e));

        subject.onNext("4");
        subject.onNext("5");
        subject.onNext("6");
        subject.onComplete();

        Thread.sleep(6666);
        d1.dispose();
        d2.dispose();
    }
}
