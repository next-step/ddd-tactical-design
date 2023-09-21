package kitchenpos.products.tobe.dto;

public class ProductCreateRequestDto {
    private String name;
    private Long price;

    public ProductCreateRequestDto(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }
}
