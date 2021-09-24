package kitchenpos.products.tobe.domain;

import java.util.Arrays;
import java.util.List;

public class FakeProfanities implements Profanities {
    private final List<String> profanities = Arrays.asList("욕설", "비속어");

    @Override
    public boolean contains(String name) {
        return profanities.contains(name);
    }
}
