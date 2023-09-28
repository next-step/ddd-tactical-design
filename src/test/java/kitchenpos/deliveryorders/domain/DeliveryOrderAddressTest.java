package kitchenpos.deliveryorders.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeliveryOrderAddressTest {

    @DisplayName("배달 주문의 주소는 필수값이다.")
    @ParameterizedTest
    @NullAndEmptySource
    void delivery_address_of_delivery_order_should_be_exist(String deliveryAddress) {
        assertThatThrownBy(() -> new DeliveryOrderAddress(deliveryAddress))
                .isInstanceOf(IllegalArgumentException.class);
    }
}