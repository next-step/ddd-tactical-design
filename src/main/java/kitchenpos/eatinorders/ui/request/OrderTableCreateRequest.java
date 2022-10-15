package kitchenpos.eatinorders.ui.request;

public class OrderTableCreateRequest {

    private String name;

    protected OrderTableCreateRequest() {
    }

    public OrderTableCreateRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
