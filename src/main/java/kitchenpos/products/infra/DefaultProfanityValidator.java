package kitchenpos.products.infra;

import kitchenpos.products.domain.ProfanityValidator;

public class DefaultProfanityValidator implements ProfanityValidator {
    private final PurgomalumClient purgomalumClient;

    public DefaultProfanityValidator(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean isHas(String word) {
        return purgomalumClient.containsProfanity(word);
    }
}
