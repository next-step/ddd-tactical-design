package fixtures;

import kitchenpos.domain.*;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderTableRepository;
import kitchenpos.products.domain.ProductRepository;

public class TestContainers {

    public MenuGroupRepository menuGroupRepository = new FakeMenuGroupRepository();
    public ProductRepository productRepository = new FakeProductRepository();
    public MenuRepository menuRepository = new FakeMenuRepository();
    public OrderRepository orderRepository = new FakeOrderRepository();
    public OrderTableRepository orderTableRepository = new FakeOrderTableRepository();
}
