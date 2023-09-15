package kitchenpos.eatinorders.ui.request;

import javax.validation.constraints.NotNull;

public class OrderTableCreateRequest {

    @NotNull
    private String name;

    public OrderTableCreateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
