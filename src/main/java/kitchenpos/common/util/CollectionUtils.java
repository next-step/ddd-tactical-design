package kitchenpos.common.util;

import kitchenpos.common.exception.KitchenPosException;
import org.springframework.lang.Nullable;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static void requireNonEmpty(@Nullable Collection<?> collection, KitchenPosException exception) {
        if (collection == null || collection.isEmpty()) {
            throw exception;
        }
    }

}
