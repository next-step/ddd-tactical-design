package kitchenpos.eatinorders.tobe.domain.order;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import static java.util.Objects.isNull;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderDateTime;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.orderTable.vo.OrderTableId;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * > 주문
 * <p>
 * | 한글명      | 영문명            | 설명                                                                                                                                     |
 * |----------|----------------|----------------------------------------------------------------------------------------------------------------------------------------|
 * | 매장 주문    | EatInOrder           | 매장에서 식사하는 고객 대상. 손님들이 매장에서 먹을 수 있도록 조리된 음식을 가져다준다.
 * | 주문 테이블 id | OrderTableId | 매장에서 식사하는 고객의 테이블
 * | 주문 상태   | EatInOrderStatus     | 고객이 주문을 요청하면 해당 주문을 매장에서 접수하고 고객에게 음식을 제공하기까지의 단계를 표시한다. e.g. `주문 대기`(WAITING), `주문 접수`(ACCEPTED), `서빙 완료`(SERVED), `주문 완료`(COMPLETED) |
 * | 주문 대기   | waiting         | (주문 상태) 매장에 있는 고객이 음식을 주문했고, 사장님이 해당 주문을 확인하지 못한 상태                                                                                    |
 * | 주문 접수   | accepted        | (주문 상태) 사장님이 해당 주문을 확인한 뒤 승인한 상태                                                                                                       |
 * | 서빙 완료   | served          | (주문 상태) 고객이 주문한 음식이 나왔고, 해당 음식을 고객에게 제공한 상태                                                                                            |
 * | 주문 완료   | completed       | (주문 상태) 고객이 해당 음식을 받은 상태                                                                                                               |
 * | 주문이 접수된 날짜 | orderDateTime | 고객의 주문이 접수된 날짜
 * | 주문 항목   | EatInOrderLineItem   | 주문에 포함된 개별 메뉴의 수량과 가격을 나타내는 항목                                                                                                       |
 */
@Table(name = "orders")
@Entity
public class EatInOrder {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableId orderTableId;

    @Column(name = "eat_in_order_status", nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Embedded
    private EatInOrderDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems orderLineItems;

    protected EatInOrder() {
    }

    public EatInOrder(final UUID orderTableId,
                      final EatInOrderStatus status,
                      final LocalDateTime orderDateTime,
                      final EatInOrderLineItems orderLineItems) {
        validate(orderTableId, status, orderDateTime, orderLineItems);
        this.orderTableId = new OrderTableId(orderTableId);
        this.status = status;
        this.orderDateTime = new EatInOrderDateTime(orderDateTime);
        this.orderLineItems = orderLineItems;
    }

    private void validate(final UUID orderTableId,
                          final EatInOrderStatus status,
                          final LocalDateTime orderDateTime,
                          final EatInOrderLineItems orderLineItems) {
        if (isNull(orderTableId) || isNull(status) || isNull(orderDateTime) || isNull(orderLineItems)) {
            throw new IllegalArgumentException();
        }
    }
}
