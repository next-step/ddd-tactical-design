package kitchenpos.products.tobe.domain;

import kitchenpos.commons.ValueObject;

public class Name extends ValueObject<Name> {
    private String value;

    public Name(String value, final Profanities profanities) {
        if (profanities.contains(value)) {
            throw new IllegalArgumentException("욕설 포함");
        }
        this.value = value;
    }
}
