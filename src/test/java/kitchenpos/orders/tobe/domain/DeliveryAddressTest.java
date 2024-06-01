package kitchenpos.orders.tobe.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class DeliveryAddressTest {
    @ParameterizedTest
    @NullAndEmptySource
    @DisplayName("배송 주소가 null이거나 비어있으면 배송 주소 객체를 생성할 수 없다.")
    void deliveryAddressOfNullOrEmpty(String invalidAddress) {
        // When & Then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new DeliveryAddress(invalidAddress))
            .withMessage(DeliveryAddress.NULL_OR_EMPTY_ADDRESS_ERROR);
    }

    @Test
    @DisplayName("유효한 배송 주소를 입력하면 배송 주소 객체가 정상적으로 생성된다.")
    void deliveryAddress() {
        // Given
        String validAddress = "서울시 송파구 위례성대로 2";

        // When
        DeliveryAddress deliveryAddress = new DeliveryAddress(validAddress);

        // Then
        assertEquals(validAddress, deliveryAddress.getValue());
    }
}
