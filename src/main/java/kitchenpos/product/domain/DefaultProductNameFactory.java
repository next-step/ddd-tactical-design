package kitchenpos.product.domain;

import static kitchenpos.product.support.constant.Name.NAME_CANDIDATE;
import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import kitchenpos.product.application.exception.ContainsProfanityException;
import kitchenpos.product.application.port.out.ProductPurgomalumChecker;

public class DefaultProductNameFactory implements ProductNameFactory {

    private final ProductPurgomalumChecker checker;

    public DefaultProductNameFactory(final ProductPurgomalumChecker checker) {
        this.checker = checker;
    }

    @Override
    public ProductName create(final Name nameCandidate) {
        checkNotNull(nameCandidate, NAME_CANDIDATE);

        if (checker.containsProfanity(nameCandidate)) {
            throw new ContainsProfanityException(nameCandidate);
        }

        return ProductName.of(nameCandidate);
    }
}
