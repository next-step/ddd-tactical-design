package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

public class ProductNameValidationService {
    private PurgomalumClient purgomalumClient;

    public ProductNameValidationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public boolean hasProfanity(String name) {
        return purgomalumClient.containsProfanity(name);
    }
}
