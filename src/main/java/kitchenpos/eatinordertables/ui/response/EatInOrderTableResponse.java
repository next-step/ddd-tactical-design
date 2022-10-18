package kitchenpos.eatinordertables.ui.response;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.UUID;
import kitchenpos.eatinordertables.domain.EatInOrderTable;

public class EatInOrderTableResponse {

    private final UUID id;
    private final String name;
    private final int numberOfGuests;
    private final boolean occupied;

    public EatInOrderTableResponse(UUID id, String name, int numberOfGuests, boolean occupied) {
        this.id = id;
        this.name = name;
        this.numberOfGuests = numberOfGuests;
        this.occupied = occupied;
    }

    public static EatInOrderTableResponse from(EatInOrderTable eatInOrderTable) {
        return new EatInOrderTableResponse(
            eatInOrderTable.getId(),
            eatInOrderTable.getNameValue(),
            eatInOrderTable.getNumberOfGuestsValue(),
            eatInOrderTable.isOccupied()
        );
    }

    public static List<EatInOrderTableResponse> of(List<EatInOrderTable> eatInOrderTables) {
        return eatInOrderTables.stream()
            .map(EatInOrderTableResponse::from)
            .collect(toList());
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

    public boolean isOccupied() {
        return occupied;
    }
}
