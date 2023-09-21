package kitchenpos.products.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupName;
import kitchenpos.menus.tobe.domain.MenuName;
import kitchenpos.menus.tobe.domain.MenuPrice;
import kitchenpos.menus.tobe.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.MenuProductQuantity;
import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.event.ProductPriceChangedEvent;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@RecordApplicationEvents
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @Autowired
    ApplicationEvents events;
    @Autowired
    private MenuGroupRepository MenuGroupRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private MenuService menuService;

    @Test
    public void 가격_변경() throws Exception {
        MenuGroup menuGroup = MenuGroup.of(new MenuGroupName("메뉴그룹이름"));
        MenuGroupRepository.save(menuGroup);
        final Product product = productRepository.save(new Product(UUID.randomUUID(),new ProductName("후라이드",purgomalumClient),new ProductPrice(
            BigDecimal.valueOf(16_000))));
        MenuProducts menuProducts = new MenuProducts(
            List.of(new MenuProduct(product.getId(), new MenuProductQuantity(1))));
        Menu menu = Menu.of(new MenuName("메뉴이름", purgomalumClient), new MenuPrice(BigDecimal.valueOf(15_000)), menuGroup, true, menuProducts);
        menuRepository.save(menu);
        product.changePrice(BigDecimal.valueOf(14_000));
        productService.changePrice(product.getId(), product);
        assertThat(events.stream(ProductPriceChangedEvent.class).count()).isEqualTo(1);
    }
}