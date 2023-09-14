package kitchenpos.support;

import static com.google.common.base.Preconditions.checkArgument;

public final class ParameterValidateUtils {

    public static <T> T checkNotNull(final T value, final String name) {
        checkArgument(value != null, "null %s", name);

        return value;
    }

    private ParameterValidateUtils() {
        throw new UnsupportedOperationException();
    }
}
