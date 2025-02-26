package kitchenpos.order.eatinorder.ui.dto;

public class CreateOrderTableRq {
    private String name;

    public CreateOrderTableRq(String name) {
        this.name = name;
    }

    public CreateOrderTableRq() {
    }

    public String getName() {
        return name;
    }
}
