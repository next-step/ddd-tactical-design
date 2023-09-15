package kitchenpos.util;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class KitchenPostExceptionAssertionUtils {

    private KitchenPostExceptionAssertionUtils() {
    }

    public static void assertThrows(KitchenPosExceptionType expectedType, Executable executable) {
        KitchenPosException actualException = Assertions.assertThrows(KitchenPosException.class, executable);
        assertEqualsExceptionType(actualException, expectedType);
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
