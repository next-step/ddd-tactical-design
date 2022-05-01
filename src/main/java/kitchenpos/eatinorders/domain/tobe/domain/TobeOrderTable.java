package kitchenpos.eatinorders.domain.tobe.domain;

import kitchenpos.eatinorders.domain.tobe.domain.vo.Guests;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableId;
import kitchenpos.eatinorders.domain.tobe.domain.vo.OrderTableName;
import kitchenpos.eatinorders.domain.tobe.domain.vo.TableEmptyStatus;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Table(name = "order_table")
@Entity
public class TobeOrderTable {

    @EmbeddedId
    private OrderTableId id;

    @Embedded
    private OrderTableName name;

    @Embedded
    private Guests guests;

    @Column(name = "empty", nullable = false)
    @Enumerated(EnumType.STRING)
    private TableEmptyStatus emptyStatus;

    private TobeOrderTable(OrderTableId id, OrderTableName name, Guests guests, TableEmptyStatus emptyStatus) {
        this.id = id;
        this.name = name;
        this.guests = guests;
        this.emptyStatus = emptyStatus;
    }

    protected TobeOrderTable() {

    }

    public static TobeOrderTable Of(OrderTableName name) {
        if (Objects.isNull(name)) {
            throw new IllegalArgumentException();
        }
        return new TobeOrderTable(new OrderTableId(UUID.randomUUID()), name, new Guests(0), TableEmptyStatus.EMPTY);
    }

    public static TobeOrderTable Of(OrderTableName name, Guests guests, TableEmptyStatus emptyStatus) {
        if (Objects.isNull(name) || Objects.isNull(guests) || Objects.isNull(emptyStatus)) {
            throw new IllegalArgumentException();
        }
        return new TobeOrderTable(new OrderTableId(UUID.randomUUID()), name, guests, emptyStatus);
    }

    public TobeOrderTable sit() {
        this.emptyStatus = TableEmptyStatus.OCCUPIED;
        return this;
    }

    //TODO: 도메인 서비스를 이용하여 벨리데이션하는거잊지말긔
    public TobeOrderTable clear() {
        this.guests = new Guests(0);
        this.emptyStatus = TableEmptyStatus.EMPTY;
        return this;
    }

    public TobeOrderTable changeNumberOfGuests(Guests numberOfGuests) {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        this.guests = numberOfGuests;
        return this;
    }

    public OrderTableId getId() {
        return id;
    }

    public OrderTableName getName() {
        return name;
    }

    public Guests getGuests() {
        return guests;
    }

    public int getNumberOfGuests() {
        return guests.getValue();
    }

    public boolean isEmpty() {
        return emptyStatus.isEmpty();
    }

    public TableEmptyStatus getEmptyStatus() {
        return emptyStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TobeOrderTable that = (TobeOrderTable) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
