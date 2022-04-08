package kitchenpos.util;

public class StringUtils {

    private StringUtils() { }

    public static boolean isBlank(String source) {
        return source == null || source.trim().length() == 0;
    }
}
