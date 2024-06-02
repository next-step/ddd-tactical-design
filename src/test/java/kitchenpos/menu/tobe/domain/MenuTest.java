package kitchenpos.menu.tobe.domain;

import kitchenpos.exception.IllegalPriceException;
import kitchenpos.fixture.tobe.ProductFixture;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import kitchenpos.product.tobe.domain.Product;
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
    PurgomalumClient purgomalumClient;
    MenuGroup menuGroup;
    Product product = ProductFixture.createProduct();
    MenuProduct menuProduct;
    MenuProducts menuProducts;
    MenuNameFactory menuName;

    @BeforeEach
    void setUP() {
        menuGroup = new MenuGroup("메뉴그룹명");
        product = ProductFixture.createProduct("상품명", 만원);
        menuProduct = new MenuProduct(product, 2);
        menuProducts = new MenuProducts(List.of(menuProduct));
        purgomalumClient = new FakePurgomalumClient();
        menuName = new MenuNameFactory(purgomalumClient);
    }

    @Test
    @DisplayName("메뉴를 등록할 수 있다.")
    void success() {
        final var menu = new Menu(menuName.create("메뉴이름"), new MenuPrice(10_000L), menuGroup, true, menuProducts);

        assertNotNull(menu.getId());
    }

    @Test
    @DisplayName("메뉴 가격을 변경할 수 있다.")
    void success2() {
        final var menu = new Menu(menuName.create("메뉴이름"), new MenuPrice(10_000L), menuGroup, true, menuProducts);
        menu.changeMenuPrice(5_000L);

        assertThat(menu.getMenuPrice()).isEqualTo(5_000L);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, -10_000L})
    @DisplayName("0원보다 적은 금액을 입력하는 경우 메뉴 가격을 변경할할 수 없다.")
    void priceFail2(final long input) {
        final var menu = new Menu(menuName.create("메뉴이름"), new MenuPrice(10_000L), menuGroup, true, menuProducts);

        assertThrows(IllegalPriceException.class, () -> menu.changeMenuPrice(input));
    }

    @Test
    @DisplayName("변경금액이 메뉴상품의 총 합계 금액보다 비싼 경우 변경할 수 없다.")
    void priceFail2() {
        final var menu = new Menu(menuName.create("메뉴이름"), new MenuPrice(10_000L), menuGroup, true, menuProducts);

        assertThrows(IllegalPriceException.class, () -> menu.changeMenuPrice(30_000L));
    }
}
