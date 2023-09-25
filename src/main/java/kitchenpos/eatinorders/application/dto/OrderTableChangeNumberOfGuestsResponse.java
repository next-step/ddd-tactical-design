package kitchenpos.eatinorders.application.dto;

import java.util.UUID;

public class OrderTableChangeNumberOfGuestsResponse {
    private UUID id;
    private String name;
    private Integer changedNumberOfGuests;


    private OrderTableChangeNumberOfGuestsResponse(UUID id, String name, Integer changedNumberOfGuests) {
        this.id = id;
        this.name = name;
        this.changedNumberOfGuests = changedNumberOfGuests;
    }

    public static OrderTableChangeNumberOfGuestsResponse create(UUID id, String name, Integer changedNumberOfGuests) {
        return new OrderTableChangeNumberOfGuestsResponse(id, name, changedNumberOfGuests);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getChangedNumberOfGuests() {
        return changedNumberOfGuests;
    }
}
