package kitchenpos.product.tobe.domain.validate;

import kitchenpos.common.infra.PurgomalumClient;

public class ProfanityValidator {
    private final PurgomalumClient purgomalumClient;

    public ProfanityValidator(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public void validate(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException("비속어가 포함되어 있습니다: " + name);
        }
    }
}
