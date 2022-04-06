package kitchenpos.products.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void constructor() {
        assertDoesNotThrow(() -> product(14000));
    }

    @DisplayName("상품의 가격은 0 보다 작을 수 없다.")
    @Test
    void negativePrice() {
        assertThatThrownBy(() -> product(-100))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 0원 이상이어야 합니다.");
    }

    @DisplayName("상품의 가격은 입력하지 않을 수 없다.")
    @ParameterizedTest
    @NullSource
    void nullPrice(BigDecimal price) {
        assertThatThrownBy(() -> new Product("엽기떡볶이", price, new FakePurgomalumClient()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 0원 이상이어야 합니다.");
    }

    @DisplayName("상품의 가격은 입력하지 않을 수 없다.")
    @ParameterizedTest
    @NullSource
    void nullName(String name) {
        assertThatThrownBy(() -> new Product(name, BigDecimal.valueOf(12000), new FakePurgomalumClient()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름으로 사용할 수 없습니다.");
    }

    @DisplayName("상품의 이름에 금지어가 포함될 수 없다.")
    @Test
    void forbiddenName() {
        assertThatThrownBy(() -> new Product("비속어", BigDecimal.valueOf(0), new FakePurgomalumClient()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 이름으로 사용할 수 없습니다.");
    }

    @DisplayName("상품의 가격을 변경할 수 있다.")
    @Test
    void changePrice() {
        BigDecimal newPrice = BigDecimal.valueOf(14000);
        Product product = product(10000);

        product.changePrice(newPrice);

        assertThat(product.getPrice()).isEqualTo(new ProductPrice(newPrice));
    }

    @DisplayName("상품의 가격은 0원 이하로 변경할 수 없다.")
    @Test
    void changeNegativePrice() {
        Product product = product(10000);

        assertThatThrownBy(() -> product.changePrice(BigDecimal.valueOf(-100)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품의 가격은 0원 이상이어야 합니다.");
    }

    @DisplayName("상품의 가격과 수량을 곱한 결과값을 알 수 있다.")
    @Test
    void multiplyPrice() {
        Product product = product(20000);

        assertThat(product.multiplyPrice(BigDecimal.valueOf(5))).isEqualTo(BigDecimal.valueOf(100000));
    }

    private Product product(Integer price) {
        return new Product("엽기떡볶이", BigDecimal.valueOf(price), new FakePurgomalumClient());
    }
}
