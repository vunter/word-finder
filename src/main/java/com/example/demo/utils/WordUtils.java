package com.example.demo.utils;

import com.example.demo.model.FiltersModel;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.apache.logging.log4j.util.Strings;

@UtilityClass
public class WordUtils {

    public void removeContainsConflict(@NonNull FiltersModel filters) {
        if (Strings.isNotBlank(filters.getWithoutLetters())) {
            for (String withoutLetter : filters.getWithoutLetters().split("")) {
                if (filters.getWithLetters().contains(withoutLetter)
                        || filters.getStartLetters().contains(withoutLetter)
                        || filters.getEndLetters().contains(withoutLetter)
                        || filters.getWithThisSequence().contains(withoutLetter))
                    filters.setWithoutLetters(filters.getWithoutLetters().replace(withoutLetter, ""));
            }
        }
    }

}
