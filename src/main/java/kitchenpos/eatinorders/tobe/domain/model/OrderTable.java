package kitchenpos.eatinorders.tobe.domain.model;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * - 주문 테이블을 등록할 수 있다.
 * - 주문 테이블의 이름이 올바르지 않으면 등록할 수 없다.
 *   - 주문 테이블의 이름은 비워 둘 수 없다.
 * - 빈 테이블을 해지할 수 있다.
 * - 빈 테이블로 설정할 수 있다.
 * - 완료되지 않은 주문이 있는 주문 테이블은 빈 테이블로 설정할 수 없다.
 * - 방문한 손님 수를 변경할 수 있다.
 * - 방문한 손님 수가 올바르지 않으면 변경할 수 없다.
 *   - 방문한 손님 수는 0 이상이어야 한다.
 * - 빈 테이블은 방문한 손님 수를 변경할 수 없다.
 * - 주문 테이블의 목록을 조회할 수 있다.
 */
@Table(name = "order_table")
@Entity
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
        this.name = new OrderTableName(name);
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        this.empty = true;
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
            throw new IllegalArgumentException("주문 완료 상태인 경우에만 빈 테이블로 설정이 가능합니다.");
        }
        this.empty = true;
        this.numberOfGuests = NumberOfGuests.ZERO;
        return this;
    }

    public OrderTable changeNumberOfGuests(final int numberOfGuests) {
        if (isEmpty()) {
            throw new IllegalArgumentException("빈 테이블은 방문한 손님 수를 변경할 수 없습니다.");
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
        return this;
    }
}
