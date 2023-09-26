package kitchenpos.products.tobe.domain;

import kitchenpos.products.infra.PurgomalumClient;

import java.math.BigDecimal;
import java.util.Objects;

public class ProductValidator {
    private final NameValidator nameValidator;
    private final PriceValidator priceValidator;

    public ProductValidator(String name, BigDecimal price, PurgomalumClient purgomalumClient) {
        this.nameValidator = new NameValidator(name, purgomalumClient);
        this.priceValidator = new PriceValidator(price);
    }

    public NameValidator getNameValidator() {
        return nameValidator;
    }

    public PriceValidator getPriceValidator() {
        return priceValidator;
    }
}
