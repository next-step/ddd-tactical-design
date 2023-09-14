package kitchenpos.product.application.exception;

import kitchenpos.product.domain.Name;

public final class ContainsProfanityException extends IllegalArgumentException {

    public ContainsProfanityException(final Name name) {
        super(String.format("name contains profanity. name: %s", name));
    }
}
