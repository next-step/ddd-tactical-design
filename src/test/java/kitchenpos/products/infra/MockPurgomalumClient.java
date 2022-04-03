package kitchenpos.products.infra;

import java.util.Arrays;
import java.util.List;

public class MockPurgomalumClient implements PurgomalumClient {

    private static final List<String> profanities = Arrays.asList("fuck", "shit");

    @Override
    public boolean containsProfanity(String text) {
        String target = text.toLowerCase();
        return profanities.stream().anyMatch(target::contains);
    }
}
