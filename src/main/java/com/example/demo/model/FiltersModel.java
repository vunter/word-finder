package com.example.demo.model;

import com.example.demo.utils.WordUtils;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

@Data
@Builder
public class FiltersModel implements Serializable {

    private String startLetters;
    private String endLetters;
    private String withLetters;
    private String withoutLetters;
    private String withThisSequence;
    private String withoutThisSequence;
    private Long minNumLetters;
    private Long maxNumLetters;


    public Predicate<String> buildMainPredicate() {
        WordUtils.removeContainsConflict(this);
        List<Predicate<String>> predicates = new ArrayList<>();

        if (Strings.isNotBlank(getStartLetters()))
            predicates.add(getStartWithPredicate());

        if (Strings.isNotBlank(getEndLetters()))
            predicates.add(getEndWithPredicate());

        if (Strings.isNotBlank(getWithLetters()))
            predicates.add(getWithLettersPredicate());

        if (Strings.isNotBlank(getWithoutLetters()))
            predicates.add(getWithoutLettersPredicate());

        if (Strings.isNotBlank(getWithThisSequence()))
            predicates.add(getWithThisSequencePredicate());

        if (Strings.isNotBlank(getWithoutThisSequence()))
            predicates.add(getWithoutThisSequencePredicate());

        if (Objects.nonNull(getMinNumLetters()))
            predicates.add(getMinNumLettersPredicate());

        if (Objects.nonNull(getMaxNumLetters()))
            predicates.add(getMaxNumLettersPredicate());

        Predicate<String> mainPredicate = str -> true;
        for (Predicate<String> predicate : predicates) {
            mainPredicate = mainPredicate.and(predicate);
        }
        return mainPredicate;
    }

    public Predicate<String> getMaxNumLettersPredicate() {
        return str -> str.length() <= getMaxNumLetters();
    }

    public Predicate<String> getMinNumLettersPredicate() {
        return str -> str.length() >= getMinNumLetters();
    }

    public Predicate<String> getWithoutThisSequencePredicate() {
        return str -> !str.contains(this.getWithoutThisSequence());
    }

    public Predicate<String> getWithThisSequencePredicate() {
        return str -> str.contains(this.getWithThisSequence());
    }

    public Predicate<String> getWithoutLettersPredicate() {
        return str -> {
            var asCharArr = getWithoutLetters().toCharArray();
            for (char letter : asCharArr) {
                for (int i = 0; i < str.length(); i++) {
                    var point = str.charAt(i);
                    if (point == letter) {
                        return false;
                    }
                }
            }
            return true;
        };
    }

    public Predicate<String> getWithLettersPredicate() {
        return str -> {
            List<Boolean> foundMarker = new ArrayList<>();
            var asCharArr = getWithLetters().toCharArray();
            for (char letter : asCharArr) {
                for (int i = 0; i < str.length(); i++) {
                    var point = str.charAt(i);
                    if (point == letter) {
                        foundMarker.add(true);
                        break;
                    }
                }
            }
            return !foundMarker.isEmpty() && foundMarker.size() == asCharArr.length;
        };
    }

    public Predicate<String> getEndWithPredicate() {
        return str -> str.endsWith(this.getEndLetters());
    }

    public Predicate<String> getStartWithPredicate() {
        return str -> str.startsWith(this.getStartLetters());
    }

    public String getStartLetters() {
        return Objects.nonNull(startLetters) ? startLetters : "";
    }

    public String getEndLetters() {
        return Objects.nonNull(endLetters) ? endLetters : "";
    }

    public String getWithLetters() {
        return Objects.nonNull(withLetters) ? withLetters : "";
    }

    public String getWithThisSequence() {
        return Objects.nonNull(withThisSequence) ? withThisSequence : "";
    }
}
