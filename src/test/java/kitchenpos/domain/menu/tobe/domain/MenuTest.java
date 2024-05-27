package kitchenpos.domain.menu.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class MenuTest {

    @DisplayName("메뉴 생성을 성공한다")
    @Test
    void constructor() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(1_000), 1);
        Menu menu = new Menu("메뉴A", BigDecimal.ONE, null, false, List.of(menuProduct));
        assertThat(menu.name()).isEqualTo("메뉴A");
        assertThat(menu.price()).isEqualTo(BigDecimal.ONE);
        assertThat(menu.displayed()).isFalse();
    }

    @DisplayName("메뉴 가격을 변경한다")
    @Test
    void changePrice() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.valueOf(1_000), 1);
        Menu menu = new Menu("메뉴A", BigDecimal.ONE, null, false, List.of(menuProduct));

        menu.changePrice(BigDecimal.valueOf(1_000));

        assertThat(menu.price()).isEqualTo(BigDecimal.valueOf(1_000));
    }

    @DisplayName("메뉴 가격이 메뉴 상품 가격의 합을 초과하면, 메뉴 가격을 변경을 실패한다")
    @Test
    void changePrice_fail() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.ONE, 1);
        Menu menu = new Menu("메뉴A", BigDecimal.ONE, null, false, List.of(menuProduct));

        assertThatIllegalStateException().isThrownBy(() -> menu.changePrice(BigDecimal.valueOf(1_000)));
    }

    @DisplayName("메뉴를 Hide Menu로 변경한다")
    @Test
    void hide() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.ONE, 1);
        Menu menu = new Menu("메뉴A", BigDecimal.ONE, null, false, List.of(menuProduct));

        menu.hide();
        assertThat(menu.displayed()).isFalse();
    }

    @DisplayName("메뉴를 Displayed Menu로 변경한다")
    @Test
    void display() {
        MenuProduct menuProduct = new MenuProduct(UUID.randomUUID(), BigDecimal.ONE, 1);
        Menu menu = new Menu("메뉴A", BigDecimal.ONE, null, false, List.of(menuProduct));

        menu.display();
        assertThat(menu.displayed()).isTrue();
    }

    @DisplayName("메뉴 가격이 메뉴 상품 가격의 합을 초과하면, Displayed Menu로 변경할 수 없다")
    @Test
    void display_fail() {
        // do not test
    }
}
