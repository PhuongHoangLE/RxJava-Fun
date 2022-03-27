package com.phl.util;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class Lets {

    public static void dispose(Disposable... disposables) throws InterruptedException {
        disposeAfter(0, disposables);
    }

    public static void disposeAfter(long millis, Disposable... disposables) throws InterruptedException {
        Thread.sleep(millis);
        var cd = new CompositeDisposable();
        cd.addAll(disposables);
        cd.dispose();
    }
}
