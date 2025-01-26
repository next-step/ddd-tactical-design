package kitchenpos.menus.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.infra.listener.ProductPriceChangeListener;
import kitchenpos.menus.tobe.domain.Product;
import kitchenpos.clients.FakePurgomalumClient;
import kitchenpos.menus.tobe.domain.PurgomalumClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.shared.event.ProductPriceChangeEvent;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.Fixtures.*;
import static kitchenpos.MenuFixture.menu;
import static kitchenpos.MenuFixture.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;


class ProductPriceChangeListenerTest {

    private ProductPriceChangeListener productPriceChangeListener;
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private MenuAntiCorruptionService menuAntiCorruptionService;
    private PurgomalumClient purgomalumClient;
    private MenuService menuService;
    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        menuAntiCorruptionService = new MenuAntiCorruptionService(productRepository);
        menuService = new MenuService(menuRepository, menuGroupRepository, menuAntiCorruptionService, purgomalumClient);
        productPriceChangeListener = new ProductPriceChangeListener(menuService);
        var savedProduct = productRepository.save(product());
        product = new Product(savedProduct.getId(), savedProduct.getPrice());
    }

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Menu menu = menuRepository.save(menu(19_000L, menuProduct(product, 2L)));
        productPriceChangeListener.listen(new ProductPriceChangeEvent(product.getProductId(), product.getProductPrice()));
        assertThat(menu.isDisplayed()).isFalse();
    }

}
