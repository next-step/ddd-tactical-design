package kitchenpos.products.application;

import kitchenpos.products.domain.ProfanityValidator;
import kitchenpos.products.infra.PurgomalumClient;

public class FakeProfanityValidator implements ProfanityValidator {
    private final PurgomalumClient purgomalumClient;

    public FakeProfanityValidator(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean isHas(String word) {
        return purgomalumClient.containsProfanity(word);
    }
}
