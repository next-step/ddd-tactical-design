package kitchenpos.products.tobe.domain.service;

import static kitchenpos.common.exception.ExceptionDetails.DISPLAYED_NAME_INCLUDE_PROFANITY_EXCEPTION;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.exception.DisplayedNameIncludeProfanityException;
import org.springframework.stereotype.Component;

@Component
public class ProfanityFilterService {

    private final PurgomalumClient purgomalumClient;

    public ProfanityFilterService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public void validateProfanity(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new DisplayedNameIncludeProfanityException(
                DISPLAYED_NAME_INCLUDE_PROFANITY_EXCEPTION.getMessage());
        }
    }
}
