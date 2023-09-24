package kitchenpos.deliveryorders.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeliveryAddressTest {

    @DisplayName("배달 주소는 빈 값이 될 수 없다")
    @ParameterizedTest
    @NullAndEmptySource
    void nullAndEmpty(String deliveryAddress) {
        assertThatThrownBy(() -> new DeliveryAddress(deliveryAddress))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("배달 주소를 생성할 수 있다")
    @Test
    void constructor() {
        DeliveryAddress actual = new DeliveryAddress("주소");
        assertThat(actual).isNotNull();
    }
}
