package com.phl.CombiningObservables;

import io.reactivex.rxjava3.core.Observable;

public class Grouping {

    public static void main(String[] args) {

        Observable
            .just(
                new Person(1, "Paul", "Smith", 40),
                new Person(2, "Dan", "Smith", 38),
                new Person(3, "Mike", "Jackson", 20),
                new Person(4, "Laura", "Henderson", 16),
                new Person(5, "John", "Smith", 60)
            )
            .groupBy(Person::lastName)
            .flatMapSingle(e -> e.toMultimap(person -> e.getKey(), Person::firstName))
            .subscribe(System.out::println)
            .dispose();
    }

    record Person(Integer id, String firstName, String lastName, Integer age) {
    }
}
