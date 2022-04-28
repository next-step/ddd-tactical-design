package kitchenpos.eatinorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryAddressTest {

    @DisplayName("배달 주소를 생성한다")
    @Test
    void create() {
        assertThatCode(() -> new DeliveryAddress("서울시 마포구"))
                .doesNotThrowAnyException();
    }

    @DisplayName("배달 주소는 비어있지 않아야 한다")
    @ParameterizedTest
    @NullAndEmptySource
    void createNullAndEmpty(String address) {
        assertThatThrownBy(() -> new DeliveryAddress(address))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("배달 주소는 빈 값이 아니어야 합니다. 입력 값 : " + address);
    }
}
