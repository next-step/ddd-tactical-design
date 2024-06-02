package kitchenpos.menu.tobe.domain;

import kitchenpos.fixture.tobe.ProductFixture;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import kitchenpos.product.tobe.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        product = ProductFixture.createProduct();
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


}
