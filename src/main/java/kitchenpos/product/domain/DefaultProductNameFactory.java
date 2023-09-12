package kitchenpos.product.domain;

import kitchenpos.product.application.port.out.ProductPurgomalumChecker;

public class DefaultProductNameFactory implements ProductNameFactory {

    private final ProductPurgomalumChecker checker;

    public DefaultProductNameFactory(final ProductPurgomalumChecker checker) {
        this.checker = checker;
    }

    @Override
    public ProductName create(final Name nameCandidate) {
        if (!checker.containsProfanity(nameCandidate)) {
            throw new IllegalArgumentException(
                String.format("name has profanity. name: %s", nameCandidate));
        }

        return ProductName.of(nameCandidate);
    }
}
