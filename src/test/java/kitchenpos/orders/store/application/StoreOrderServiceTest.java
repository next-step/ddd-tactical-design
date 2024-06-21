package kitchenpos.orders.store.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Arrays;
import java.util.List;
import kitchenpos.fake.InMemoryMenuGroupRepository;
import kitchenpos.fake.InMemoryMenuRepository;
import kitchenpos.fake.InMemoryOrderTableRepository;
import kitchenpos.fake.InMemoryProductRepository;
import kitchenpos.fake.InMemoryStoreOrderRepository;
import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.fixture.OrderTableFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.application.dto.OrderLineItemRequest;
import kitchenpos.orders.common.application.dto.OrderLineItemRequests;
import kitchenpos.orders.common.domain.OrderStatus;
import kitchenpos.orders.store.application.dto.StoreOrderCreateRequest;
import kitchenpos.orders.store.domain.OrderTableRepository;
import kitchenpos.orders.store.domain.StoreOrderRepository;
import kitchenpos.orders.store.domain.tobe.NumberOfGuests;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import kitchenpos.orders.store.domain.tobe.StoreOrder;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.tobe.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("OrderService")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StoreOrderServiceTest {

    private StoreOrderRepository storeOrderRepository = new InMemoryStoreOrderRepository();

    private MenuRepository menuRepository = new InMemoryMenuRepository();

    private OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();

    private MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();

    private ProductRepository productRepository = new InMemoryProductRepository();

    private StoreOrderService storeOrderService;

    @BeforeEach
    void setUp() {
        storeOrderService = new StoreOrderService(storeOrderRepository, menuRepository,
                orderTableRepository);
    }

    @Test
    void 매장주문_생성시_테이블을_빠뜨리면_예외를_던진다() {
        MenuGroup menuGroup = createMenuGroup();

        assertThatThrownBy(() -> storeOrderService.create(new StoreOrderCreateRequest(
                createOrderLineItemRequests(createFriedMenu(menuGroup)),
                null)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 매장주문_생성시_손님이_앉아있지_않은_테이블이면_예외를_던진다() {
        MenuGroup menuGroup = createMenuGroup();
        OrderTable orderTable = createOrderTable();
        StoreOrderCreateRequest createRequest = new StoreOrderCreateRequest(
                createOrderLineItemRequests(createFriedMenu(menuGroup)),
                orderTable.getId());

        assertThatThrownBy(() -> storeOrderService.create(createRequest))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 매장주문_하나에_여러_메뉴를_주문할_수_있다() {
        StoreOrderCreateRequest createRequest = createStoreOrderCreateRequest();

        StoreOrder actual = storeOrderService.create(createRequest);

        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 매장주문이_생성되면_주문은_승인을_대기하는_상태가_된다() {
        StoreOrderCreateRequest createRequest = createStoreOrderCreateRequest();

        StoreOrder actual = storeOrderService.create(createRequest);

        assertThat(actual.getStatus()).isEqualTo(OrderStatus.WAITING);
    }

    @Test
    void 대기상태의_매장주문을_접수한다() {
        StoreOrderCreateRequest createRequest = createStoreOrderCreateRequest();
        StoreOrder saved = storeOrderService.create(createRequest);

        storeOrderService.accept(saved.getId());

        assertThat(saved.getStatus()).isEqualTo(OrderStatus.ACCEPTED);
    }

    @Test
    void 대기상태가_아닌_매장주문을_접수하면_예외를_던진다() {
        StoreOrderCreateRequest createRequest = createStoreOrderCreateRequest();
        StoreOrder saved = storeOrderService.create(createRequest);

        storeOrderService.accept(saved.getId());

        assertThatThrownBy(() -> storeOrderService.accept(saved.getId())).isInstanceOf(
                IllegalStateException.class);
    }

    @Test
    void 접수상태의_매장주문을_전달상태로_변경한다() {
        StoreOrderCreateRequest createRequest = createStoreOrderCreateRequest();
        StoreOrder saved = storeOrderService.create(createRequest);
        storeOrderService.accept(saved.getId());

        storeOrderService.serve(saved.getId());

        assertThat(saved.getStatus()).isEqualTo(OrderStatus.SERVED);
    }

    @Test
    void 접수상태가_아닌_매장주문을_전달상태로_변경하면_예외를_던진다() {
        StoreOrderCreateRequest createRequest = createStoreOrderCreateRequest();
        StoreOrder saved = storeOrderService.create(createRequest);

        assertThatThrownBy(() -> storeOrderService.serve(saved.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 전달된_매장주문을_완료상태로_변경하면_테이블은_초기값으로_세팅된다() {
        MenuGroup menuGroup = createMenuGroup();
        OrderTable orderTable = createOrderTableAndSit();
        StoreOrderCreateRequest createRequest = new StoreOrderCreateRequest(
                createOrderLineItemRequests(createFriedMenu(menuGroup)),
                orderTable.getId());
        StoreOrder saved = storeOrderService.create(createRequest);
        storeOrderService.accept(saved.getId());
        storeOrderService.serve(saved.getId());

        storeOrderService.complete(saved.getId());

        assertAll(() -> assertThat(saved.getStatus()).isEqualTo(OrderStatus.COMPLETED),
                () -> assertThat(orderTable.isOccupied()).isFalse(),
                () -> assertThat(orderTable.getNumberOfGuests()).isZero());
    }

    @Test
    void 전달상태가_아닌_포장주문을_완료상태로_변경하면_예외를_던진다() {
        StoreOrderCreateRequest createRequest = createStoreOrderCreateRequest();
        StoreOrder saved = storeOrderService.create(createRequest);

        assertThatThrownBy(() -> storeOrderService.complete(saved.getId())).isInstanceOf(
                IllegalStateException.class);
    }

    private StoreOrderCreateRequest createStoreOrderCreateRequest() {
        MenuGroup menuGroup = createMenuGroup();
        OrderTable orderTable = createOrderTableAndSit();
        return new StoreOrderCreateRequest(
                createOrderLineItemRequests(
                        createFriedMenu(menuGroup),
                        createSeasonedMenu(menuGroup)),
                orderTable.getId());
    }

    private MenuGroup createMenuGroup() {
        return menuGroupRepository.save(MenuGroupFixture.createChicken());
    }

    private OrderTable createOrderTable() {
        return orderTableRepository.save(OrderTableFixture.createNumber1());
    }

    private OrderTable createOrderTableAndSit() {
        OrderTable orderTable = createOrderTable();
        orderTable.sit();
        orderTable.changeNumberOfGuests(new NumberOfGuests(3));
        return orderTable;
    }

    private Menu createFriedMenu(MenuGroup menuGroup) {
        Product product = productRepository.save(ProductFixture.createFired());
        return menuRepository.save(MenuFixture.createFriedOnePlusOne(menuGroup, product));
    }

    private Menu createSeasonedMenu(MenuGroup menuGroup) {
        Product product = productRepository.save(ProductFixture.createSeasoned());
        return menuRepository.save(MenuFixture.SeasonedOnePlusOne(menuGroup, product));
    }

    private OrderLineItemRequests createOrderLineItemRequests(Menu... menu) {
        List<OrderLineItemRequest> orderLineItemRequests = Arrays.stream(menu)
                .map(this::createOrderLineItemRequest).toList();
        return new OrderLineItemRequests(orderLineItemRequests);
    }

    private OrderLineItemRequest createOrderLineItemRequest(Menu menu) {
        return new OrderLineItemRequest(menu.getId(), 1);
    }
}