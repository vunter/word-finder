package com.example.demo.services.impl;

import com.example.demo.model.FiltersModel;
import com.example.demo.services.CacheService;
import com.example.demo.services.FinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class FinderCacheImpl implements FinderService {

    private final CacheService<String> cacheService;
    private final Random random;

    @Override
    public List<String> findWordsWithFilterObject(FiltersModel filtersModel) {
        return cacheService.getAllAsStream()
                .parallel()
                .filter(filtersModel.buildMainPredicate())
                .toList();
    }

    @Override
    public String findRandom() {
        var list = cacheService.getAllAsStream().toList();
        var value = random.nextInt(list.size() - 1);
        return list.get(value);
    }
}
