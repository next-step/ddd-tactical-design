package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * - 1개 이상의 등록된 메뉴로 매장 주문을 등록할 수 있다.
 * - 메뉴가 없으면 등록할 수 없다.
 * - 매장 주문은 주문 항목의 수량이 0 미만일 수 있다.
 * - 빈 테이블에는 매장 주문을 등록할 수 없다.
 * - 숨겨진 메뉴는 주문할 수 없다.
 * - 주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야 한다.
 * - 주문을 접수한다.
 * - 접수 대기 중인 주문만 접수할 수 있다.
 * - 주문을 서빙한다.
 * - 접수된 주문만 서빙할 수 있다.
 * - 포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.
 * - 주문 테이블의 모든 매장 주문이 완료되면 빈 테이블로 설정한다.
 * - 완료되지 않은 매장 주문이 있는 주문 테이블은 빈 테이블로 설정하지 않는다.
 */
@Table(name = "eat_in_order")
@Entity
public class EatInOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private OrderLineItems orderLineItems;

    @Transient
    private UUID orderTableId;

    protected EatInOrder() {

    }

    public EatInOrder(
        final UUID id,
        final OrderStatus status,
        final LocalDateTime orderDateTime,
        final OrderLineItems orderLineItems,
        final UUID orderTableId
    ) {
        this.id = id;
        this.status = status;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = orderLineItems;
        this.orderTableId = orderTableId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public static EatInOrder create(
        final List<Menu> menus,
        final List<OrderLineItem> orderLineItemList,
        final OrderTable orderTable
    ) {
        validateOrderTable(orderTable);
        return new EatInOrder(
            UUID.randomUUID(),
            OrderStatus.WAITING,
            LocalDateTime.now(),
            new OrderLineItems(orderLineItemList, createMenuMap(menus)),
            orderTable.getId()
        );
    }

    private static void validateOrderTable(OrderTable orderTable) {
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }
    }

    private static Map<UUID, Menu> createMenuMap(final List<Menu> menus) {
        return menus.stream()
            .map(menu -> {
                validateDisplayedMenu(menu);
                return menu;
            })
            .collect(Collectors.toMap(
                Menu::getId,
                menu -> menu
            ));
    }

    private static void validateDisplayedMenu(final Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
    }

    public void accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.SERVED;
    }

    public void complete() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.COMPLETED;
    }
}
