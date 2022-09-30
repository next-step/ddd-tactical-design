package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class ProductTest {

    @DisplayName("식별자가 같으면 동등한 상품이다.")
    @Test
    void equals() {
        UUID id = UUID.randomUUID();

        assertThat(new Product(id, "후라이드 치킨", 9_000L))
                .isEqualTo(new Product(id, "Fried Chicken", 9_000L));
    }
}
