package com.example.demo.controller;

import com.example.demo.model.FiltersModel;
import com.example.demo.services.FinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PublicController {

    private final FinderService finderService;

    @PostMapping
    public List<String> getWordsByFilterObject(@RequestBody FiltersModel filters) {
        return finderService.findWordsWithFilterObject(filters);
    }

    @GetMapping
    public String getRandom() {
        return finderService.findRandom();
    }


    @GetMapping("param")
    public List<String> getWordsByParams(@RequestParam(value = "max", defaultValue = "") Long maxLetters,
                                         @RequestParam(value = "min", defaultValue = "") Long minLetters,
                                         @RequestParam(value = "start", defaultValue = "") String startLetters,
                                         @RequestParam(value = "end", defaultValue = "") String endLetters,
                                         @RequestParam(value = "with", defaultValue = "") String withLetters,
                                         @RequestParam(value = "without", defaultValue = "") String withoutLetters,
                                         @RequestParam(value = "withSeq", defaultValue = "") String withThisSequence,
                                         @RequestParam(value = "withoutSeq", defaultValue = "") String withoutThisSequence) {
        return finderService.findWordsWithFilterObject(
                FiltersModel.builder()
                        .maxNumLetters(maxLetters)
                        .minNumLetters(minLetters)
                        .startLetters(startLetters)
                        .endLetters(endLetters)
                        .withLetters(withLetters)
                        .withoutLetters(withoutLetters)
                        .withThisSequence(withThisSequence)
                        .withoutThisSequence(withoutThisSequence)
                        .build());
    }

}
