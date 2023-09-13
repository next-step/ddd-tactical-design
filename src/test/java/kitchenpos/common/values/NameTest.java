package kitchenpos.common.values;

import kitchenpos.common.exception.KitchenPosException;
import kitchenpos.common.exception.KitchenPosExceptionType;
import kitchenpos.common.infra.FakePurgomalum;
import org.junit.jupiter.api.DisplayName;
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

    @DisplayName("[정상] 이름을 생성합니다.")
    @Test
    void create_success() {
        final String name = "name";

        final Name actual = new Name(name, FakePurgomalum.create());

        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(name, actual.getValue())
        );
    }

    @DisplayName("[예외] null이나 빈 값으로 이름을 생성할 수 없습니다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create_fail1(String givenName) {
        assertThrows(BAD_REQUEST, () -> new Name(givenName, FakePurgomalum.create()));
    }

}
