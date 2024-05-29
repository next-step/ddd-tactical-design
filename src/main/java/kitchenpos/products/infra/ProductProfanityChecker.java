package kitchenpos.products.infra;

import kitchenpos.support.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProfanityChecker;
import org.springframework.stereotype.Component;

@Component
public class ProductProfanityChecker implements ProfanityChecker {
    private final PurgomalumClient purgomalumClient;

    public ProductProfanityChecker(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    @Override
    public boolean containsProfanity(String text) {
        return purgomalumClient.containsProfanity(text);
    }
}
