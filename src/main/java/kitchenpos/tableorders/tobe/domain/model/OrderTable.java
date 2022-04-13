package kitchenpos.tableorders.tobe.domain.model;

import kitchenpos.global.domain.vo.Name;

import java.util.UUID;

public class OrderTable {

    private UUID id;
    private Name name;
    private int numberOfGuests;
    private boolean empty;

    public OrderTable(Name name) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = 0;
        this.empty = true;
    }

    public void assign() {
        this.empty = false;
    }

    public void assign(int numberOfGuests) {
        assign();
        this.numberOfGuests = numberOfGuests;
    }

    public void clear() {
        this.numberOfGuests = 0;
        this.empty = true;
    }

    public boolean isEmpty() {
        return empty;
    }

    public UUID getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void changNumberOfGuest(int numberOfGuests) {
        verifyChangNumberOfGuest(numberOfGuests);
        this.numberOfGuests = numberOfGuests;
    }

    private void verifyChangNumberOfGuest(int numberOfGuests) {
        if (empty) {
            throw new IllegalArgumentException("먼저 테이블을 지정해야합니다.");
        }
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException("테이블 인원은 음수일 수 없습니다.");
        }
    }
}
