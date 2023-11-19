package kitchenpos.order.tobe.eatinorder.application.dto;

public class CreateOrderTableRequest {

    private final String name;

    public CreateOrderTableRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
