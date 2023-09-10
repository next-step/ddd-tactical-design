package kitchenpos.common.values;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.web.client.HttpClientErrorException;

import static kitchenpos.common.exception.KitchenPosExceptionType.BAD_REQUEST;
import static kitchenpos.common.exception.KitchenPosExceptionType.METHOD_NOT_ALLOWED;
import static kitchenpos.util.KitchenPostExceptionAssertionUtils.assertEqualsExceptionType;
import static kitchenpos.util.KitchenPostExceptionAssertionUtils.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @Test
    void create_success() {
        final String name = "name";

        final Name actual = new Name(name);

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(name, actual.getValue())
        );
    }

    @Test
    void create_fail1() {
        assertThrows(METHOD_NOT_ALLOWED, Name::new);
    }

    @NullAndEmptySource
    @ParameterizedTest
    void create_fail2(String givenName) {
        assertThrows(BAD_REQUEST, () -> new Name(givenName));
    }

}
