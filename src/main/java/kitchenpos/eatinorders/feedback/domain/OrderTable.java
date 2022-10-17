package kitchenpos.eatinorders.feedback.domain;

import kitchenpos.eatinorders.feedback.vo.DisplayedName;
import kitchenpos.eatinorders.feedback.vo.NumberOfGuest;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_table")
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DisplayedName name;

    @Embedded
    private NumberOfGuest numberOfGuest;

    private boolean occupied;

    protected OrderTable() {
    }

    public OrderTable(String name) {
        this(null, name);
    }

    public OrderTable(Long id, String name) {
        this(id, new DisplayedName(name), new NumberOfGuest(0));
    }

    public OrderTable(Long id, DisplayedName name, NumberOfGuest numberOfGuest) {
        this.id = id;
        this.name = name;
        this.numberOfGuest = numberOfGuest;
        this.occupied = false;
    }

    public void changeNumberOfGuest(int number) {
        if (!occupied) {
            throw new IllegalArgumentException("테이블이 비어있는 경우 손님 수를 변경할 수 없습니다.");
        }
        numberOfGuest = new NumberOfGuest(number);
    }

    public void use() {
        this.use(0);
    }

    public void use(int numberOfGuest) {
        this.numberOfGuest = new NumberOfGuest(numberOfGuest);
        this.occupied = true;
    }

    public void clear(OrderTableClearPolicy orderTableClearPolicy) {
        if (orderTableClearPolicy.canClear(id)) {
            this.numberOfGuest = new NumberOfGuest();
            this.occupied = false;
        }
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name.getName();
    }

    public int getNumberOfGuest() {
        return numberOfGuest.getNumberOfGuest();
    }
}
