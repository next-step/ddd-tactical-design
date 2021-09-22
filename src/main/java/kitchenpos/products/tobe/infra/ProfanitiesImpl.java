package kitchenpos.products.tobe.infra;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Profanities;
import org.springframework.stereotype.Component;

@Component
public class ProfanitiesImpl implements Profanities {
    private final PurgomalumClient purgomalumClient;

    public ProfanitiesImpl(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean contains(String value) {
        if (purgomalumClient.containsProfanity(value)) {
            return true;
        }
        return false;
    }
}
