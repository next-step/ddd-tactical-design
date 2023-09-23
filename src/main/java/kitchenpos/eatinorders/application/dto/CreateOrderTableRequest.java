package kitchenpos.eatinorders.application.dto;

public class CreateOrderTableRequest {
    private String name;

    public CreateOrderTableRequest() {
    }

    public CreateOrderTableRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
