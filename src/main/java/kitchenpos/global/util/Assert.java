package kitchenpos.global.util;

import kitchenpos.global.exception.NonInstantiableException;

public final class Assert extends org.springframework.util.Assert {

    private Assert() {
        throw new NonInstantiableException();
    }

    // implement custom assertions...
}
