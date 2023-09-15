package kitchenpos.eatinorders.domain.tobe.domain;

import static kitchenpos.eatinorders.domain.OrderStatus.*;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.domain.OrderType;

@Table(name = "orders_master")
@Entity
public class OrderMaster {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    protected OrderMaster() {
    }

    public OrderMaster(OrderType type) {
        if (!OrderType.isValid(type)) {
            throw new IllegalArgumentException("주문 유형이 올바르지 않습니다.");
        }
        this.id = UUID.randomUUID();
        this.type = type;
        this.status = initialOrderStatus();
        this.orderDateTime = LocalDateTime.now();
    }

    public void accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException("접수 대기 중인 주문만 접수할 수 있다.");
        }
        status = OrderStatus.ACCEPTED;
        //도메인서비스에서 배달호출
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException("접수된 주문만 서빙할 수 있다.");
        }
        status = OrderStatus.SERVED;
    }

    public void startDelivery() {
        if (type != OrderType.DELIVERY) {
            throw new IllegalStateException("배달 주문만 배달할 수 있다.");
        }
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException("서빙된 주문만 배달할 수 있다.");
        }
        status = OrderStatus.DELIVERING;
    }

    public void completeDelivery() {
        if (status != OrderStatus.DELIVERING) {
            throw new IllegalStateException("배달 중인 주문만 배달 완료할 수 있다.");
        }
        status = OrderStatus.DELIVERED;
    }

    public void complete() {
        validationOfDelivery();
        validationOfTakeOutAndEatIn();
        status = OrderStatus.COMPLETED;
        //매장테이블 클리어 호출
    }

    private void validationOfTakeOutAndEatIn() {
        if (type == OrderType.DELIVERY) {
            return;
        }
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException("포장 및 매장 주문의 경우 서빙된 주문만 완료할 수 있다.");
        }
    }

    private void validationOfDelivery() {
        if (type != OrderType.DELIVERY) {
            return;
        }
        if (status != OrderStatus.DELIVERED) {
            throw new IllegalStateException("배달 주문의 경우 배달 완료된 주문만 완료할 수 있다.");
        }
    }

    public boolean isSameStatus(OrderStatus orderStatus) {
        return status == orderStatus;
    }
}
