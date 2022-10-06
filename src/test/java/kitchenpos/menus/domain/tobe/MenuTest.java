package kitchenpos.menus.domain.tobe;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuTest {

    private MenuName menuName;
    private MenuPrice menuPrice;
    private MenuProducts menuProducts;
    private MenuGroup menuGroup;
    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setup() {
        this.purgomalumClient = new FakePurgomalumClient();
        this.menuName = new MenuName("메뉴", purgomalumClient);
        this.menuPrice = new MenuPrice(BigDecimal.TEN);
        this.menuProducts = new MenuProducts(List.of(new MenuProduct(1L, product(), 1L)));
        this.menuGroup = new MenuGroup(new MenuGroupName("메뉴 그룹"));
    }

    @Test
    @DisplayName("메뉴 생성이 가능하다")
    void constructor() {
        final Menu expected = new Menu(menuName, menuPrice, menuGroup, true, menuProducts);
        assertThat(expected).isNotNull();
    }

    @Test
    @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
    void constructor_with_null_menu_group() {
        assertThatThrownBy(() -> new Menu(menuName, menuPrice, null, true, menuProducts))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴 가격 변경이 가능하다")
    void change_price() {
        final Menu expected = new Menu(menuName, menuPrice, menuGroup, true, menuProducts);
        final BigDecimal updatePrice = BigDecimal.valueOf(9);
        expected.changePrice(new MenuPrice(updatePrice));

        assertThat(expected.price().value()).isEqualTo(updatePrice);
    }

    @Test
    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다")
    void change_price_not_valid_price() {
        final Menu expected = new Menu(menuName, menuPrice, menuGroup, true, menuProducts);
        final BigDecimal updatePrice = BigDecimal.valueOf(-1);

        assertThatThrownBy(() -> expected.changePrice(new MenuPrice(updatePrice)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다")
    void constructor_with_not_valid_price_policy() {
        assertThatThrownBy(() -> new Menu(menuName, new MenuPrice(BigDecimal.valueOf(32_000L)), menuGroup, true, menuProducts))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("메뉴를 노출할 수 있다")
    void show() {
        final Menu expected = new Menu(menuName, menuPrice, menuGroup, false, menuProducts);

        expected.show();
        assertThat(expected.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다")
    void hide() {
        final Menu expected = new Menu(menuName, menuPrice, menuGroup, false, menuProducts);

        expected.changePrice(new MenuPrice(BigDecimal.valueOf(32_000L)));
        assertThatThrownBy(() -> expected.show())
            .isInstanceOf(IllegalArgumentException.class);
    }
}
