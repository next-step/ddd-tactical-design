package kitchenpos.support;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.List;
import org.apache.commons.collections4.CollectionUtils;

/**
 * 해당 클래스는 파라미터에 대한 유효성 검사를 위한 것이므로, 비즈니스 종속적인 곳에서는 사용하면 안된다.
 */
public final class ParameterValidateUtils {

    public static <T> T checkNotNull(final T value, final String name) {
        checkArgument(value != null, "null %s", name);

        return value;
    }

    public static <E> List<E> checkNotEmpty(final List<E> collection, final String name) {
        checkArgument(CollectionUtils.isNotEmpty(collection), "%s is empty", name);

        return collection;
    }

    private ParameterValidateUtils() {
        throw new UnsupportedOperationException();
    }
}
