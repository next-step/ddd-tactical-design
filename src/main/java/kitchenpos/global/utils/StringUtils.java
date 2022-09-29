package kitchenpos.global.utils;

import java.util.Objects;

public final class StringUtils {

    private StringUtils() {
    }

    public static boolean isBlank(String str) {
        return Objects.isNull(str) || str.isBlank();
    }
}
