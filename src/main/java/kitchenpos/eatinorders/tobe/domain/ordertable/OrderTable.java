package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.Objects;
import java.util.UUID;

public class OrderTable {
    private static final String INVALID_NAME = "잘못된 테이블 이름 입니다";
    private static final String IS_EMPTY_TABLE = "빈 테이블 입니다.";

    private final UUID id;
    private final String name;
    private NumberOfGuests numberOfGuests;
    private boolean empty;

    private OrderTable(String name, NumberOfGuests numberOfGuests, boolean empty) {
        validate(name);
        this.id = UUID.randomUUID();
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public static OrderTable empty(String name) {
        return new OrderTable(name, NumberOfGuests.EMPTY, true);
    }

    public static OrderTable of(String name, int numberOfGuests, boolean empty) {
        return new OrderTable(name, new NumberOfGuests(numberOfGuests), empty);
    }

    private void validate(String name) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException(INVALID_NAME);
        }
    }

    public void use() {
        this.empty = false;
    }

    public void empty() {
        this.empty = true;
    }

    public void changeNumberOfGuests(int numberOfGuests) {
        if (isEmpty()) {
            throw new IllegalStateException(IS_EMPTY_TABLE);
        }
        this.numberOfGuests = new NumberOfGuests(numberOfGuests);
    }

    public boolean isEmpty() {
        return empty;
    }

    public NumberOfGuests getNumberOfGuests() {
        return numberOfGuests;
    }

    public UUID getId() {
        return id;
    }
}
