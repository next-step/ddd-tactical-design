package kitchenpos.products.application.dto;

public class CreateProductRequest {
    private String name;
    private Long price;

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }
}
