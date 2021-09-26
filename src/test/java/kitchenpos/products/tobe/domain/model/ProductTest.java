package kitchenpos.products.tobe.domain.model;

import kitchenpos.common.FakeProfanities;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProductTest {

    @DisplayName("생성 검증")
    @Test
    void create() {
        Assertions.assertDoesNotThrow(() -> new Product(new Name("후라이드", new FakeProfanities()), new Price(BigDecimal.valueOf(10000L))));
    }

    @DisplayName("가격 변경 검증")
    @Test
    void changePrice() {
        Product product = new Product(new Name("후라이드", new FakeProfanities()), new Price(BigDecimal.valueOf(10000L)));
        product.changePrice(BigDecimal.valueOf(9000L));
        org.assertj.core.api.Assertions.assertThat(product.getPrice()).isEqualTo(new Price(BigDecimal.valueOf(9000L)));
    }

}
