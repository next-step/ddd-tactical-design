package kitchenpos.product.domain.model;

import static kitchenpos.product.exception.ProductExceptionMessage.PRODUCT_NAME_VALIDATION_EXCEPTION;

import kitchenpos.common.application.PurgomalumClient;
import org.springframework.stereotype.Service;

@Service
public class ProductNameCreationService {
    private final PurgomalumClient purgomalumClient;

    public ProductNameCreationService(PurgomalumClient purgomalumClient) {
        this.purgomalumClient = purgomalumClient;
    }

    public ProductName createName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException(PRODUCT_NAME_VALIDATION_EXCEPTION.getMessage());
        }
        return new ProductName(name);
    }
}
