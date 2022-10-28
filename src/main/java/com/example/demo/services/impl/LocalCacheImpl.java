package com.example.demo.services.impl;


import com.example.demo.services.CacheService;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Stream;


@Data
@Service
public class LocalCacheImpl<T> implements CacheService<T> {

    private ConcurrentSkipListSet<T> cache = new ConcurrentSkipListSet<>();

    @Override
    public boolean add(T value) {
        return cache.add(value);
    }

    @Override
    public boolean remove(T value) {
        return cache.remove(value);
    }

    @Override
    public boolean contains(T value) {
        return cache.contains(value);
    }

    @Override
    public T getValue(T value) {
        return cache.parallelStream()
                .parallel()
                .filter(o -> o.equals(value))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<T> getAll() {
        return cache.stream().toList();
    }

    @Override
    public Stream<T> getAllAsStream() {
        return cache.stream();
    }
}
