package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TobeProductTest {

    @DisplayName("상품의 가격을 변경한다.")
    @Test
    void changePrice01() {
        TobeProduct product = new TobeProduct(UUID.randomUUID(), "후라이드 치킨", BigDecimal.valueOf(10000));

        product.changePrice(BigDecimal.valueOf(15000));

        assertThat(product.getPrice()).isEqualTo(new ProductPrice(BigDecimal.valueOf(15000)));
    }
}
