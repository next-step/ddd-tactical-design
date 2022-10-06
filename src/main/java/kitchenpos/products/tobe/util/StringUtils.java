package kitchenpos.products.tobe.util;

public class StringUtils {

    private StringUtils() {
    }

    public static boolean isEmpty(String name) {
        return name == null ||"".equals(name);
    }
}
