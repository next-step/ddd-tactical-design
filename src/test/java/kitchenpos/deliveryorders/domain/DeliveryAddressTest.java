package kitchenpos.deliveryorders.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class DeliveryAddressTest {
    @DisplayName("배달 주소는 필수 값입니다.")
    @ParameterizedTest
    @NullAndEmptySource
    void name(String address) {
        assertThatThrownBy(() -> DeliveryAddress.of(address))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("배달 주소는 비워 둘 수 없습니다.");
    }
}
