package kitchenpos.eatinorders.dto;

import kitchenpos.eatinorders.domain.tobe.domain.TobeOrderTable;
import kitchenpos.support.dto.DTO;

import java.util.UUID;

public class OrderTableResponse extends DTO {
    private final UUID id;
    private final String name;
    private final int numberOfGuests;
    private final boolean isEmpty;

    public OrderTableResponse(UUID id, String name, int numberOfGuests, boolean isEmpty) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.isEmpty = isEmpty;
    }

    public OrderTableResponse(TobeOrderTable entity) {
        this(
                entity.getId().getValue(),
                entity.getName().getValue(),
                entity.getNumberOfGuests(),
                entity.isEmpty()
        );
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
        return isEmpty;
    }
}
