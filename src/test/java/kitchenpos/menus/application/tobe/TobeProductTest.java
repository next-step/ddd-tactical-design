package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.TobeProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.menus.application.tobe.TobeMenusFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TobeProductTest {

    @DisplayName("상품 생성")
    @Test
    void create() {
        TobeProduct product = product("후라이드", 16_000L);
        assertThat(product).isNotNull();
    }

    @DisplayName("상품의 가격이 올바르지 않으면 등록할 수 없다.")
    @ValueSource(strings = "-1000")
    @ParameterizedTest
    void negativePrice1(final Long price) {
        assertThatThrownBy(() -> product("후라이드", price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품의 가격이 비어있으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void negativePrice2(final Long price) {
        assertThatThrownBy(() -> product("후라이드", price))
                .isInstanceOf(NullPointerException.class);
    }

    @DisplayName("상품의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullSource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> product(name, 16_000L))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
