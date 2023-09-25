package kitchenpos.eatinorders.application.dto;

public class OrderTableRequest {
    private String name;

    public OrderTableRequest(String name) {
        this.name = name;
    }

    public static OrderTableRequest create(String name) {
        return new OrderTableRequest(name);
    }

    public String getName() {
        return name;
    }
}
