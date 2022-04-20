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

public class EatInOrderFactoryTest {
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

    @DisplayName("1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.")
    @Test
    void createEatInOrder() {
        //given
        Map<UUID, Quantity> menuIdsAndQuantities = new HashMap<>();
        menuIdsAndQuantities.put(menu.getId(), Quantity.of(1));
        OrderTable assignedOrderTable = orderTable.assign(3);
        orderTableRepository.save(assignedOrderTable);
        EatInOrderCreationRequest request = new EatInOrderCreationRequest(orderTable.getId(), menuIdsAndQuantities);

        //when
        EatInOrder eatInOrder = EatInOrderFactory.createEatInOrder(request);

        //then
        Assertions.assertThat(eatInOrder.getOrderTable().getId()).isEqualTo(orderTable.getId());
        Assertions.assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @Test
    void create_eat_in_order_when_no_menu_exists() {
        //given
        Map<UUID, Quantity> menuIdsAndQuantities = new HashMap<>();
        menuIdsAndQuantities.put(UUID.randomUUID(), Quantity.of(1));
        OrderTable assignedOrderTable = orderTable.assign(3);
        orderTableRepository.save(assignedOrderTable);
        EatInOrderCreationRequest request = new EatInOrderCreationRequest(assignedOrderTable.getId(), menuIdsAndQuantities);

        //when
        //then
        Assertions.assertThatThrownBy(
                        () -> EatInOrderFactory.createEatInOrder(request)
                ).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("cannot find menu.");
    }

    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    @Test
    void create_eat_in_order_when_order_table_is_empty() {
        //given
        Map<UUID, Quantity> menuIdsAndQuantities = new HashMap<>();
        menuIdsAndQuantities.put(menu.getId(), Quantity.of(1));
        EatInOrderCreationRequest request = new EatInOrderCreationRequest(orderTable.getId(), menuIdsAndQuantities);

        //when
        //then
        Assertions.assertThatThrownBy(
                () -> EatInOrderFactory.createEatInOrder(request)
        ).isInstanceOf(IllegalStateException.class);
    }

}
