package com.example.demo.services;

import com.example.demo.model.FiltersModel;

import java.util.List;

public interface FinderService {

    List<String> findWordsWithFilterObject(FiltersModel filtersModel);
    String findRandom();

}
