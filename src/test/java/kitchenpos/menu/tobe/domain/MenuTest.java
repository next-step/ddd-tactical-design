package kitchenpos.menu.tobe.domain;

import kitchenpos.exception.IllegalPriceException;
import kitchenpos.fixture.tobe.ProductFixture;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menus.tobe.domain.menu.*;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menuproduct.MenuProduct;
import kitchenpos.menus.tobe.domain.menuproduct.MenuProducts;
import kitchenpos.menus.tobe.domain.menuproduct.Quantity;
import kitchenpos.products.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static kitchenpos.MoneyConstants.만원;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MenuTest {
    private MenuGroup menuGroup;
    private MenuProducts menuProducts;
    private MenuNameFactory menuName;

    @BeforeEach
    void setUP() {
        menuGroup = new MenuGroup("메뉴그룹명");
        Product product = ProductFixture.createProduct("상품명", 만원);
        MenuProduct menuProduct = MenuProduct.of(product.getId(), Quantity.of(2), product.getPrice().longValue());
        menuProducts = new MenuProducts(List.of(menuProduct));
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        menuName = new MenuNameFactory(purgomalumClient);
    }

    @Test
    @DisplayName("메뉴를 등록할 수 있다.")
    void success() {
        final var menu = Menu.of(menuName.create("메뉴이름"), MenuPrice.of(10_000L), menuGroup.getId(), true, menuProducts);

        assertNotNull(menu.getId());
    }

    @Test
    @DisplayName("메뉴 가격을 변경할 수 있다.")
    void success2() {
        final var menu = Menu.of(menuName.create("메뉴이름"), MenuPrice.of(10_000L), menuGroup.getId(), true, menuProducts);
        menu.changeMenuPrice(5_000L);

        assertThat(menu.getMenuPrice()).isEqualTo(5_000L);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, -10_000L})
    @DisplayName("0원보다 적은 금액을 입력하는 경우 메뉴 가격을 변경할할 수 없다.")
    void priceFail2(final long input) {
        final var menu = Menu.of(menuName.create("메뉴이름"), MenuPrice.of(10_000L), menuGroup.getId(), true, menuProducts);

        assertThrows(IllegalPriceException.class, () -> menu.changeMenuPrice(input));
    }

    @Test
    @DisplayName("변경금액이 메뉴상품의 총 합계 금액보다 비싼 경우 변경할 수 없다.")
    void priceFail2() {
        final var menu = Menu.of(menuName.create("메뉴이름"), MenuPrice.of(10_000L), menuGroup.getId(), true, menuProducts);

        assertThrows(IllegalPriceException.class, () -> menu.changeMenuPrice(30_000L));
    }

}
