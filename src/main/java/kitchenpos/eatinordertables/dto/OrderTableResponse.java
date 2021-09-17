package kitchenpos.eatinordertables.dto;

import java.util.UUID;

public class OrderTableResponse {
    private final UUID id;
    private final String name;
    private final int numberOfGuests;
    private final boolean empty;

    public OrderTableResponse(final UUID id, final String name, final int numberOfGuests, final boolean empty) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

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
