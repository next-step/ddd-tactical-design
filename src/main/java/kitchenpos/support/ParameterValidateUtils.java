package kitchenpos.support;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * 해당 클래스는 파라미터에 대한 유효성 검사를 위한 것이므로, 비즈니스 종속적인 곳에서는 사용하면 안된다.
 */
public final class ParameterValidateUtils {

    public static <T> T checkNotNull(final T value, final String name) {
        checkArgument(value != null, "null %s", name);

        return value;
    }

    private ParameterValidateUtils() {
        throw new UnsupportedOperationException();
    }
}
