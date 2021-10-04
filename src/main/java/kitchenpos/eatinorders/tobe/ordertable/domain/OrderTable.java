package kitchenpos.eatinorders.tobe.ordertable.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class OrderTable {
    private static final int DEFAULT_GUEST_NUMBER = 0;
    private static final boolean DEFAULT_EMPTY = true;

    @Column(name = "id", columnDefinition = "varbinary(16)")
    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number_of_guests", nullable = false)
    private int numberOfGuests;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    protected OrderTable() {
    }

    public OrderTable(final String name) {
        this(UUID.randomUUID(), name);
    }

    public OrderTable(final UUID id, final String name) {
        this(id, name, DEFAULT_GUEST_NUMBER, DEFAULT_EMPTY);
    }

    private OrderTable(final UUID id, final String name, final int numberOfGuests, final boolean empty) {
        verify(name);
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    private void verify(final String name) {
        if(!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("이름은 비워둘 수 없습니다.");
        }
    }

    public UUID getId() {
        return id;
    }

    public void sit() {
        this.empty = false;
    }

    public void changeNumberOfGuests(final int numberOfGuests) {
        if(numberOfGuests < DEFAULT_GUEST_NUMBER) {
            throw new IllegalArgumentException("변경하려는 손님은 "+DEFAULT_GUEST_NUMBER+"명 이상이어야합니다.");
        }
        if(this.empty) {
            throw new IllegalStateException("빈 테이블에는 손님 수를 변경할 수 없습니다.");
        }
        this.numberOfGuests = numberOfGuests;
    }

    public void clear() {
        this.numberOfGuests = DEFAULT_GUEST_NUMBER;
        this.empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final OrderTable that = (OrderTable) o;
        return Objects.equals(id, that.id) || (numberOfGuests == that.numberOfGuests && empty == that.empty && Objects.equals(name, that.name));
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numberOfGuests, empty);
    }
}
