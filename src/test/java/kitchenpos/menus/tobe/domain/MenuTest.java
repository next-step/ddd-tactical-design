package kitchenpos.menus.tobe.domain;

import static java.math.BigDecimal.valueOf;
import kitchenpos.menus.tobe.infra.DefaultProfanities;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuGroupEmptyException;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuPriceException;
import kitchenpos.menus.tobe.domain.vo.MenuProducts;
import kitchenpos.menus.tobe.domain.vo.Profanities;
import kitchenpos.products.tobe.domain.Product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuTest {

    private MenuGroup menuGroup;
    private MenuProducts menuProducts;
    private Profanities profanities;

    @BeforeEach
    void setUp() {
        menuGroup = new MenuGroup("메인 메뉴");
        profanities = new DefaultProfanities();

        Product product1 = new Product("후라이드 치킨", valueOf(20000));
        Product product2 = new Product("양념 치킨", valueOf(22000));
        MenuProduct mp1 = new MenuProduct(product1, 20000, 1, product1.getId());
        MenuProduct mp2 = new MenuProduct(product2, 22000, 1, product2.getId());
        menuProducts = new MenuProducts(mp1, mp2);
    }

    @Test
    void 메뉴는_특정_메뉴그룹에_속해야한다() {
        // given when & then
        assertThatThrownBy(() -> new Menu(null, "치킨 세트", 40000, false, menuProducts, profanities))
                .isInstanceOf(InvalidMenuGroupEmptyException.class)
                .hasMessage("메뉴는 반드시 특정 메뉴 그룹에 속해야 합니다.");
    }

    @Test
    void 메뉴_가격은_포함된_상품들의_총_가격의_합보다_클_수_없다() {
        // given & when & then
        // 상품 총액은 20,000 + 22,000 = 42,000원, 메뉴 가격 50000원이므로 예외 발생해야 함.
        assertThatThrownBy(() -> new Menu(menuGroup, "치킨 세트", 50_000, false, menuProducts, profanities))
                .isInstanceOf(InvalidMenuPriceException.class)
                .hasMessage("메뉴 가격은 포함된 상품들의 총 가격보다 클 수 없습니다.");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -1000, -10000})
    void 메뉴_가격을_변경할_때_가격이_0원_이상이어야한다(int invalidMenuPrice) {
        // given
        Menu menu = new Menu(menuGroup, "후라이드 치킨", 20_000, false, menuProducts,  profanities);

        // when & then
        assertThatThrownBy(() -> menu.changeMenuPrice(invalidMenuPrice, menuProducts))
                .isInstanceOf(InvalidMenuPriceException.class)
                .hasMessage("메뉴 가격을 변경할 때 가격이 0원 이상이어야 합니다.")
        ;
    }

    @Test
    void 메뉴_가격을_변경할_때_메뉴_가격은_포함된_상품들의_총_가격의_합보다_클_수_없다() {
        // given
        Menu menu = new Menu(menuGroup, "치킨 세트", 42_000, false, menuProducts, profanities);

        // when & then
        assertThatThrownBy(() -> menu.changeMenuPrice(50_000, menuProducts))
                .isInstanceOf(InvalidMenuPriceException.class)
                .hasMessage("메뉴 가격은 포함된 상품들의 총 가격보다 클 수 없습니다.")
        ;
    }

    /**
     * `display()` 호출 시 메뉴 가격이 포함된 상품들의 총 가격보다 크다면 예외가 발생해야 한다.
     *
     * - `Menu` 생성 시에는 정상적인 가격(40,000원)으로 생성해야 함.
     * - 이후 **상품 가격이 변경된 상황**을 가정하고 `display()`를 호출해야 테스트가 올바르게 수행됨.
     */
    @Test
    void 메뉴_가격이_포함된_상품들의_총_가격의_합보다_크면_메뉴를_표시할_수_없다() {
        // given
        Menu menu = new Menu(menuGroup, "치킨 세트", 42_000, false, menuProducts, profanities);

        Product product1 = new Product("후라이드 치킨", valueOf(18_000));
        Product product2 = new Product("양념 치킨", valueOf(20_000));
        MenuProduct mp1 = new MenuProduct(product1, 18_000, 1, product1.getId());
        MenuProduct mp2 = new MenuProduct(product2, 20_000, 1, product2.getId());
        MenuProducts 변경된_상품_목록 = new MenuProducts(mp1, mp2);

        // when & then
        assertThatThrownBy(() -> menu.display(변경된_상품_목록))
                .isInstanceOf(InvalidMenuPriceException.class)
                .hasMessage("메뉴 가격은 포함된 상품들의 총 가격보다 클 수 없습니다.");
        assertThat(menu.isDisplayed()).isFalse();
    }

    @Test
    void 메뉴_가격이_포함된_상품들의_총_가격의_합과_같거나_작으면_메뉴를_표시할_수_있다() {
        // given
        Menu menu = new Menu(menuGroup, "치킨 세트", 42_000, false, menuProducts, profanities);

        // when
        menu.display(menuProducts);

        // then
        assertThat(menu.isDisplayed()).isTrue();
    }

    @Test
    void 메뉴를_숨길_수_있다() {
        // given
        Menu menu = new Menu(menuGroup, "치킨 세트", 42_000, true, menuProducts, profanities);

        // when
        menu.hide();

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }
}
