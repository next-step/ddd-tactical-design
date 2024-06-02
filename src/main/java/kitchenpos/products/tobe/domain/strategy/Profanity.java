package kitchenpos.products.tobe.domain.strategy;

import java.util.ArrayList;
import java.util.List;

public class Profanity {

    public void validate(String word) {
        List<String> wrongWords = new ArrayList<>();
        wrongWords.add("wrong-name");

        if (wrongWords.contains(word)) {
            throw new IllegalArgumentException("The word '" + word + "' is not allowed.");
        }
    }
}
