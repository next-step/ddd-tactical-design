package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.domain.ProfanityFilteredNameFactory;
import kitchenpos.common.domain.Quantity;
import kitchenpos.eatinorders.tobe.dto.EatInOrderCreationRequest;
import kitchenpos.menus.tobe.domain.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.domain.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuFactory;
import kitchenpos.menus.tobe.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static kitchenpos.Fixtures.tobe_menu;
import static kitchenpos.Fixtures.tobe_product;

public class EatInOrderTest {
    private EatInOrderRepository eatInOrderRepository;
    private OrderTableRepository orderTableRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private MenuRepository menuRepository;

    private MenuFactory menuFactory;
    private EatInOrderFactory eatInOrderFactory;
    private ProfanityFilteredNameFactory profanityFilteredNameFactory;

    private OrderTable orderTable;
    private MenuGroup menuGroup;
    private Product product;
    private Menu menu;

    @BeforeEach
    void setUp() {
        eatInOrderRepository = new InMemoryEatInOrderRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();

        menuFactory = new MenuFactory(productRepository, menuGroupRepository);
        eatInOrderFactory = new EatInOrderFactory(orderTableRepository, menuRepository);
        profanityFilteredNameFactory = new ProfanityFilteredNameFactory(new FakePurgomalumClient());

        orderTable = orderTableRepository.save(new OrderTable("1번 테이블"));
        menuGroup = menuGroupRepository.save(MenuFactory.createMenuGroup("아무메뉴"));
        product = productRepository.save(tobe_product("후라이드", 19_000L));
        menu = menuRepository.save(tobe_menu(menuGroup.getId(), product.getId()));
    }

    @DisplayName("주문을 서빙한다.")
    @Test
    void serve() {
        //given
        Map<UUID, Quantity> menuIdsAndQuantities = new HashMap<>();
        menuIdsAndQuantities.put(menu.getId(), Quantity.of(1));
        OrderTable assignedOrderTable = orderTable.assign(3);
        orderTableRepository.save(assignedOrderTable);
        EatInOrderCreationRequest request = new EatInOrderCreationRequest(orderTable.getId(), menuIdsAndQuantities);
        EatInOrder eatInOrder = EatInOrderFactory.createEatInOrder(request);

        //when
        EatInOrder servedEatInOrder = eatInOrder.serve();

        //then
        Assertions.assertThat(servedEatInOrder.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
    }

    @DisplayName("접수된 주문만 서빙할 수 있다.")
    @Test
    void serve_when_order_status_is_unavailable() {
        //given
        Map<UUID, Quantity> menuIdsAndQuantities = new HashMap<>();
        menuIdsAndQuantities.put(menu.getId(), Quantity.of(1));
        OrderTable assignedOrderTable = orderTable.assign(3);
        orderTableRepository.save(assignedOrderTable);
        EatInOrderCreationRequest request = new EatInOrderCreationRequest(orderTable.getId(), menuIdsAndQuantities);
        EatInOrder eatInOrder = EatInOrderFactory.createEatInOrder(request);
        EatInOrder servedEatInOrder = eatInOrder.serve();

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> servedEatInOrder.serve()
        ).isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문을 완료한다.")
    @Test
    void complete() {
        //given
        Map<UUID, Quantity> menuIdsAndQuantities = new HashMap<>();
        menuIdsAndQuantities.put(menu.getId(), Quantity.of(1));
        OrderTable assignedOrderTable = orderTable.assign(3);
        orderTableRepository.save(assignedOrderTable);
        EatInOrderCreationRequest request = new EatInOrderCreationRequest(orderTable.getId(), menuIdsAndQuantities);
        EatInOrder eatInOrder = EatInOrderFactory.createEatInOrder(request);
        EatInOrder servedEatInOrder = eatInOrder.serve();

        //when
        EatInOrder completedEatInOrder = servedEatInOrder.complete();

        //then
        Assertions.assertThat(completedEatInOrder.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
        Assertions.assertThat(completedEatInOrder.getOrderTable().isEmpty()).isTrue();
    }

    @DisplayName("서빙된 주문만 완료할 수 있다.")
    @Test
    void complete_when_order_status_is_unavailable() {
        //given
        Map<UUID, Quantity> menuIdsAndQuantities = new HashMap<>();
        menuIdsAndQuantities.put(menu.getId(), Quantity.of(1));
        OrderTable assignedOrderTable = orderTable.assign(3);
        orderTableRepository.save(assignedOrderTable);
        EatInOrderCreationRequest request = new EatInOrderCreationRequest(orderTable.getId(), menuIdsAndQuantities);
        EatInOrder eatInOrder = EatInOrderFactory.createEatInOrder(request);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> eatInOrder.complete()
        ).isInstanceOf(IllegalStateException.class);
    }
}
