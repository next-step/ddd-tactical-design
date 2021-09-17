package kitchenpos.eatinorders.tobe.dto;

import java.util.UUID;

public class OrderTableResponse {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean empty;

    protected OrderTableResponse() {}

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
