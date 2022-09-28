package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import kitchenpos.tobe.Fixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    @DisplayName("UUID가 일치하면 동일한 상품으로 취급한다.")
    @Test
    void sameUUID() {
        // given
        UUID id = UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d");
        Product apple = Fixtures.product(
            id,
            "사과",
            3_000
        );
        Product banana = Fixtures.product(
            id,
            "바나나",
            5_000
        );

        // when
        boolean result = apple.equals(banana);

        // then
        assertThat(result).isTrue();
    }
}
