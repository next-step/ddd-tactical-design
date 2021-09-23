package kitchenpos.eatinorders.tobe.domain.ordertable;

import java.util.UUID;

public class OrderTable {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean empty;

    protected OrderTable() {}

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}
