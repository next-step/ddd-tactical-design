package kitchenpos.order.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.fixture.MenuFixture;
import kitchenpos.menu.domain.model.MenuVo.MenuInfo;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.application.MenuContextProvider;
import kitchenpos.order.common.application.dto.OrderRequest.OrderLineItemCreate;
import kitchenpos.order.common.domain.entity.Order;
import kitchenpos.order.common.domain.entity.OrderStatus;
import kitchenpos.order.common.domain.entity.OrderType;
import kitchenpos.order.common.domain.exception.OrderHideMenuException;
import kitchenpos.order.common.domain.exception.OrderInvalidException;
import kitchenpos.order.common.domain.exception.OrderLineItemQtyException;
import kitchenpos.order.common.domain.exception.OrderMenuInvalidException;
import kitchenpos.order.common.domain.model.OrderVo;
import kitchenpos.order.common.domain.repository.OrderRepository;
import kitchenpos.order.common.domain.repository.OrderTableRepository;
import kitchenpos.order.common.domain.service.DefaultOrderService;
import kitchenpos.order.delivery.domain.entity.DeliveryOrder;
import kitchenpos.order.delivery.domain.repository.DeliveryOrderRepository;
import kitchenpos.order.delivery.domain.service.DefaultDeliveryService;
import kitchenpos.order.delivery.domain.service.DeliveryService;
import kitchenpos.order.domain.fixture.DeliveryOrderFixture;
import kitchenpos.order.domain.fixture.OrderFixture;
import kitchenpos.order.domain.fixture.OrderLineItemFixture;
import kitchenpos.order.domain.fixture.OrderTableFixture;
import kitchenpos.order.eatin.domain.entity.OrderTable;
import kitchenpos.order.eatin.domain.service.DefaultEatInService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderFacadeTest {
    @InjectMocks
    private DefaultDeliveryService defaultDeliveryService;
    @Mock
    private DeliveryService deliveryService;
    @Mock
    private DefaultEatInService eatinService;
    @InjectMocks
    private DefaultOrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DeliveryOrderRepository deliveryOrderRepository;
    @Mock
    private MenuRepository menuRepository;
    @Mock
    private OrderTableRepository orderTableRepository;
    @Mock
    private MenuContextProvider menuContextProvider;

    private Order order;
    private DeliveryOrder deliveryOrder;
    private OrderTable orderTable;
    private OrderVo.Create createVo;
    private Menu chickenMenu;

    @BeforeEach
    void setUp() {
        chickenMenu = MenuFixture.init().toEntity();
        order = OrderFixture.init().toEntity();
        deliveryOrder = DeliveryOrderFixture.init().toEntity();
        orderTable = OrderTableFixture.init().toEntity();
        createVo = OrderFixture.init().createVo();
    }

    @Nested
    @DisplayName("주문 조회")
    class 주문_조회 {

        @Test
        @DisplayName("성공 : 특정 조건 없이 상품의 모든 목록을 조회할 수 있다.")
        void 주문목록_조회() {
            mockFindAllByOrder(order);
            var result = orderService.findAll();

            assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertEquals(result.size(), 1)
            );
        }
    }

    @Nested
    @DisplayName("주문 등록")
    class 주문_등록 {

        @Test
        @DisplayName("성공")
        void 주문등록_성공() {
            createVo = OrderFixture.test(
                OrderType.DELIVERY,
                null,
                null,
                List.of(OrderLineItemFixture.test(chickenMenu.getMenuId().get(), UUID.randomUUID(), OrderType.DELIVERY, 1, null).create()),
                null,
                UUID.randomUUID()
            ).createVo();
            mockCreateOrder();

            var result = orderService.create(createVo);

            assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.type(), order.getType()),
                () -> assertEquals(result.status(), order.getStatus()),
                () -> assertEquals(result.orderDateTime(), order.getOrderDateTime())
            );

        }

        @ParameterizedTest
        @DisplayName("배달, 먹고가기, 포장(주문 유형)이 반드시 있어야 한다.")
        @NullSource
        void 주문유형_있는지_검사(final OrderType orderType) {
            assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> {
                    createVo = OrderFixture.test(
                        orderType,
                        OrderStatus.DELIVERED,
                        null,
                        null,
                        null,
                        OrderTableFixture.init().toEntity().getOrderTableId().get()
                    ).createVo();
                    orderService.create(createVo);
                });
        }

        @ParameterizedTest
        @DisplayName("주문 아이템이 반드시 있어야 한다.")
        @EmptySource
        void 주문아이템_있는지_검사(final List<OrderLineItemCreate> orderLineItems) {
            assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> {
                    createVo = OrderFixture.test(
                        OrderType.DELIVERY,
                        null,
                        null,
                        orderLineItems,
                        null,
                        UUID.randomUUID()
                    ).createVo();
                })
                .withMessage(ErrorCode.NOT_FOUND_ORDER_ITEM.toString());
        }

        @ParameterizedTest
        @DisplayName("주문 아이템의 수량은 0개 이상이어야 한다.")
        @ValueSource(ints = {-100, 0, 100})
        void 주문아이템_수량이_0개이상_인지_검사(final int qty) {
            if (qty < 0) {
                assertThatExceptionOfType(OrderLineItemQtyException.class)
                    .isThrownBy(() -> {
                        createVo = OrderFixture.test(
                            OrderType.DELIVERY,
                            null,
                            null,
                            List.of(OrderLineItemFixture.test(
                                chickenMenu.getMenuId().get(),
                                UUID.randomUUID(),
                                OrderType.DELIVERY,
                                qty,
                                null
                            ).create()),
                            null,
                            OrderTableFixture.init().toEntity().getOrderTableId().get()
                        ).createVo();

                        mockFindMenus(List.of(MenuInfo.fromEntity(chickenMenu)));
                        orderService.create(createVo);
                    });
            }
        }

        @Test
        @DisplayName("주문 아이템의 메뉴는 반드시 있어야 한다.")
        void 주문아이템의_메뉴가_존재하는지_검사() {
            assertThatExceptionOfType(OrderMenuInvalidException.class)
                .isThrownBy(() -> orderService.create(createVo));
        }

        @Test
        @DisplayName("메뉴가 노출된 상태여야 한다.")
        void 메뉴가_노출상태인지_검사() {
            createVo = OrderFixture.test(
                null,
                null,
                null,
                List.of(OrderLineItemFixture.test(chickenMenu.getMenuId().get(), null, null, 1, null).create()),
                null,
                OrderTableFixture.init().toEntity().getOrderTableId().get()
            ).createVo();

            chickenMenu.updateDisplayed(false);

            mockFindMenus(List.of(MenuInfo.fromEntity(chickenMenu)));

            assertThatExceptionOfType(OrderHideMenuException.class)
                .isThrownBy(() -> orderService.create(createVo))
                .withMessage(ErrorCode.ORDER_HIDE_MENU_NOT_ALLOWED.toString());
        }

        @Test
        @DisplayName("메뉴가격과 주문 아이템 가격이 다르면 안된다.")
        void 메뉴가격_주문아이템가격_비교() {
            chickenMenu = MenuFixture.test(null, BigDecimal.valueOf(500_000), null, true, null)
                .toEntity();

            createVo = OrderFixture.test(
                null,
                null,
                null,
                List.of(OrderLineItemFixture.test(chickenMenu.getMenuId().get(), null, null, 1, null).create()),
                null,
                OrderTableFixture.init().toEntity().getOrderTableId().get()
            ).createVo();

            assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderService.create(createVo));
        }

        @ParameterizedTest
        @DisplayName("배달 주문시 배달 주소가 반드시 있어야 한다.")
        @NullAndEmptySource
        void 배달일경우_배달지주소_여부검사(final String address) {
            createVo = OrderFixture.test(
                OrderType.DELIVERY,
                null,
                null,
                null,
                address,
                OrderTableFixture.init().toEntity().getOrderTableId().get()
            ).createVo();

            assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> orderService.create(createVo));
        }

        @Test
        @DisplayName("먹고가기(주문유형)의 경우 주문 테이블내역이 있어야 한다.")
        void 먹고가기일경우_주문테이블내역_여부검사() {
            createVo = OrderFixture.test(
                OrderType.EAT_IN,
                null,
                null,
                List.of(OrderLineItemFixture.test(chickenMenu.getMenuId().get(), UUID.randomUUID(), OrderType.EAT_IN, 1, null).create()),
                null,
                UUID.randomUUID()
            ).createVo();
            mockFindMenus(List.of(MenuInfo.fromEntity(chickenMenu)));
            mockCreateEatInOrderFail();

            assertThatExceptionOfType(NotFoundException.class)
                .isThrownBy(() -> orderService.create(createVo))
                .withMessage(ErrorCode.NOT_FOUND_ORDER_TABLE.toString());
        }
    }

    @Nested
    @DisplayName("주문 수락")
    class 주문_수락 {

        @Test
        @DisplayName("성공")
        void 주문수락_성공() {
            mockFindByOrder(order);
            assertThatCode(() -> {
                orderService.accept(order.getOrderId());
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("현 주문상태가 **대기**이어야 한다.")
        void 주문상태_대기인지_검사() {
            order = OrderFixture.test(
                null,
                OrderStatus.ACCEPTED,
                null,
                null,
                null,
                OrderTableFixture.init().toEntity().getOrderTableId().get()
            ).toEntity();
            mockFindByOrder(order);

            assertThatExceptionOfType(OrderInvalidException.class)
                .isThrownBy(() -> orderService.accept(order.getOrderId()))
                .withMessage(ErrorCode.ORDER_STATUS_IS_NOT_WAITING.toString());
        }

        @Test
        @DisplayName("배달 주문인 경우, 라이더에게 주문번호, 주문 아이템의 총 금액, 배달 주소를 전달해 배달 요청한다.")
        void 배달주문_라이더에게_배달정보_전달_후_배달요청() {
            mockFindByOrder(deliveryOrder);

            mockRequestDelivery();
            assertThatCode(() -> {
                orderService.accept(order.getOrderId());
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("서빙/준비 완료")
    class 서빙_준비_완료 {

        @Test
        @DisplayName("성공")
        void 주문수락_성공() {
            order.updateOrderStatus(OrderStatus.ACCEPTED);

            mockFindByOrder(order);

            assertThatCode(() -> {
                orderService.serve(order.getOrderId());
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("현 주문상태가 **수락**이어야 한다.")
        void 주문상태_수락인지_검사() {
            mockFindByOrder(order);
            assertThatExceptionOfType(OrderInvalidException.class)
                .isThrownBy(() -> orderService.serve(order.getOrderId()))
                .withMessage(ErrorCode.ORDER_STATUS_IS_NOT_ACCEPTED.toString());

        }
    }

    @Nested
    @DisplayName("배달 시작")
    class 배달_시작 {

        @Test
        @DisplayName("성공")
        void 배달시작_성공() {
            deliveryOrder = DeliveryOrderFixture.test(
                OrderType.DELIVERY,
                OrderStatus.SERVED,
                null,
                null
            ).toEntity();

            mockFindByDeliveryOrder(deliveryOrder);

            assertThatCode(() -> {
                defaultDeliveryService.startDelivery(order.getOrderId());
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("주문 유형이 **배달**이어야 한다.")
        void 주문유형_배달인지_검사() {
            deliveryOrder = DeliveryOrderFixture.test(
                OrderType.EAT_IN,
                OrderStatus.SERVED,
                null,
                null
            ).toEntity();
            mockFindByDeliveryOrder(deliveryOrder);

            assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> defaultDeliveryService.startDelivery(order.getOrderId()));
        }

        @Test
        @DisplayName("현 주문상태가 **서빙/준비 완료**이어야 한다.")
        void 주문상태_서빙완료인지_검사() {
            mockFindByDeliveryOrder(deliveryOrder);

            assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> defaultDeliveryService.startDelivery(order.getOrderId()));
        }
    }

    @Nested
    @DisplayName("배달 완료")
    class 배달_완료 {

        @Test
        @DisplayName("성공")
        void 배달완료_성공() {
            deliveryOrder = DeliveryOrderFixture.test(
                OrderType.DELIVERY,
                OrderStatus.DELIVERING,
                null,
                null
            ).toEntity();

            mockFindByDeliveryOrder(deliveryOrder);

            assertThatCode(() -> {
                defaultDeliveryService.completeDelivery(order.getOrderId());
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("현 주문상태가 **배달중**이어야 한다.")
        void 주문상태_배달중인지_검사() {
            mockFindByDeliveryOrder(deliveryOrder);
            assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> defaultDeliveryService.completeDelivery(order.getOrderId()));
        }
    }

    @Nested
    @DisplayName("주문 완료")
    class 주문_완료 {

        @Test
        @DisplayName("성공")
        void 주문완료_성공() {
            order = OrderFixture.test(
                OrderType.DELIVERY,
                OrderStatus.DELIVERED,
                null,
                null,
                null,
                OrderTableFixture.init().toEntity().getOrderTableId().get()
            ).toEntity();
            mockFindByOrder(order);

            assertThatCode(() -> {
                orderService.complete(order.getOrderId());
            }).doesNotThrowAnyException();
        }

        @Test
        @DisplayName("배달(주문유형)인데 배달완료(주문상태)가 아니면 안된다.")
        void 배달이면_배달완료인지_검사() {
            mockFindByOrder(order);

            assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> orderService.complete(order.getOrderId()));
        }

        @Test
        @DisplayName("포장, 먹고가기(주문유형)일 경우 서빙완료(주문상태)이어야 한다.")
        void 포장_먹고가기이면_서빙완료인지_검사() {
            order = OrderFixture.test(
                OrderType.TAKEOUT,
                OrderStatus.DELIVERED,
                null,
                null,
                null,
                OrderTableFixture.init().toEntity().getOrderTableId().get()
            ).toEntity();
            mockFindByOrder(order);

            assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> orderService.complete(order.getOrderId()));
        }

        @Test
        @DisplayName("먹고가기(주문유형)일 경우, 해당 주문을 완료 처리 하고 해당 테이블에 다른 진행 중인 주문이 없다면 테이블을 비우고 인원 수를 0명으로 설정한다.")
        void 먹고가기이면_주문완료처리하고_테이블_초기화처리() {
            orderTable = OrderTableFixture.init().toEntity();

            order = OrderFixture.test(
                OrderType.EAT_IN,
                OrderStatus.SERVED,
                null,
                null,
                null,
                orderTable.getOrderTableId().get()
            ).toEntity();

            mockFindByOrder(order);

            orderTable.clear();

            orderService.complete(order.getOrderId());

            assertAll(
                () -> assertEquals(orderTable.getNumberOfGuests().get(), 0),
                () -> assertFalse(orderTable.isOccupied())
            );
        }


    }

    private void mockFindAllByOrder(Order request) {
        when(orderRepository.findAll())
            .thenReturn(List.of(request));
    }

    private void mockFindByOrder(Order request) {
        when(orderRepository.findById(Mockito.any()))
            .thenReturn(Optional.of(request));
    }

    private void mockFindByDeliveryOrder(Order request) {

        when(deliveryOrderRepository.findById(Mockito.any()))
            .thenReturn(Optional.of((DeliveryOrder) request));
    }

    private void mockCreateEatInOrderFail() {
        when(eatinService.createEatInOrder(Mockito.any(), Mockito.any(), Mockito.any()))
            .thenThrow(new NotFoundException(ErrorCode.NOT_FOUND_ORDER_TABLE.toString()));

    }

    private void mockCreateDeliveryOrderSuccess() {
        when(deliveryService.createDeliveryOrder(Mockito.any(), Mockito.any(), Mockito.any()))
            .thenReturn(deliveryOrder);

    }

    private void mockCreateOrder() {
        mockFindMenus(List.of(MenuInfo.fromEntity(chickenMenu)));
        mockCreateDeliveryOrderSuccess();
        mockSaveOrder(order);
    }

    private void mockSaveOrder(Order request) {
        when(orderRepository.save(Mockito.any(Order.class))).thenReturn(request);
    }

    private void mockRequestDelivery() {
        doNothing().when(deliveryService).requestDelivery(Mockito.any(), Mockito.any());
    }

    private void mockFindMenus(List<MenuInfo> menuInfos) {
        when(menuContextProvider.findMenus(Mockito.anyList())).thenReturn(menuInfos);
    }
}
