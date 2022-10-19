package kitchenpos.eatinorders.domain;

import static kitchenpos.eatinorders.domain.EatInOrderStatus.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.*;

@Table(name = "eat_in_order")
@Entity
public class EatInOrder {

    @Id
    @Column(
        name = "id",
        length = 16,
        unique = true,
        nullable = false
    )
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EatInOrderStatus status;

    @Column(name = "eat_in_order_date_time", nullable = false)
    private LocalDateTime eatInOrderDateTime;

    @Embedded
    private EatInOrderLineItems eatInOrderLineItems;

    @Column(name = "eat_in_order_table_id", length = 16, nullable = false, columnDefinition = "binary(16)")
    private UUID eatInOrderTableId;

    protected EatInOrder() {
    }

    public EatInOrder(
        EatInOrderLineItems eatInOrderLineItems,
        UUID eatInOrderTableId
    ) {
        this(UUID.randomUUID(), WAITING, LocalDateTime.now(), eatInOrderLineItems, eatInOrderTableId);
    }

    public EatInOrder(
        UUID id,
        EatInOrderStatus status,
        LocalDateTime eatInOrderDateTime,
        EatInOrderLineItems eatInOrderLineItems,
        UUID eatInOrderTableId
    ) {
        this.id = id;
        this.status = status;
        this.eatInOrderDateTime = eatInOrderDateTime;
        this.eatInOrderLineItems = eatInOrderLineItems;
        this.eatInOrderTableId = eatInOrderTableId;
    }

    public void accept() {
        if (this.status != WAITING) {
            throw new IllegalStateException("주문상태가 '대기중'이 아니라 '접수됨'으로 변경할 수 없습니다.");
        }
        this.status = ACCEPTED;
    }

    public void serve() {
        if (this.status != ACCEPTED) {
            throw new IllegalStateException("주문상태가 '접수됨'이 아니라 '서빙됨'으로 변경할 수 없습니다.");
        }
        this.status = SERVED;
    }

    public void complete() {
        if (this.status != SERVED) {
            throw new IllegalStateException("주문상태가 '서빙됨'이 아니라 '완료'로 변경할 수 없습니다.");
        }
        this.status = COMPLETED;
    }

    public UUID getId() {
        return id;
    }

    public EatInOrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getEatInOrderDateTime() {
        return eatInOrderDateTime;
    }

    public List<EatInOrderLineItem> getEatInOrderLineItemValues() {
        return eatInOrderLineItems.getValues();
    }

    public UUID getEatInOrderTableId() {
        return eatInOrderTableId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EatInOrder that = (EatInOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
