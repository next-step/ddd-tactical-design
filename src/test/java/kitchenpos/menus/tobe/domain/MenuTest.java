package kitchenpos.menus.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

/*
- [x] 메뉴의 가격을 변경할 수 있다.
- [x] 메뉴의 가격이 올바르지 않으면 변경할 수 없다.
- [x] 메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.
- [x] 메뉴는 특정 메뉴 그룹에 속해야 한다.
- 메뉴를 노출할 수 있다.
- 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.
- 메뉴를 숨길 수 있다.
- 메뉴의 목록을 조회할 수 있다.
 */
class MenuTest {
    @DisplayName("메뉴의 가격을 변경할 수 있다.")
    @Test
    void construct() {
        final Price price = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new DisplayedName("양념치킨", new FakeProfanity()), new Product(BigDecimal.valueOf(6L)));
        final Menu menu = new Menu(price, name, menuProduct, new MenuGroup());

        menu.changePrice(new Price(BigDecimal.ONE));

        assertThat(menu.getPrice()).isEqualTo(new Price(BigDecimal.ONE));
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @ParameterizedTest
    @ValueSource(longs = {5L, 6L})
    void greaterThanAndEqualsMenuProductPriceSum(final long productPrice) {
        final Price menuPrice = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new DisplayedName("양념치킨", new FakeProfanity()), new Product(BigDecimal.valueOf(productPrice)));

        assertThatCode(() -> new Menu(menuPrice, name, menuProduct, new MenuGroup()))
                .doesNotThrowAnyException();
    }

    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    @Test
    void consistOfMenuGroup() {
        final Price menuPrice = new Price(BigDecimal.TEN);
        final DisplayedName name = new DisplayedName("치킨 세트", new FakeProfanity());
        final MenuProduct menuProduct = new MenuProduct(2L, new DisplayedName("양념치킨", new FakeProfanity()), new Product(BigDecimal.valueOf(6L)));
        final MenuGroup menuGroup = new MenuGroup();

        assertThatCode(() -> new Menu(menuPrice, name, menuProduct, menuGroup))
                .doesNotThrowAnyException();
    }
}
