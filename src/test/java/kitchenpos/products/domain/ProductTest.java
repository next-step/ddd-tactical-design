package kitchenpos.products.domain;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductTest {

    @DisplayName("상품의 가격이 유효하지 않다")
    @ParameterizedTest()
    @ValueSource(longs = {-1L, -2L})
    void invalid_price_product(long price) {
        assertThatThrownBy(
                () -> new Product(UUID.randomUUID(), "후라이드", BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 이름이 유효하지 않다")
    @ParameterizedTest
    @NullAndEmptySource
    void invalid_name_product(String name) {
        assertThatThrownBy(
                () -> new Product(UUID.randomUUID(), name, BigDecimal.valueOf(15000L))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격을 null로 변경하는 것을 실패한다")
    @ParameterizedTest
    @NullSource
    void invalid_change_price(BigDecimal price){
        Product product = product("후라이드", 15000L);
        assertThatThrownBy(
                () -> product.changePrice(price)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격 변경을 실패한다")
    @ParameterizedTest
    @ValueSource(longs = {-1L, -2L})
    void invalid_change_price(long price){
        Product product = product("후라이드", 15000L);
        assertThatThrownBy(
                () -> product.changePrice(BigDecimal.valueOf(price))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격 변경 성공한다.")
    @ParameterizedTest
    @ValueSource(longs = {15000L, 20000L})
    void change_price_product(long price){
        Product product = product("후라이드", 14000L);
        product.changePrice(BigDecimal.valueOf(price));

        assertThat(product.getPrice()).isEqualTo(BigDecimal.valueOf(price));

    }

}
