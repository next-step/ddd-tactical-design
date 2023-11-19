package kitchenpos.menu.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.Fixtures;
import kitchenpos.common.Price;
import kitchenpos.menu.tobe.domain.Menu;
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
        assertDoesNotThrow(() -> new Menu(UUID.randomUUID(), "test menu name", Price.of(BigDecimal.valueOf(price)), UUID.randomUUID(), true, menuProducts));
    }

    @DisplayName("메뉴 객체를 생성한다")
    @ParameterizedTest
    @ValueSource(longs = {15_001L, 16_000L, 17_000L, 18_000L, 19_000L})
    void testInitMenuIfPriceIsMoreThanSumOfMenuProductPrices(long price) {
        // given
        var menuProducts = List.of(Fixtures.menuProduct(10_000L, 1), Fixtures.menuProduct(5_000L, 1));

        // when // then
        assertThatThrownBy(() -> new Menu(UUID.randomUUID(), "test menu name", Price.of(BigDecimal.valueOf(price)), UUID.randomUUID(), true, menuProducts))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 메뉴 상품 가격을 변경할 때, 메뉴 상품들의 가격 합보다 메뉴의 가격이 크면 메뉴가 숨김 처리된다.")
    @Test
    void testChangeMenuProductPriceIfMenuPriceIsMoreThanSumOfMenuProductPrices() {
        // given
        MenuProduct menuProduct = Fixtures.menuProduct(100_000, 1);
        Menu menu = Fixtures.menu(100_000, true, menuProduct);

        // when
        menu.changeMenuProductPrice(menuProduct.getProductId(), Price.of(BigDecimal.valueOf(99_999L)));

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 메뉴 상품 가격을 변경할 때, 메뉴 상품들의경 가격 합보다 메뉴의 가격이 작거나 같으면 메뉴의 숨김 여부가 변경되지 않는다.")
    @CsvSource(value = {"true;true", "false;false"}, delimiter = ';')
    @ParameterizedTest
    void testChangeMenuProductPriceIfMenuPriceIsLessThanSumOfMenuProductPrices(boolean displayed, boolean expected) {
        // given
        MenuProduct menuProduct = Fixtures.menuProduct(100_000L, 1);
        Menu menu = Fixtures.menu(100_000, displayed, menuProduct);

        // when
        menu.changeMenuProductPrice(menuProduct.getProductId(), Price.of(BigDecimal.valueOf(100_001L)));

        // then
        assertThat(menu.isDisplayed()).isEqualTo(expected);
    }

    @DisplayName("메뉴를 노출한다")
    @Test
    void testDisplay() {
        // given
        var menu = Fixtures.menu(9_999L, false, Fixtures.menuProduct(10_000L, 1));

        // when
        menu.display();

        // then
        assertThat(menu.isDisplayed()).isTrue();
    }

    @DisplayName("만약 메뉴 가격이 메뉴 상품의 가격 합보다 크면 메뉴를 노출할 수 없다")
    @Test
    void testDisplayIfMenuPriceIsMoreThanSumOfMenuProductPrices() {
        // given
        var menu = Fixtures.hideMenu(9_999L, Fixtures.menuProduct(10_000L, 1));

        // when // then
        assertThatThrownBy(() -> menu.display())
            .isExactlyInstanceOf(IllegalStateException.class);
    }

    @DisplayName("메뉴를 숨긴다")
    @Test
    void testHide() {
        // given
        var menu = Fixtures.menu();

        // when
        menu.hide();

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 가격을 수정한다")
    @Test
    void testChangePrice() {
        // given
        var menu = Fixtures.menu();
        BigDecimal expected = BigDecimal.valueOf(5_000L);

        // when
        menu.changePrice(Price.of(expected));

        // then
        assertThat(menu.getPrice()).isEqualTo(expected);
    }

    @DisplayName("메뉴항목들의 가격 합보다 작은 가격으로는 메뉴 가격을 수정할 수 없다")
    @Test
    void testChangePriceIfSumOfMenuProductPricesIsLowerThanChangedPrice() {
        // given
        var menu = Fixtures.menu(10_000L, Fixtures.menuProduct(11_000L, 1));
        var changedPrice = Price.of(BigDecimal.valueOf(15_000L));

        // when // then
        assertThatThrownBy(() -> menu.changePrice(changedPrice))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }
}
