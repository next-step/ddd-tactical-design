package kitchenpos.product.application.port.out;

import kitchenpos.product.domain.Name;

public interface ProductPurgomalumChecker {

    boolean containsProfanity(final Name name);
}
