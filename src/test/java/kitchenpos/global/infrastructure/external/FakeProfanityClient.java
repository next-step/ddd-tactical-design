package kitchenpos.global.infrastructure.external;

import java.util.List;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;
import kitchenpos.product.domain.service.ProductPurgomalumClient;

public class FakeProfanityClient implements MenuPurgomalumClient, ProductPurgomalumClient  {

    private final List<String> profanities;

    public FakeProfanityClient() {
        this(List.of());
    }

    public FakeProfanityClient(List<String> profanities) {
        this.profanities = profanities;
    }

    @Override
    public boolean containsProfanity(String text) {
        return profanities.stream()
            .anyMatch(profanity -> profanity.contains(text));
    }
}
