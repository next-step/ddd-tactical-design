package kitchenpos.menus.subscriber;

import kitchenpos.common.FakeProfanityPolicy;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.menugroups.application.InMemoryMenuGroupRepository;
import kitchenpos.menugroups.application.MenuGroupService;
import kitchenpos.menugroups.dto.MenuGroupResponse;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.application.MenuGroupLoader;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.application.ProductPriceLoader;
import kitchenpos.menus.dto.MenuResponse;
import kitchenpos.menus.infra.DefaultMenuGroupLoader;
import kitchenpos.menus.infra.DefaultProductPriceLoader;
import kitchenpos.menus.tobe.domain.menu.MenuId;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
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

import static kitchenpos.menugroups.fixtures.MenuGroupFixture.menuGroup;
import static kitchenpos.menus.application.fixtures.MenuFixture.menuCreateRequest;
import static kitchenpos.menus.application.fixtures.MenuFixture.menuProduct;
import static kitchenpos.products.fixture.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProductPriceEventListener.class,
        InMemoryMenuRepository.class,
        InMemoryProductRepository.class,
        InMemoryMenuGroupRepository.class,
        MenuGroupService.class,
        ProductService.class,
        FakeProfanityPolicy.class,
        DefaultProductPriceLoader.class,
        DefaultMenuGroupLoader.class,
        MenuService.class
})
@DisplayName("상품 가격 변경 이벤트 발행")
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
    MenuGroupLoader menuGroupLoader;

    @Autowired
    ProductService productService;

    @Autowired
    ProfanityPolicy profanityPolicy;

    @Autowired
    ProductPriceLoader mappingService;

    @Autowired
    MenuService menuService;

    @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
    @Test
    void changePriceInMenu() {
        final Product product = productRepository.save(product("후라이드", 16_000L));
        final MenuGroupResponse menuGroup = menuGroupService.create(menuGroup());
        final MenuResponse menu = menuService.create(menuCreateRequest(19_000L, true, menuGroup.getId(), menuProduct(product, 2L)));
        productService.changePrice(product.getId(), BigDecimal.valueOf(8_000L));
        assertThat(menuService.findMenuById(new MenuId(menu.getId())).isDisplayed()).isFalse();
    }

}
