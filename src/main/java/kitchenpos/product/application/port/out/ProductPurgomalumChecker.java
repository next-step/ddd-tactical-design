package kitchenpos.product.application.port.out;

import kitchenpos.support.vo.Name;

public interface ProductPurgomalumChecker {

    boolean containsProfanity(final Name name);
}
