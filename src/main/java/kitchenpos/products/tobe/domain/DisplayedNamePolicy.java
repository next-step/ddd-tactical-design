package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DisplayedNamePolicy {
    private final PurgomalumClient purgomalumClient;

    public DisplayedNamePolicy(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public void validateDisplayName(ProductDisplayedName displayName) {
        if (Objects.isNull(displayName.getValue()) || this.containsProfanity(displayName)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean containsProfanity(ProductDisplayedName productDisplayedName) {
        return purgomalumClient.containsProfanity(productDisplayedName.getValue());
    }
}
