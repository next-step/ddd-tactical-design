package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProfanityChecker;
import org.springframework.stereotype.Component;

@Component
public class DefaultProfanityChecker implements ProfanityChecker {

    private final PurgomalumClient purgomalumClient;

    public DefaultProfanityChecker(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean containsProfanity(String text) {
        return purgomalumClient.containsProfanity(text);
    }
}
