package kitchenpos.products.application;

import kitchenpos.Fixtures;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @DisplayName("상품의 가격이 올바르지 않으면 상품을 생성할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    public void create(final BigDecimal price) {
        assertThatThrownBy(() -> Fixtures.product("후라이드", price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 비어있으면 상품을 생성할 수 없다.")
    @NullSource
    @ParameterizedTest
    public void create(final String name) {
        assertThatThrownBy(() -> Fixtures.product(name, 16000L))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 변경한다.")
    @Test
    public void changePrice() {
        Product product = Fixtures.product("후라이드", 20000L);

        product.changePrice(BigDecimal.valueOf(16000L));

        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(16000L));
    }


    @DisplayName("상품의 가격이 올바르지 않으면 상품의 가격을 변경할 수 없다.")
    @ValueSource(strings = "-1000")
    @NullSource
    @ParameterizedTest
    public void changePrice(final BigDecimal price) {
        Product product = Fixtures.product("후라이드", 20000L);

        assertThatThrownBy(() -> product.changePrice(price))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 개수만큼 가격을 구한다.")
    @Test
    public void multiply() {
        Product product = Fixtures.product("후라이드", 20000L);

        BigDecimal bigDecimal = product.multiplyPrice(2);

        assertThat(bigDecimal).isEqualTo(BigDecimal.valueOf(40000L));
    }

}
