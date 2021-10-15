package kitchenpos.eatinorders.tobe.ui;

import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;

import java.util.UUID;

public class OrderTableForm {
    private UUID id;
    private String name;
    private int numberOfGuests;
    private boolean empty;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public static OrderTableForm of(TobeOrderTable orderTable) {
        OrderTableForm orderTableForm = new OrderTableForm();
        orderTableForm.setId(orderTable.getId());
        orderTableForm.setName(orderTable.getName());
        orderTableForm.setEmpty(orderTable.isEmpty());
        orderTableForm.setNumberOfGuests(orderTable.getNumberOfGuests());
        return orderTableForm;
    }
}
