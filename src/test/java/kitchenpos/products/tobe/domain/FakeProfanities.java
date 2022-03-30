package kitchenpos.products.tobe.domain;

import java.util.stream.Stream;

public class FakeProfanities implements Profanities {

    @Override
    public boolean contains(final String text) {
        return Stream.of("욕설", "비속어").anyMatch(text::contains);
    }

}
