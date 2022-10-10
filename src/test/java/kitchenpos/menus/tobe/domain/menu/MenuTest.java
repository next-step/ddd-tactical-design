package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.global.vo.DisplayedName;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("메뉴")
class MenuTest {

    private UUID menuIdFirst;
    private UUID productIdFirst;
    private UUID menuIdSecond;
    private UUID productIdSecond;

    private MenuGroup menuGroup;

    private MenuProduct menuProductFirst;
    private MenuProduct menuProductSecond;

    private MenuProducts menuProducts;

    @BeforeEach
    void setUp() {
        menuIdFirst = UUID.randomUUID();
        productIdFirst = UUID.randomUUID();
        menuIdSecond = UUID.randomUUID();
        productIdSecond = UUID.randomUUID();

        menuGroup = new MenuGroup(new DisplayedName("두마리메뉴"));

        menuProductFirst = new MenuProduct(productIdFirst, new Quantity(2), new Price(20_000));
        menuProductSecond = new MenuProduct(productIdSecond, new Quantity(1), new Price(10_000));

        menuProducts = new MenuProducts(List.of(menuProductFirst, menuProductSecond));
    }

    @DisplayName("메뉴 생성")
    @Test
    void createMenu() {
        Menu menu = new Menu(new DisplayedName("후라이드+후라이드"), new Price(20_000), menuGroup, true, menuProducts);

        assertAll(
                () -> assertThat(menu.getName()).isEqualTo(new DisplayedName("후라이드+후라이드")),
                () -> assertThat(menu.getPrice()).isEqualTo(new Price(20_000)),
                () -> assertThat(menu.isDisplayed()).isEqualTo(true)
        );
    }

    @DisplayName("메뉴 가격이 menuProduce 총액보다 크면 에러")
    @Test
    void menuPriceBiggerThanMenuProductTotalPrice() {
        assertThatThrownBy(() -> new Menu(new DisplayedName("후라이드+후라이드"), new Price(100_000), menuGroup, true, menuProducts)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 가격 변경")
    @Test
    void menuChangePrice() {
        Menu menu = new Menu(new DisplayedName("후라이드+후라이드"), new Price(20_000), menuGroup, true, menuProducts);
        menu.changePrice(new Price(25_000));

        assertThat(menu.getPrice()).isEqualTo(new Price(25_000));
    }

    @DisplayName("메뉴 가격 변경시 메뉴 가격이 menuProduce 총액보다 크면 에러")
    @Test
    void menuChangePriceBiggerThanMenuProductTotalPrice() {
        Menu menu = new Menu(new DisplayedName("후라이드+후라이드"), new Price(20_000), menuGroup, true, menuProducts);

        assertThatThrownBy(() -> menu.changePrice(new Price(100_000))).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("상품 가격 변경")
    @Test
    void changeProductPrice() {
        Menu menu = new Menu(new DisplayedName("후라이드+후라이드"), new Price(20_000), menuGroup, true, menuProducts);
        menu.changeMenuProductPrice(productIdFirst, new Price(25_000));

        BigDecimal menuProductTotalPrice = menu.getMenuProducts().getMenuProductsTotalPrice();

        assertThat(menuProductTotalPrice).isEqualTo(BigDecimal.valueOf(60_000));
    }

    @DisplayName("상품 가격 변경시 메뉴 가격이 menuProduce 총액보다 크면 메뉴 숨김")
    @Test
    void changeProductPriceBiggerThanMenuProductTotalPriceHide() {
        Menu menu = new Menu(new DisplayedName("후라이드+후라이드"), new Price(20_000), menuGroup, true, menuProducts);
        menu.changeMenuProductPrice(productIdFirst, new Price(1_000));

        assertThat(menu.isDisplayed()).isEqualTo(false);
    }

    @DisplayName("메뉴 노출로 변경")
    @Test
    void changeShow() {
        Menu menu = new Menu(new DisplayedName("후라이드+후라이드"), new Price(20_000), menuGroup, false, menuProducts);
        menu.show();

        assertThat(menu.isDisplayed()).isEqualTo(true);
    }

    @DisplayName("메뉴 노출로 변경시 메뉴 가격이 menuProduce 총액보다 크면 에러")
    @Test
    void changeShowProductPriceBiggerThanMenuProductTotalPrice() {
        Menu menu = new Menu(new DisplayedName("후라이드+후라이드"), new Price(20_000), menuGroup, false, menuProducts);
        menu.changeMenuProductPrice(productIdFirst, new Price(1_000));

        assertThatThrownBy(menu::show).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 숨김 변경")
    @Test
    void changeHide() {
        Menu menu = new Menu(new DisplayedName("후라이드+후라이드"), new Price(20_000), menuGroup, true, menuProducts);
        menu.hide();

        assertThat(menu.isDisplayed()).isEqualTo(false);
    }
}
