package kitchenpos.product.tobe.fake;

import kitchenpos.product.tobe.Profanities;

import java.util.List;

public class FakePurogmalumClient implements Profanities {
    private final List<String> profanities;

    public FakePurogmalumClient(String... profanities) {
        this.profanities = List.of(profanities);
    }

    @Override
    public boolean contains(String text) {
        return profanities.stream().anyMatch(text::contains);
    }
}
