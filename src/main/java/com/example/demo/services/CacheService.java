package com.example.demo.services;

import java.util.List;
import java.util.stream.Stream;

public interface CacheService<T> {


    boolean add(T value);

    boolean remove(T value);

    boolean contains(T value);

    T getValue(T value);

    List<T> getAll();

    Stream<T> getAllAsStream();

}
