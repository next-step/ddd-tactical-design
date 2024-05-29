package kitchenpos.menus.tobe.infra;

import kitchenpos.common.infra.PurgomalumClient;
import kitchenpos.menus.tobe.domain.ProfanityChecker;
import org.springframework.stereotype.Component;

@Component
public class MenuProfanityChecker implements ProfanityChecker {
    private final PurgomalumClient purgomalumClient;

    public MenuProfanityChecker(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean containsProfanity(String text) {
        return purgomalumClient.containsProfanity(text);
    }
}
