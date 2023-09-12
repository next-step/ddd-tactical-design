package kitchenpos.menus.subscriber;

import kitchenpos.common.FakeProfanityPolicy;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menus.application.*;
import kitchenpos.menus.infra.DefaultMenuProductMappingService;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static kitchenpos.menus.application.fixtures.MenuFixture.menuCreate;
import static kitchenpos.menus.application.fixtures.MenuFixture.menuProduct;
import static kitchenpos.menus.application.fixtures.MenuGroupFixture.menuGroup;
import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProductPriceEventListener.class,
        InMemoryMenuRepository.class,
        InMemoryProductRepository.class,
        ProductService.class,
        InMemoryMenuGroupRepository.class,
        MenuGroupService.class,
        MenuService.class,
        ProductService.class,
        FakeProfanityPolicy.class,
        DefaultMenuProductMappingService.class
})
class ProductPriceEventListenerTest {

    @Autowired
    ProductPriceEventListener productPriceChangedListener;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MenuGroupService menuGroupService;

    @Autowired
    MenuService menuService;

    @Autowired
    ProductService productService;

    @Autowired
    ProfanityPolicy profanityPolicy;

    @Autowired
    MenuProductMappingService mappingService;

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        final MenuGroup menuGroup = menuGroupService.create(menuGroup());
        final Menu menu = menuService.create(menuCreate(19_000L, true, menuGroup, menuProduct(product, 2L)));
        productService.changePrice(product.getId(), BigDecimal.valueOf(8_000L));
        assertThat(menuService.findById(menu.getId()).isDisplayed()).isFalse();
    }

}
