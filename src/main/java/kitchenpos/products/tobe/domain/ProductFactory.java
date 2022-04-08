package kitchenpos.products.tobe.domain;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityFilteredName;
import kitchenpos.common.domain.ProfanityFilteredNameFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductFactory {

    private ProductFactory() {
    }

    public static Product createProduct(String name, Price price) {
        ProfanityFilteredName profanityFilteredName = ProfanityFilteredNameFactory.createProfanityFilteredName(name);

        return new Product(UUID.randomUUID(), profanityFilteredName, price);
    }
}
