package kitchenpos.menu.tobe.domain;

import kitchenpos.fixture.tobe.ProductFixture;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menuGroup.tobe.domain.MenuGroup;
import kitchenpos.menuproduct.tobe.domain.MenuProduct;
import kitchenpos.menuproduct.tobe.domain.MenuProducts;
import kitchenpos.product.tobe.domain.InMemoryProductRepository;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.MoneyConstants.만원;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MenuServiceTest {

    private MenuGroup menuGroup;
    private Product product;
    private MenuProducts menuProducts;
    private MenuNameFactory menuName;

    private final ProductRepository productRepository = new InMemoryProductRepository();
    private final MenuRepository menuRepository = new InMemoryMenuRepository();
    private final MenuService menuService = new FakeMenuService(productRepository, menuRepository);

    @BeforeEach
    void setUP() {
        menuGroup = new MenuGroup("메뉴그룹명");
        product = ProductFixture.createProduct("상품명", 만원);
        MenuProduct menuProduct = new MenuProduct(product, 1);
        menuProducts = new MenuProducts(List.of(menuProduct));
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        menuName = new MenuNameFactory(purgomalumClient);
    }

    @Test
    @DisplayName("메뉴상품 금액 총 합보다 메뉴 가격이 비싸지면 메뉴는 숨겨진다.")
    void priceFail3() {
        final var menu = new Menu(menuName.create("메뉴이름"), new MenuPrice(10_000L), menuGroup, true, menuProducts);

        assertTrue(menu.isMenuDisplayStatus());

        productRepository.save(product);
        menuRepository.save(menu);
        menuService.syncMenuDisplayStatisWithProductPrices(product.getId(), BigDecimal.valueOf(5_000L));

        assertFalse(menu.isMenuDisplayStatus());
    }

    @Test
    @DisplayName("메뉴상품 금액 총 합보다 메뉴 가격이 비싸지면 메뉴는 숨겨진다.")
    void priceFail4() {
        final var menu = new Menu(menuName.create("메뉴이름"), new MenuPrice(10_000L), menuGroup, true, menuProducts);

        assertTrue(menu.isMenuDisplayStatus());

        productRepository.save(product);
        menuRepository.save(menu);
        menuService.syncMenuDisplayStatisWithProductPrices(product.getId(), BigDecimal.valueOf(5_000L));

        assertFalse(menu.isMenuDisplayStatus());
    }
}
