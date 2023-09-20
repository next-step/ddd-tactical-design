package kitchenpos.products.tobe.domain;

import kitchenpos.common.DomainService;

import java.util.Objects;

@DomainService
public class ProductNamePolicy {
    private final ProductNameProfanities productNameProfanities;

    public ProductNamePolicy(ProductNameProfanities productNameProfanities) {
        this.productNameProfanities = productNameProfanities;
    }

    public void validateDisplayName(ProductName displayName) {
        if (Objects.isNull(displayName.getValue()) || this.containsProfanity(displayName)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean containsProfanity(ProductName productName) {
        return productNameProfanities.containsProfanity(productName.getValue());
    }
}
