package kitchenpos.eatinorders.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "order_table")
@Entity(name = "tobeOrderTable")
public class OrderTable {
    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private NumberOfGuests numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    protected OrderTable() {
    }

    public OrderTable(final String name, final int numberOfGuests) {
        this.id = UUID.randomUUID();
        this.name = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = true;
    }

    public UUID getId() {
        return id;
    }

    public boolean isEmpty() {
        return empty;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public OrderTable sit() {
        this.empty = false;
        return this;
    }

    public OrderTable clear(final OrderStatus orderStatus) {
        if (orderStatus != OrderStatus.COMPLETED) {
            throw new IllegalStateException("주문 완료 상태인 경우에만 빈 테이블로 설정이 가능합니다.");
        }
        this.empty = true;
        this.numberOfGuests = NumberOfGuests.ZERO;
        return this;
    }

    public OrderTable changeNumberOfGuests(final int numberOfGuests) {
        if (isEmpty()) {
            throw new IllegalStateException("빈 테이블은 방문한 손님 수를 변경할 수 없습니다.");
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        return this;
    }
}
