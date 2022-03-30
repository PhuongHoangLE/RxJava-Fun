# Observables and Observers

**Java Stream (Sync)**

```java
stream
    .map(s -> s + "transformed")
    .forEach(System.out::println);
```

**RxJava (Async)**

```java
observable                              // input stream
    .map(s -> s + "transformed")        // operator
    .subscribe(System.out::println);    // output stream
```

```java
interface Observable<T> {
    Subscription subscribe(Observer o);
}
```

## Demo

### [Observable and Observer](ObservableAndObserver.java)

### [Hot and Cold Observables](HotAndColdObservables.java)

### [Observable Variants](ObservableVariants.java)

### [Disposing Resources](DisposingResources.java)

