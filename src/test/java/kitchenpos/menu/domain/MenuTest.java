package kitchenpos.menu.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.Fixtures;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuPrice;
import kitchenpos.menu.tobe.domain.MenuProduct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    @DisplayName("메뉴 객체를 생성한다")
    @ParameterizedTest
    @ValueSource(longs = {10_000L, 11_000L, 12_000L, 13_000L, 14_000L, 15_000L})
    void testInitMenu(long price) {
        // given
        var menuProducts = List.of(Fixtures.menuProduct(10_000L, 1), Fixtures.menuProduct(5_000L, 1));

        // when // then
        assertDoesNotThrow(() -> new Menu(UUID.randomUUID(), "test menu name", MenuPrice.of(BigDecimal.valueOf(price)), UUID.randomUUID(), true, menuProducts));
    }

    @DisplayName("메뉴 객체를 생성한다")
    @ParameterizedTest
    @ValueSource(longs = {15_001L, 16_000L, 17_000L, 18_000L, 19_000L})
    void testInitMenuIfPriceIsMoreThanSumOfMenuProductPrices(long price) {
        // given
        var menuProducts = List.of(Fixtures.menuProduct(10_000L, 1), Fixtures.menuProduct(5_000L, 1));

        // when // then
        assertThatThrownBy(() -> new Menu(UUID.randomUUID(), "test menu name", MenuPrice.of(BigDecimal.valueOf(price)), UUID.randomUUID(), true, menuProducts))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 메뉴 상품 가격을 변경할 때, 메뉴 상품들의 가격 합보다 메뉴의 가격이 크면 메뉴가 숨김 처리된다.")
    @Test
    void testChangeMenuProductPriceIfMenuPriceIsMoreThanSumOfMenuProductPrices() {
        // given
        MenuProduct menuProduct = Fixtures.menuProduct(100_000, 1);
        Menu menu = Fixtures.menu(100_000, true, menuProduct);
        menu.setPrice(BigDecimal.valueOf(100_001L)); // menu 혹은 menu가 가지는 MenuProduct의 가격 변경이 어려워 setter로 가격 변경 (테스트를 위한 메서드 같아서 꺼려짐)

        // when
        menu.changeMenuProductPrice(menuProduct.getProductId(), MenuPrice.of(BigDecimal.valueOf(99_999L)));

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 메뉴 상품 가격을 변경할 때, 메뉴 상품들의 가격 합보다 메뉴의 가격이 작거나 같으면 메뉴의 숨김 여부가 변경되지 않는다.")
    @CsvSource(value = {"true;true", "false;false"}, delimiter = ';')
    @ParameterizedTest
    void testChangeMenuProductPriceIfMenuPriceIsLessThanSumOfMenuProductPrices(boolean displayed, boolean expected) {
        // given
        MenuProduct menuProduct = Fixtures.menuProduct(100_000L, 1);
        Menu menu = Fixtures.menu(100_000, displayed, menuProduct);

        // when
        menu.changeMenuProductPrice(menuProduct.getProductId(), MenuPrice.of(BigDecimal.valueOf(100_001L)));

        // then
        assertThat(menu.isDisplayed()).isEqualTo(expected);
    }


}
