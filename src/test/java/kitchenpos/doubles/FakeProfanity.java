package kitchenpos.doubles;

import kitchenpos.common.Profanity;

import java.util.List;

public class FakeProfanity implements Profanity {

    private static final List<String> PROFANE_WORDS = List.of("바보", "멍청이");

    @Override
    public boolean contains(String text) {
        return PROFANE_WORDS.stream()
                .anyMatch(text::contains);
    }
}
