package kitchenpos.eatinorders.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeliveryAddressTest {
    @Test
    @DisplayName("주소가 같으면 같은 주소이다.")
    void equals() {
        final DeliveryAddress deliveryAddress = new DeliveryAddress("서울시 강남구");
        final DeliveryAddress sameDeliveryAddress = new DeliveryAddress("서울시 강남구");

        assertEquals(deliveryAddress, sameDeliveryAddress);
    }
}
