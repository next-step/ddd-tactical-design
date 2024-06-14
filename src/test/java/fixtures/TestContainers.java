package fixtures;

import kitchenpos.domain.*;
import kitchenpos.menu.domain.MenuGroupRepository;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.order.domain.OrderRepository;
import kitchenpos.order.domain.OrderTableRepository;
import kitchenpos.product.domain.ProductRepository;

public class TestContainers {

    public MenuGroupRepository menuGroupRepository = new FakeMenuGroupRepository();
    public ProductRepository productRepository = new FakeProductRepository();
    public MenuRepository menuRepository = new FakeMenuRepository();
    public OrderRepository orderRepository = new FakeOrderRepository();
    public OrderTableRepository orderTableRepository = new FakeOrderTableRepository();
}
