package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.adapter.out.persistance.JpaOrderTableRepository;
import kitchenpos.eatinorder.adapter.out.persistance.entity.Order;
import kitchenpos.eatinorder.adapter.out.persistance.entity.OrderTableEntity;
import kitchenpos.eatinorder.application.service.model.CreateEatInOrderRequest;
import kitchenpos.eatinorder.application.service.model.OrderLineItemRequest;
import kitchenpos.eatinorder.domain.model.todo.EatInOrder;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderStatus;
import kitchenpos.menu.adapter.out.persistance.JpaMenuEntityEntityRepository;
import kitchenpos.menu.adapter.out.persistance.JpaMenuGroupEntityRepository;
import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import kitchenpos.menu.adapter.out.persistance.entity.MenuGroupEntity;
import kitchenpos.menu.adapter.out.persistance.entity.MenuProductEntity;
import kitchenpos.product.application.port.out.SaveProductPort;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class EatInOrderServiceTest {
    private final EatInOrderService orderService;
    private final JpaMenuEntityEntityRepository menuEntityRepository;
    private final JpaOrderTableRepository orderTableRepository;
    private final SaveProductPort saveProductPort;
    private final JpaMenuGroupEntityRepository menuGroupEntityRepository;

    public EatInOrderServiceTest(SaveProductPort saveProductPort, EatInOrderService orderService, JpaMenuEntityEntityRepository menuEntityRepository, JpaOrderTableRepository orderTableRepository, JpaMenuGroupEntityRepository menuGroupEntityRepository) {
        this.saveProductPort = saveProductPort;
        this.orderService = orderService;
        this.menuEntityRepository = menuEntityRepository;
        this.orderTableRepository = orderTableRepository;
        this.menuGroupEntityRepository = menuGroupEntityRepository;
    }

    @BeforeEach
    void setup() {
        Product product = createProduct(후라이드치킨_PRODUCT_UUID, 후라이드치킨_PRODUCT_NAME, 후라이드치킨_DEFAULT_PRICE);
        saveProductPort.save(product);

        MenuGroupEntity menuGroup = createMenuGroup(치킨류_MENU_GROUP_UUID, 치킨류_MENU_GROUP_NAME);
        menuGroupEntityRepository.save(menuGroup);

        List<MenuProductEntity> menuProducts = List.of(createMenuProduct(후라이드치킨_PRODUCT_UUID, product, 1));
        MenuEntity menu = createMenu(후라이드치킨_MENU_UUID, 후라이드치킨_MENU_NAME, 후라이드치킨_MENU_DEFAULT_PRICE, 치킨류_MENU_GROUP_UUID, menuGroup, menuProducts);
        menuEntityRepository.save(menu);

        MenuEntity noDisplayMenu = createMenu(후라이드치킨_NO_DISPLAY_MENU_UUID, 후라이드치킨_MENU_NAME, 후라이드치킨_MENU_DEFAULT_PRICE, 치킨류_MENU_GROUP_UUID, menuGroup, menuProducts);
        noDisplayMenu.setDisplayed(false);
        menuEntityRepository.save(noDisplayMenu);
    }

    @DisplayName("주문 생성하기")
    @Nested
    class CreateOrderTest {

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("매장 식사 주문을 정상적으로 생성한다")
        @Test
        void create_eat_in_order_successfully() {
            // given
            OrderTableEntity orderTableEntity = createOrderTable(테이블_1_ORDER_TABLE_UUID, "테이블 1", 0, true);
            orderTableRepository.save(orderTableEntity);

            List<OrderLineItemRequest> orderLineItems = List.of(new OrderLineItemRequest(2, 후라이드치킨_MENU_UUID, 후라이드치킨_MENU_DEFAULT_PRICE));
            CreateEatInOrderRequest request = new CreateEatInOrderRequest(테이블_1_ORDER_TABLE_UUID, orderLineItems);

            // when
            EatInOrder order = orderService.create(request);

            // then
            assertAll(
                    () -> assertThat(order.getId()).isNotNull(),
                    () -> assertThat(order.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
                    () -> assertThat(order.getLineItems()).hasSize(orderLineItems.size())
            );
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("주문 항목에 포함된 메뉴는 전시중인 상태여야 한다")
        @Test
        void menu_should_be_displayed() {
            // given
            OrderTableEntity orderTableEntity = createOrderTable(테이블_1_ORDER_TABLE_UUID, "테이블 1", 0, true);
            orderTableRepository.save(orderTableEntity);

            List<OrderLineItemRequest> orderLineItems = List.of(new OrderLineItemRequest(2, 후라이드치킨_NO_DISPLAY_MENU_UUID, 후라이드치킨_MENU_DEFAULT_PRICE));
            CreateEatInOrderRequest request = new CreateEatInOrderRequest(테이블_1_ORDER_TABLE_UUID, orderLineItems);

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.create(request);

            // then
            assertThatIllegalStateException()
                    .isThrownBy(throwingCallable);

        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("주문 항목은 1개 이상 포함되어야 한다")
        @Test
        void order_must_contain_at_least_one_item() {
            // given
            OrderTableEntity orderTableEntity = createOrderTable(테이블_1_ORDER_TABLE_UUID, "테이블 1", 0, true);
            orderTableRepository.save(orderTableEntity);
            CreateEatInOrderRequest request = new CreateEatInOrderRequest(테이블_1_ORDER_TABLE_UUID, List.of());

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.create(request);

            // then
            assertThatIllegalArgumentException()
                    .isThrownBy(throwingCallable);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("매장 식사 주문의 경우, 주문 테이블을 반드시 지정해야 한다")
        @Test
        void order_table_must_be_specified_for_eat_in_order() {
            // given
            List<OrderLineItemRequest> orderLineItems = List.of(new OrderLineItemRequest(2, 후라이드치킨_MENU_UUID, 후라이드치킨_MENU_DEFAULT_PRICE));
            CreateEatInOrderRequest request = new CreateEatInOrderRequest(null, orderLineItems);

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.create(request);

            // then
            assertThatThrownBy(throwingCallable)
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("매장 식사 주문의 경우, 테이블이 사용 중 상태여야 한다")
        @Test
        void table_must_be_occupied_for_eat_in_order() {
            // given
            OrderTableEntity orderTableEntity = createOrderTable(테이블_1_ORDER_TABLE_UUID, "테이블 1", 0, false);
            orderTableRepository.save(orderTableEntity);

            List<OrderLineItemRequest> orderLineItems = List.of(new OrderLineItemRequest(2, 후라이드치킨_MENU_UUID, 후라이드치킨_MENU_DEFAULT_PRICE));
            CreateEatInOrderRequest request = new CreateEatInOrderRequest(테이블_1_ORDER_TABLE_UUID, orderLineItems);

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.create(request);

            // then
            assertThatIllegalStateException()
                    .isThrownBy(throwingCallable);
        }
    }

    @DisplayName("주문 수락하기")
    @Nested
    class AcceptOrderTest {

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("매장 식사 주문을 정상적으로 수락한다")
        @Test
        void accept_eat_in_order_successfully() {
            // given
            EatInOrder order = createEeaInOrder();

            // when
            EatInOrder acceptedOrder = orderService.accept(order.getId());

            // then
            assertThat(acceptedOrder.getStatus()).isEqualTo(EatInOrderStatus.ACCEPTED);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("주문 상태가 '대기' 상태인 경우에만 수락할 수 있다")
        @Test
        void only_waiting_order_can_be_accepted() {
            // given
            EatInOrder order = createEeaInOrder();
            orderService.accept(order.getId());

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.accept(order.getId());

            // then
            assertThatIllegalStateException()
                    .isThrownBy(throwingCallable);
        }
    }

    @DisplayName("주문 제공하기")
    @Nested
    class ServeOrderTest {

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("매장 식사 주문을 정상적으로 제공한다")
        @Test
        void serve_eat_in_order_successfully() {
            // given
            EatInOrder order = createEeaInOrder();
            orderService.accept(order.getId());

            // when
            EatInOrder servedOrder = orderService.serve(order.getId());

            // then
            assertThat(servedOrder.getStatus()).isEqualTo(EatInOrderStatus.SERVED);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("주문 상태가 '수락됨' 상태인 경우에만 제공할 수 있다")
        @Test
        void only_accepted_order_can_be_served() {
            // given
            EatInOrder order = createEeaInOrder();

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.serve(order.getId());

            // then
            assertThatIllegalStateException()
                    .isThrownBy(throwingCallable);
        }
    }

    @DisplayName("주문 완료하기")
    @Nested
    class CompleteOrderTest {

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("매장 식사 주문을 정상적으로 완료한다")
        @Test
        void complete_eat_in_order_successfully() {
            // given
            EatInOrder order = createEeaInOrder();
            orderService.accept(order.getId());
            orderService.serve(order.getId());

            // when
            Order completedOrder = orderService.complete(order.getId());

            // then
            assertThat(completedOrder.getStatus()).isEqualTo(EatInOrderStatus.COMPLETED);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("주문 상태가 '제공됨' 상태인 경우에만 매장 식사 주문을 완료할 수 있다")
        @Test
        void only_served_order_can_be_completed_for_eat_in() {
            // given
            EatInOrder order = createEeaInOrder();

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> orderService.complete(order.getId());

            // then
            assertThatIllegalStateException()
                    .isThrownBy(throwingCallable);
        }
    }

    @DisplayName("주문 목록 조회하기")
    @Nested
    class FindAllOrdersTest {
        private static final int TOTAL_ORDER_COUNT = 2;

        @SqlGroup({
                @Sql(value = "/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        })
        @DisplayName("모든 주문을 조회한다")
        @Test
        void find_all_orders() {
            // when
            List<Order> orders = orderService.findAll();

            // then
            assertThat(orders).hasSize(TOTAL_ORDER_COUNT);
        }
    }

    private EatInOrder createEeaInOrder() {
        OrderTableEntity orderTableEntity = createOrderTable(테이블_1_ORDER_TABLE_UUID, "테이블 1", 0, true);
        orderTableRepository.save(orderTableEntity);

        List<OrderLineItemRequest> orderLineItems = List.of(new OrderLineItemRequest(2, 후라이드치킨_MENU_UUID, 후라이드치킨_MENU_DEFAULT_PRICE));
        CreateEatInOrderRequest request = new CreateEatInOrderRequest(테이블_1_ORDER_TABLE_UUID, orderLineItems);

        return orderService.create(request);
    }

    private static MenuProductEntity createMenuProduct(UUID productId, Product product, int quantity) {
        MenuProductEntity menuProduct = new MenuProductEntity();
        menuProduct.setProductId(productId);
        menuProduct.setProductPrice(product.getPrice());
        menuProduct.setProductId(product.getId());
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }

    private static Product createProduct(UUID id, String name, BigDecimal price) {
        ProductName productName = ProductName.of(name, nm -> false);
        ProductPrice productPrice = ProductPrice.of(price);
        return new Product(id, productName, productPrice);
    }

    private static MenuGroupEntity createMenuGroup(UUID id, String name) {
        MenuGroupEntity menuGroup = new MenuGroupEntity();
        menuGroup.setId(id);
        menuGroup.setName(name);
        return menuGroup;
    }

    private static MenuEntity createMenu(UUID id, String name, BigDecimal price, UUID menuGroupId, MenuGroupEntity menuGroup, List<MenuProductEntity> menuProducts) {
        MenuEntity menu = new MenuEntity();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuGroupId(menuGroupId);
        menu.setMenuGroup(menuGroup);
        menu.setMenuProducts(menuProducts);
        menu.setDisplayed(true);
        return menu;
    }

    private static OrderTableEntity createOrderTable(UUID id, String name, int numberOfGuests, boolean occupied) {
        OrderTableEntity orderTableEntity = new OrderTableEntity();
        orderTableEntity.setId(id);
        orderTableEntity.setOccupied(occupied);
        orderTableEntity.setNumberOfGuests(numberOfGuests);
        orderTableEntity.setName(name);
        return orderTableEntity;
    }

    private static final UUID 후라이드치킨_PRODUCT_UUID = UUID.randomUUID();
    private static final UUID 후라이드치킨_MENU_UUID = UUID.randomUUID();
    private static final UUID 후라이드치킨_NO_DISPLAY_MENU_UUID = UUID.randomUUID();
    private static final UUID 치킨류_MENU_GROUP_UUID = UUID.randomUUID();
    private static final UUID 테이블_1_ORDER_TABLE_UUID = UUID.randomUUID();
    private static final String 후라이드치킨_PRODUCT_NAME = "후라이드치킨";
    private static final String 치킨류_MENU_GROUP_NAME = "치킨류";
    private static final String 후라이드치킨_MENU_NAME = "후라이드치킨";
    private static final BigDecimal 후라이드치킨_DEFAULT_PRICE = new BigDecimal(20000);
    private static final BigDecimal 후라이드치킨_MENU_DEFAULT_PRICE = new BigDecimal(19000);
}