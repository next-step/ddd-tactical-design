package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;
import java.util.UUID;

public class OrderTable {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean occupied;

    public OrderTable(final UUID id, final String name, final int numberOfGuests, final boolean occupied) {
        if(Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException();
        }
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }
}
