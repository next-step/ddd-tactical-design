package kitchenpos.menus.tobe.domain;

import static java.math.BigDecimal.valueOf;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupEmptyException;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuPriceException;
import kitchenpos.menus.tobe.domain.vo.MenuProducts;
import kitchenpos.products.tobe.domain.Product;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {
    @Test
    void 메뉴는_특정_메뉴그룹에_속해야한다() {
        // given when & then
        assertThatThrownBy(() -> new Menu(null, "치킨 세트", 40000, true))
                .isInstanceOf(InvalidMenuGroupEmptyException.class)
                .hasMessage("메뉴는 반드시 특정 메뉴 그룹에 속해야 합니다.");
    }

    @Test
    void 메뉴_가격은_포함된_상품들의_총_가격의_합보다_클_수_없다() {
        // given
        Product product1 = new Product("후라이드 치킨", valueOf(20_000));
        Product product2 = new Product("양념 치킨", valueOf(22_000));

        MenuGroup menuGroup = new MenuGroup("메인 메뉴");
        Menu menu = new Menu(menuGroup, "치킨 세트", 50_000, true);

        MenuProduct mp1 = new MenuProduct(menu, product1, 20_000, 1, product1.getId());
        MenuProduct mp2 = new MenuProduct(menu, product2, 22_000, 1, product2.getId());
        MenuProducts menuProducts = new MenuProducts(mp1, mp2);

        // when & then
        // 상품 총액은 20,000 + 22,000 = 42,000원, 메뉴 가격 50000원이므로 예외 발생해야 함.
        assertThatThrownBy(() -> new Menu(menuGroup, "치킨 세트", 50_000, true, menuProducts))
                .isInstanceOf(InvalidMenuPriceException.class)
                .hasMessage("메뉴 가격은 포함된 상품들의 총 가격보다 클 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -1000, -10000})
    void 메뉴_가격을_변경할_때_가격이_0원_이상이어야한다(int invalidMenuPrice) {
        // given
        MenuGroup menuGroup = new MenuGroup("메인 메뉴");
        Menu menu = new Menu(menuGroup, "후라이드 치킨", 20_000, true);

        // when & then
        assertThatThrownBy(() -> menu.changeMenuPrice(invalidMenuPrice))
                .isInstanceOf(InvalidMenuPriceException.class)
                .hasMessage("메뉴 가격은 0보다 커야 합니다.")
        ;
    }

}
