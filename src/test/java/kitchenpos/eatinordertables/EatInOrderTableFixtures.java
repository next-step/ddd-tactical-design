package kitchenpos.eatinordertables;

import kitchenpos.eatinordertables.domain.EatInOrderTable;

public class EatInOrderTableFixtures {

    public static EatInOrderTable eatInOrderTable() {
        return eatInOrderTable(false, 0);
    }

    public static EatInOrderTable eatInOrderTable(final boolean occupied, final int numberOfGuests) {
        return new EatInOrderTable("1번", numberOfGuests, occupied);
    }
}
