package kitchenpos.products.tobe.domain;

import kitchenpos.products.exception.InvalidProductPriceException;
import kitchenpos.support.domain.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("상품")
class ProductTest {

    private static final ProductName 상품이름 = ProductName.from("상품 이름");
    private static final ProductPrice 상품가격 = ProductPrice.from(10_000);

    @DisplayName("[성공] 상품을 생성한다.")
    @Test
    void create() {
        Product actual = Product.of(상품이름, 상품가격);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(상품이름.nameValue()),
                () -> assertThat(actual.getPrice()).isEqualTo(상품가격.priceValue())
        );
    }

    @DisplayName("[성공] 상품의 가격을 바꾼다.")
    @Test
    void changePrice() {
        Product product = Product.of(상품이름, 상품가격);
        BigDecimal newPrice = BigDecimal.valueOf(15_000);

        product.changePrice(ProductPrice.from(newPrice));

        assertThat(product.getPrice()).isEqualTo(newPrice);
    }


    @DisplayName("[성공] 상품의 가격에 숫자를 곱한다.")
    @Test
    void priceMultiple() {
        Product product = Product.of(상품이름, 상품가격);

        BigDecimal actual = product.priceMultiple(BigDecimal.TEN);

        assertThat(actual).isEqualTo(BigDecimal.valueOf(100_000));
    }

    @DisplayName("[실패] 상품의 가격에 숫자를 곱한 결과는 0원 이상이어야 한다.")
    @ValueSource(strings = {"-1", "-2", "-10"})
    @ParameterizedTest
    void fail_priceMultiple(BigDecimal input) {
        Product product = Product.of(상품이름, 상품가격);

        assertThatThrownBy(() -> product.priceMultiple(input))
                .isInstanceOf(InvalidProductPriceException.class);
    }
}
