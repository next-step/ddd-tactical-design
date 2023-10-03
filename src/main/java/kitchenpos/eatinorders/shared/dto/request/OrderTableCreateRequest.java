package kitchenpos.eatinorders.shared.dto.request;

public class OrderTableCreateRequest {
    private String name;

    public OrderTableCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
