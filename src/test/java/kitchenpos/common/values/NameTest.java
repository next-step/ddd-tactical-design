package kitchenpos.common.values;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;
import kitchenpos.util.KitchenPostExceptionAssertionUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import javax.validation.constraints.Null;

import static kitchenpos.util.KitchenPostExceptionAssertionUtils.assertEqualsExceptionType;
import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void create_success() {
        final String name = "name";

        final Name actual = new Name(name);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(name, actual.getName())
        );
    }

    @Test
    void create_fail1() {
        KitchenPosException kitchenPosException = assertThrows(KitchenPosException.class, Name::new);
        assertEqualsExceptionType(kitchenPosException, KitchenPosExceptionType.METHOD_NOT_ALLOWED);
    }

    @NullAndEmptySource
    @ParameterizedTest
    void create_fail2(String givenName) {
        KitchenPosException kitchenPosException = assertThrows(KitchenPosException.class, () -> new Name(givenName));
        assertEqualsExceptionType(kitchenPosException, KitchenPosExceptionType.BAD_REQUEST);
    }

}
