package kitchenpos.products.tobe.dto;

import kitchenpos.products.tobe.domain.Product;

import java.util.List;
import java.util.UUID;

public class ProductDto {
    private UUID id;
    private String name;
    private long price;

    public ProductDto() {
    }

    public ProductDto(UUID id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public static List<ProductDto> ofList(List<Product> products) {
        return products.stream().map(ProductDto::from).toList();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public static ProductDto from(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.id = product.getId();
        productDto.name = product.getDisplayName().getName();
        productDto.price = product.getPrice().getPrice().longValue();
        return productDto;
    }



}
