package kitchenpos.order.eatinorder.application.dto;

public class CreateOrderTableServiceRq {
    private String name;

    public CreateOrderTableServiceRq(String name) {
        this.name = name;
    }

    public CreateOrderTableServiceRq() {
    }

    public String getName() {
        return name;
    }
}
