package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.commons.tobe.domain.model.DisplayedName;

import java.util.UUID;

public class OrderTable {

    public OrderTable(final UUID id, final DisplayedName displayedName, final NumberOfGuests numberOfGuests) {
    }

    public void sit() {
    }

    public void clear() {
    }

    public void changeNumberOfGuests(final NumberOfGuests numberOfGuests) {
    }

    public UUID getId() {
        return null;
    }

    public String getName() {
        return null;
    }

    public long getNumberOfGuests() {
        return -1L;
    }

    public boolean isEmpty() {
        return false;
    }
}
