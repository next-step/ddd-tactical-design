package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("상품")
class ProductTest {

    private static final String 상품이름 = "상품이름";
    private static final ProductPrice 상품가격 = ProductPrice.from(10_000);

    @DisplayName("상품을 생성한다.")
    @Test
    void create() {
        // given
        // when
        Product actual = Product.from(상품이름, 상품가격);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(상품이름),
                () -> assertThat(actual.getPrice()).isEqualTo(상품가격.price())
        );
    }

    @DisplayName("상품의 가격을 바꾼다.")
    @Test
    void changePrice() {
        // given
        Product 상품 = Product.from(상품이름, 상품가격);
        BigDecimal newPrice = BigDecimal.valueOf(15_000);

        // when
        상품.changePrice(ProductPrice.from(newPrice));

        // then
        assertThat(상품.getPrice()).isEqualTo(newPrice);
    }
}
