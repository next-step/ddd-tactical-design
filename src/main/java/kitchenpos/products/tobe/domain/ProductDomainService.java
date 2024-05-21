package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

public class ProductDomainService {
    private PurgomalumClient purgomalumClient;

    public ProductDomainService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public boolean hasProfanity(String name) {
        return purgomalumClient.containsProfanity(name);
    }
}
