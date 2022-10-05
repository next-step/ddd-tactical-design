package kitchenpos.integration;

import kitchenpos.common.ProfanityChecker;
import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.application.OrderService;
import kitchenpos.eatinorders.application.OrderTableService;
import kitchenpos.eatinorders.domain.OrderRepository;
import kitchenpos.eatinorders.domain.OrderTableRepository;
import kitchenpos.integration.doubles.FakeProfanityChecker;
import kitchenpos.integration.doubles.FakeRidersClient;
import kitchenpos.integration.doubles.MemoryMenuGroupRepository;
import kitchenpos.integration.doubles.MemoryMenuRepository;
import kitchenpos.integration.doubles.MemoryOrderRepository;
import kitchenpos.integration.doubles.MemoryOrderTableRepository;
import kitchenpos.integration.doubles.MemoryProductRepository;
import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    MenuGroupService menuGroupService() {
        return new MenuGroupService(menuGroupRepository());
    }

    @Bean
    ProductService productService() {
        return new ProductService(productRepository(), menuRepository(), profanityChecker());
    }

    @Bean
    MenuService menuService() {
        return new MenuService(menuRepository(), menuGroupRepository(), productRepository(), profanityChecker());
    }

    @Bean
    OrderTableService orderTableService() {
        return new OrderTableService(orderTableRepository(), orderRepository());
    }

    @Bean
    OrderService orderService() {
        return new OrderService(orderRepository(), menuRepository(), orderTableRepository(), riders());
    }

    @Bean

    MenuGroupRepository menuGroupRepository() {
        return new MemoryMenuGroupRepository();
    }

    @Bean
    MenuRepository menuRepository() {
        return new MemoryMenuRepository();
    }

    @Bean
    ProductRepository productRepository() {
        return new MemoryProductRepository();
    }

    @Bean
    OrderTableRepository orderTableRepository() {
        return new MemoryOrderTableRepository();
    }

    @Bean
    OrderRepository orderRepository() {
        return new MemoryOrderRepository();
    }

    @Bean
    ProfanityChecker profanityChecker() {
        return new FakeProfanityChecker();
    }

    @Bean
    KitchenridersClient riders() {
        return new FakeRidersClient();
    }
}
