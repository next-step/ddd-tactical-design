package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.ProfanityChecker;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component("tobeMenuContextProfanityChecker")
public class DefaultProfanityChecker implements ProfanityChecker {

    private final PurgomalumClient purgomalumClient;

    public DefaultProfanityChecker(final PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean containsProfanity(final String text) {
        return purgomalumClient.containsProfanity(text);
    }
}
