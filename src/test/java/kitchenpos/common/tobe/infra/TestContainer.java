package kitchenpos.common.tobe.infra;

import kitchenpos.common.tobe.domain.PurgomalumClient;
import kitchenpos.eatinorders.tobe.application.EatInOrderService;
import kitchenpos.eatinorders.tobe.application.OrderService;
import kitchenpos.eatinorders.tobe.application.adapter.EatInOrderMenuServiceAdapter;
import kitchenpos.eatinorders.tobe.application.adapter.MenuServiceAdapter;
import kitchenpos.eatinorders.tobe.domain.OrderRepository;
import kitchenpos.eatinorders.tobe.domain.OrderTableRepository;
import kitchenpos.eatinorders.tobe.factory.OrderFactoryProvider;
import kitchenpos.eatinorders.tobe.infra.FakeKitchenridersClient;
import kitchenpos.eatinorders.tobe.infra.InMemoryOrderFactoryProvider;
import kitchenpos.eatinorders.tobe.infra.InMemoryOrderRepository;
import kitchenpos.eatinorders.tobe.infra.InMemoryOrderTableRepository;
import kitchenpos.menugroups.tobe.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.application.DefaultMenuService;
import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.menus.tobe.application.adapter.DefaultProductServiceAdapter;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.infra.InMemoryMenuRepository;
import kitchenpos.products.tobe.application.DefaultProductService;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.application.adapter.ProductsMenuServiceAdapter;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;

public class TestContainer {

    public OrderRepository orderRepository;

    public MenuRepository menuRepository;
    public MenuService menuService;
    public MenuServiceAdapter menuServiceAdapter;
    public ProductService productService;

    public PurgomalumClient purgomalumClient;
    public OrderTableRepository orderTableRepository;
    public FakeKitchenridersClient kitchenridersClient;
    public OrderFactoryProvider orderFactoryProvider;
    public OrderService orderService;

    public TestContainer() {
        this.menuRepository = new InMemoryMenuRepository();
        this.orderRepository = new InMemoryOrderRepository();
        this.orderTableRepository = new InMemoryOrderTableRepository();
        this.purgomalumClient = new FakePurgomalumClient();
        this.productService = new DefaultProductService(new InMemoryProductRepository(), new ProductsMenuServiceAdapter(menuService), purgomalumClient);
        this.menuService = new DefaultMenuService(menuRepository, new InMemoryMenuGroupRepository(), new DefaultProductServiceAdapter(productService), purgomalumClient);
        this.menuServiceAdapter = new EatInOrderMenuServiceAdapter(menuService);
        this.kitchenridersClient = new FakeKitchenridersClient();
        this.orderFactoryProvider = new InMemoryOrderFactoryProvider();
        this.orderService = new EatInOrderService(orderRepository, menuServiceAdapter, orderTableRepository, kitchenridersClient, orderFactoryProvider);
    }
}
