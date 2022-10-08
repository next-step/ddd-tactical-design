package kitchenpos.menus.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;

import static kitchenpos.Fixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MenuTest {

    @DisplayName("메뉴 생성")
    @Test
    void create_menu() {
        Menu menu = new Menu(UUID.randomUUID(), "후라이드+후라이드", BigDecimal.valueOf(20000L), menuGroup(), true, Arrays.asList(menuProduct(product("양념치킨", 16000L), 1L)));
        assertThat(menu).isNotNull();
    }

    @DisplayName("메뉴의 가격이 유효하지 않다")
    @ParameterizedTest()
    @ValueSource(longs = {-1L, -2L})
    void invalid_price_menu(long price) {
        MenuProduct menuProduct = menuProduct(product("양념치킨", 16000L), 1L);
        assertThatThrownBy(
                () -> new Menu(UUID.randomUUID(), "후라이드+후라이드", BigDecimal.valueOf(price), menuGroup(), true, Arrays.asList(menuProduct(product("양념치킨", 16000L), 1L)))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 이름이 유효하지 않다")
    @ParameterizedTest
    @NullSource
    void invalid_name_menu(String name) {
        assertThatThrownBy(
                () -> new Menu(UUID.randomUUID(), name, BigDecimal.valueOf(20000L), menuGroup(), true, Arrays.asList(menuProduct(product("양념치킨", 16000L), 1L)))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void menu_price_calculation() {
        MenuProduct menuProduct = menuProduct(product("양념치킨", 16000L), 1L);
        Menu menu = menu(10000L, menuProduct, menuProduct);

        assertThat(menu.calculateMenuProductsPrice().compareTo(BigDecimal.valueOf(32000L))).isEqualTo(0);
    }
}
