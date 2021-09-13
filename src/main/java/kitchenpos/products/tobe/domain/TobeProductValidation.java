package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Component;

@Component
public class TobeProductValidation {
    private final PurgomalumClient purgomalumClient;

    public TobeProductValidation(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public void vaildationName(ProductName name) {
        if (purgomalumClient.containsProfanity(name.getName())) {
            throw new IllegalArgumentException();
        }
    }
}
