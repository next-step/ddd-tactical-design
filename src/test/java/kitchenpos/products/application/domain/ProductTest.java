package kitchenpos.products.application.domain;

import kitchenpos.Fixtures;
import kitchenpos.products.domain.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProductTest {

    @Test
    void changePrice() {
        //given
        Product before = Fixtures.product("아무상품", 150_000L);
        BigDecimal afterPrice = BigDecimal.valueOf(200_000L);
        //when
        Product after = before.changePrice(afterPrice);

        //then
        Assertions.assertThat(after.getPrice()).isEqualTo(afterPrice);
    }
}
