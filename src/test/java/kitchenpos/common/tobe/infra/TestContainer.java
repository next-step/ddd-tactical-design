package kitchenpos.common.tobe.infra;

import kitchenpos.common.tobe.domain.PurgomalumClient;
import kitchenpos.menugroups.tobe.infra.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.application.DefaultMenuService;
import kitchenpos.menus.tobe.application.MenuService;
import kitchenpos.menus.tobe.application.adapter.DefaultProductServiceAdapter;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.infra.InMemoryMenuRepository;
import kitchenpos.orders.tobe.application.DeliveryOrderService;
import kitchenpos.orders.tobe.application.EatInOrderService;
import kitchenpos.orders.tobe.application.OrderService;
import kitchenpos.orders.tobe.application.TakeoutOrderService;
import kitchenpos.orders.tobe.application.adapter.MenuServiceAdapter;
import kitchenpos.orders.tobe.application.adapter.OrderMenuServiceAdapter;
import kitchenpos.orders.tobe.domain.OrderRepository;
import kitchenpos.orders.tobe.domain.OrderTableRepository;
import kitchenpos.orders.tobe.domain.factory.OrderFactoryProvider;
import kitchenpos.orders.tobe.infra.FakeKitchenridersClient;
import kitchenpos.orders.tobe.infra.InMemoryOrderFactoryProvider;
import kitchenpos.orders.tobe.infra.InMemoryOrderRepository;
import kitchenpos.orders.tobe.infra.InMemoryOrderTableRepository;
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
    public EatInOrderService eatInOrderService;
    public DeliveryOrderService deliveryOrderService;
    public TakeoutOrderService takeoutOrderService;

    public TestContainer() {
        this.menuRepository = new InMemoryMenuRepository();
        this.orderRepository = new InMemoryOrderRepository();
        this.orderTableRepository = new InMemoryOrderTableRepository();
        this.purgomalumClient = new FakePurgomalumClient();
        this.productService = new DefaultProductService(new InMemoryProductRepository(), new ProductsMenuServiceAdapter(menuService), purgomalumClient);
        this.menuService = new DefaultMenuService(menuRepository, new InMemoryMenuGroupRepository(), new DefaultProductServiceAdapter(productService), purgomalumClient);
        this.menuServiceAdapter = new OrderMenuServiceAdapter(menuService);
        this.kitchenridersClient = new FakeKitchenridersClient();
        this.orderFactoryProvider = new InMemoryOrderFactoryProvider();
        this.eatInOrderService = new EatInOrderService(orderRepository, menuServiceAdapter, orderTableRepository, kitchenridersClient, orderFactoryProvider);
        this.deliveryOrderService = new DeliveryOrderService(orderRepository, menuServiceAdapter, orderTableRepository, kitchenridersClient, orderFactoryProvider);
        this.takeoutOrderService = new TakeoutOrderService(orderRepository, menuServiceAdapter, orderTableRepository, kitchenridersClient, orderFactoryProvider);
    }
}
