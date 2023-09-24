package kitchenpos.eatinorders.tobe.application.dto;

import java.util.Objects;

public class OrderTableRequest {

    private String name;

    private int numberOfGuests;

    public OrderTableRequest(String name, int numberOfGuests) {
        this.name = name;
        this.numberOfGuests = numberOfGuests;
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

    public void validate() {
        validateName();
        validateNumberOfGuests();
    }

    private void validateName() {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNumberOfGuests() {
        if (numberOfGuests < 0) {
            throw new IllegalArgumentException();
        }
    }
}
