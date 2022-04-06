package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityFilteredName;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

public class ProductFactory {
    private final PurgomalumClient purgomalumClient;

    public ProductFactory(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public Product createProduct(String name, Price price) {
        if(ObjectUtils.isEmpty(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        ProfanityFilteredName profanityFilteredName = ProfanityFilteredName.of(name);

        return new Product(UUID.randomUUID(), profanityFilteredName, price);
    }
}
