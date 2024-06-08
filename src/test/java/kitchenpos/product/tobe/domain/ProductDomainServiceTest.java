package kitchenpos.product.tobe.domain;

import kitchenpos.fixture.tobe.ProductFixture;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menu.tobe.domain.*;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPriceService;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.MoneyConstants.만원;
import static kitchenpos.MoneyConstants.오천원;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductDomainServiceTest {

    private MenuGroup menuGroup;
    private Product product;
    private MenuProducts menuProducts;
    private MenuNameFactory menuName;

    private final ProductRepository productRepository = new InMemoryProductRepository();
    private final MenuRepository menuRepository = new InMemoryMenuRepository();
    private final ProductPriceService menuService = new FakeProductDomainService(productRepository, menuRepository);

    @BeforeEach
    void setUP() {
        menuGroup = new MenuGroup("메뉴그룹명");
        product = ProductFixture.createProduct("상품명", 만원);
        MenuProduct menuProduct = MenuProduct.of(product.getId(), product.getProductPrice().longValue(), 1);
        menuProducts = new MenuProducts(List.of(menuProduct));
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        menuName = new MenuNameFactory(purgomalumClient);
    }

    @Test
    @DisplayName("메뉴상품 금액 총 합보다 메뉴 가격이 비싸지면 메뉴는 숨겨진다.")
    void priceFail3() {
        final var menu = Menu.of(menuName.create("메뉴이름"), new MenuPrice(만원), menuGroup.getId(), true, menuProducts);

        assertTrue(menu.isMenuDisplayStatus());

        productRepository.save(product);
        menuRepository.save(menu);
        Product result = menuService.syncMenuDisplayStatusWithProductPrices(product.getId(), BigDecimal.valueOf(오천원));

        assertFalse(menu.isMenuDisplayStatus());
    }
}
