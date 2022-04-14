package kitchenpos.products.infrastructure;

import kitchenpos.products.domain.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class DefaultPurgomalumClient implements PurgomalumClient {

    @Override
    public boolean containsProfanity(String name) {
        return false;
    }
}
