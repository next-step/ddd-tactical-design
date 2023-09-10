package kitchenpos.util;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class KitchenPostExceptionAssertionUtils {

    private KitchenPostExceptionAssertionUtils() {
    }

    public static void assertEqualsExceptionType(final KitchenPosException actualException, KitchenPosExceptionType expectedType) {
        assertTrue(
                () -> {
                    String message = actualException.getMessage();
                    return message.contains(expectedType.getMessage());
                }
        );
    }
}
