package kitchenpos.eatinorders;

import java.util.UUID;
import kitchenpos.eatinorders.domain.OrderTable;

public class OrderTableFixture {

    public static OrderTableCreateRequestBuilder createRequestBuilder() {
        return new OrderTableCreateRequestBuilder();
    }

    public static class OrderTableCreateRequestBuilder {

        private String name;

        public OrderTableCreateRequestBuilder name(final String name) {
            this.name = name;

            return this;
        }

        public OrderTable build() {
            OrderTable orderTable = new OrderTable();
            orderTable.setName(name);

            return orderTable;
        }
    }

    public static OrderTableUpdateRequestBuilder updateRequestBuilder() {
        return new OrderTableUpdateRequestBuilder();
    }

    public static class OrderTableUpdateRequestBuilder {

        private int numberOfGuests;

        public OrderTableUpdateRequestBuilder numberOfGuests(final int numberOfGuests) {
            this.numberOfGuests = numberOfGuests;

            return this;
        }

        public OrderTable build() {
            OrderTable orderTable = new OrderTable();
            orderTable.setNumberOfGuests(numberOfGuests);

            return orderTable;
        }
    }

    public static OrderTable orderTable(boolean occupied) {
        OrderTable orderTable = orderTable();
        orderTable.setOccupied(occupied);

        return orderTable;
    }
    public static OrderTable orderTable() {
        OrderTable orderTable = new OrderTable();
        orderTable.setId(UUID.randomUUID());
        orderTable.setName("1");
        orderTable.setOccupied(true);
        return orderTable;
    }
}
