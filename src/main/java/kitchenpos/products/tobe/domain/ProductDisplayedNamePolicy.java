package kitchenpos.products.tobe.domain;

import kitchenpos.common.DomainService;

import java.util.Objects;

@DomainService
public class ProductDisplayedNamePolicy {
    private final ProductDisplayedNameProfanities productDisplayedNameProfanities;

    public ProductDisplayedNamePolicy(ProductDisplayedNameProfanities productDisplayedNameProfanities) {
        this.productDisplayedNameProfanities = productDisplayedNameProfanities;
    }

    public void validateDisplayName(ProductDisplayedName displayName) {
        if (Objects.isNull(displayName.getValue()) || this.containsProfanity(displayName)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean containsProfanity(ProductDisplayedName productDisplayedName) {
        return productDisplayedNameProfanities.containsProfanity(productDisplayedName.getValue());
    }
}
