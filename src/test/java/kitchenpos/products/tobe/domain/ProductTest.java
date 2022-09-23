package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {
    @DisplayName("상품을 생성한다.")
    @Test
    void create() {
        assertDoesNotThrow(() -> new Product("후라이드 치킨", 20000));
    }

    @DisplayName("이름이 null이거나 빈칸인 상품을 생성할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createWithNullOrEmptyName(String name) {
        assertThatThrownBy(() -> new Product(name, 20000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격이 음수인 상품을 생성할 수 없다.")
    @Test
    void createWithNegativePrice() {
        assertThatThrownBy(() -> new Product("후라이드 치킨", -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경한다.")
    @Test
    void changePrice() {
        Product product = new Product("후라이드 치킨", 20000);

        product.changePrice(10000);

        assertThat(product.getPrice()).isEqualTo(new Price(10000));
    }

    @DisplayName("상품의 가격을 음수로 변경할 수 없다.")
    @Test
    void changePriceWithNegativePrice() {
        Product product = new Product("후라이드 치킨", 20000);

        product.changePrice(10000);

        assertThatThrownBy(() -> product.changePrice(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
