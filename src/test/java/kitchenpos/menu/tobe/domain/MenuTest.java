package kitchenpos.menu.tobe.domain;

import kitchenpos.exception.IllegalPriceException;
import kitchenpos.fixture.tobe.ProductFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuTest {
    @Test
    @DisplayName("메뉴를 등록할 수 있다.")
    void success() {
        final var menuGroup = new MenuGroup("메뉴그룹명");
        final var product = ProductFixture.createProduct();
        final var menuProduct = new MenuProduct(product, 2);
        final var menuProducts = new MenuProducts(List.of(menuProduct));
        final var menu = new Menu("메뉴이름", 10_000L, menuGroup, true, menuProducts);

        assertNotNull(menu.getId());
    }

    @ParameterizedTest
    @NullSource
    @DisplayName("금액 정보는 필수로 입력해야한다.")
    void priceFail1(final Long input) {
        final var menuGroup = new MenuGroup("메뉴그룹명");
        final var product = ProductFixture.createProduct();
        final var menuProduct = new MenuProduct(product, 2);
        final var menuProducts = new MenuProducts(List.of(menuProduct));

        Assertions.assertThatExceptionOfType(IllegalPriceException.class)
                .isThrownBy(() -> new Menu("메뉴이름", input, menuGroup, true, menuProducts));
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, -10_000L})
    @DisplayName("0원보다 적은 금액을 입력하는 경우 메뉴를 등록할 수 없다.")
    void priceFail2(final long input) {
        final var menuGroup = new MenuGroup("메뉴그룹명");
        final var product = ProductFixture.createProduct();
        final var menuProduct = new MenuProduct(product, 2);
        final var menuProducts = new MenuProducts(List.of(menuProduct));

        assertThrows(IllegalPriceException.class, () -> new Menu("메뉴이름", input, menuGroup, true, menuProducts));
    }
}
