package kitchenpos.takeoutorder.application;

import static kitchenpos.Fixtures.INVALID_ID;
import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.menuProduct;
import static kitchenpos.Fixtures.takeoutOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import kitchenpos.menu.InMemoryMenuRepository;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.takeoutorder.InMemoryTakeoutOrderRepository;
import kitchenpos.takeoutorder.domain.TakeoutOrder;
import kitchenpos.takeoutorder.domain.TakeoutOrderLineItem;
import kitchenpos.takeoutorder.domain.TakeoutOrderRepository;
import kitchenpos.takeoutorder.domain.TakeoutOrderStatus;
import kitchenpos.takeoutorder.domain.TakeoutOrderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class TakeoutOrderServiceTest {
    private TakeoutOrderRepository takeoutOrderRepository;
    private MenuRepository menuRepository;
    private TakeoutOrderService takeoutOrderService;

    @BeforeEach
    void setUp() {
        takeoutOrderRepository = new InMemoryTakeoutOrderRepository();
        menuRepository = new InMemoryMenuRepository();
        takeoutOrderService = new TakeoutOrderService(takeoutOrderRepository, menuRepository);
    }

    @DisplayName("1개 이상의 등록된 메뉴로 포장 주문을 등록할 수 있다.")
    @Test
    void createTakeoutOrder() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, true, menuProduct())
        ).getId();
        final TakeoutOrder expected = createTakeoutOrder(
            createTakeoutOrderLineItem(menuId, 19_000L, 3L)
        );
        final TakeoutOrder actual = takeoutOrderService.create(expected);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getType()).isEqualTo(expected.getType()),
            () -> assertThat(actual.getStatus()).isEqualTo(TakeoutOrderStatus.WAITING),
            () -> assertThat(actual.getOrderDateTime()).isNotNull(),
            () -> assertThat(actual.getOrderLineItems()).hasSize(1)
        );
    }

    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    @MethodSource("takeoutOrderLineItems")
    @ParameterizedTest
    void create(final List<TakeoutOrderLineItem> takeoutOrderLineItems) {
        final TakeoutOrder expected = createTakeoutOrder(takeoutOrderLineItems);
        assertThatThrownBy(() -> takeoutOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Arguments> takeoutOrderLineItems() {
        return Arrays.asList(
            null,
            Arguments.of(Collections.emptyList()),
            Arguments.of(List.of(createTakeoutOrderLineItem(INVALID_ID, 19_000L, 3L)))
        );
    }

    @DisplayName("포장 주문 항목의 수량은 0 이상이어야 한다.")
    @ValueSource(longs = -1L)
    @ParameterizedTest
    void createWithoutTakeoutOrder(final long quantity) {
        final UUID menuId = menuRepository.save(
            menu(19_000L, true, menuProduct())
        ).getId();
        final TakeoutOrder expected = createTakeoutOrder(
            createTakeoutOrderLineItem(menuId, 19_000L, quantity)
        );
        assertThatThrownBy(() -> takeoutOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    @Test
    void createNotDisplayedMenuOrder() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, false, menuProduct())
        ).getId();
        final TakeoutOrder expected = createTakeoutOrder(
            createTakeoutOrderLineItem(menuId, 19_000L, 3L)
        );
        assertThatThrownBy(() -> takeoutOrderService.create(expected))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.")
    @Test
    void createNotMatchedMenuPriceOrder() {
        final UUID menuId = menuRepository.save(
            menu(19_000L, true, menuProduct())
        ).getId();
        final TakeoutOrder expected = createTakeoutOrder(
            createTakeoutOrderLineItem(menuId, 16_000L, 3L)
        );
        assertThatThrownBy(() -> takeoutOrderService.create(expected))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("포장 주문을 접수한다.")
    @Test
    void accept() {
        final UUID orderId = takeoutOrderRepository.save(takeoutOrder(TakeoutOrderStatus.WAITING))
            .getId();
        final TakeoutOrder actual = takeoutOrderService.accept(orderId);
        assertThat(actual.getStatus()).isEqualTo(TakeoutOrderStatus.ACCEPTED);
    }

    @DisplayName("접수 대기 중인 포장 주문만 접수할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "WAITING", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void accept(final TakeoutOrderStatus status) {
        final UUID orderId = takeoutOrderRepository.save(takeoutOrder(status)).getId();
        assertThatThrownBy(() -> takeoutOrderService.accept(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("포장 주문을 서빙한다.")
    @Test
    void serve() {
        final UUID orderId = takeoutOrderRepository.save(takeoutOrder(TakeoutOrderStatus.ACCEPTED))
            .getId();
        final TakeoutOrder actual = takeoutOrderService.serve(orderId);
        assertThat(actual.getStatus()).isEqualTo(TakeoutOrderStatus.SERVED);
    }

    @DisplayName("접수된 포장 주문만 서빙할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "ACCEPTED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void serve(final TakeoutOrderStatus status) {
        final UUID orderId = takeoutOrderRepository.save(takeoutOrder(status)).getId();
        assertThatThrownBy(() -> takeoutOrderService.serve(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("포장 주문을 완료한다.")
    @Test
    void complete() {
        final TakeoutOrder expected = takeoutOrderRepository.save(
            takeoutOrder(TakeoutOrderStatus.SERVED)
        );
        final TakeoutOrder actual = takeoutOrderService.complete(expected.getId());
        assertThat(actual.getStatus()).isEqualTo(TakeoutOrderStatus.COMPLETED);
    }

    @DisplayName("서빙된 포장 주문만 완료할 수 있다.")
    @EnumSource(value = TakeoutOrderStatus.class, names = "SERVED", mode = EnumSource.Mode.EXCLUDE)
    @ParameterizedTest
    void completeTakeoutAndTakeoutOrder(final TakeoutOrderStatus status) {
        final UUID orderId = takeoutOrderRepository.save(takeoutOrder(status)).getId();
        assertThatThrownBy(() -> takeoutOrderService.complete(orderId))
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("포장 주문의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        takeoutOrderRepository.save(takeoutOrder(TakeoutOrderStatus.SERVED));
        takeoutOrderRepository.save(takeoutOrder(TakeoutOrderStatus.COMPLETED));
        final List<TakeoutOrder> actual = takeoutOrderService.findAll();
        assertThat(actual).hasSize(2);
    }

    private TakeoutOrder createTakeoutOrder(final TakeoutOrderLineItem... takeoutOrderLineItems) {
        return createTakeoutOrder(Arrays.asList(takeoutOrderLineItems));
    }

    private TakeoutOrder createTakeoutOrder(
        final List<TakeoutOrderLineItem> takeoutOrderLineItems
    ) {
        final TakeoutOrder takeoutOrder = new TakeoutOrder();
        takeoutOrder.setType(TakeoutOrderType.TAKEOUT);
        takeoutOrder.setOrderLineItems(takeoutOrderLineItems);
        return takeoutOrder;
    }

    private static TakeoutOrderLineItem createTakeoutOrderLineItem(
        final UUID menuId,
        final long price,
        final long quantity
    ) {
        final TakeoutOrderLineItem takeoutOrderLineItem = new TakeoutOrderLineItem();
        takeoutOrderLineItem.setSeq(new Random().nextLong());
        takeoutOrderLineItem.setMenuId(menuId);
        takeoutOrderLineItem.setPrice(BigDecimal.valueOf(price));
        takeoutOrderLineItem.setQuantity(quantity);
        return takeoutOrderLineItem;
    }
}
