package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.common.annotations.Validator;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProfanityValidator;

@Validator
public class DefaultProfanityValidator implements ProfanityValidator {

    private final PurgomalumClient purgomalumClient;

    public DefaultProfanityValidator(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public boolean validate(String name) {
        return !purgomalumClient.containsProfanity(name);
    }

}
