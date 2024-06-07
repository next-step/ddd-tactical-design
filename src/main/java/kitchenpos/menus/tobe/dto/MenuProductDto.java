package kitchenpos.menus.tobe.dto;

import kitchenpos.menus.tobe.application.query.result.MenuQueryResult;
import kitchenpos.products.tobe.dto.ProductDto;

public class MenuProductDto {
    private Long quantity;
    private ProductDto productDto;

    public MenuProductDto(Long quantity, ProductDto productDto) {
        this.quantity = quantity;
        this.productDto = productDto;
    }

    public static MenuProductDto from(MenuQueryResult menuQueryResult) {
        return new MenuProductDto(
            menuQueryResult.getProductQuantity(),
            new ProductDto(menuQueryResult.getProductId(),
                menuQueryResult.getProductName(),
                menuQueryResult.getProductPrice())
        );
    }

    public Long getQuantity() {
        return quantity;
    }

    public ProductDto getProductDto() {
        return productDto;
    }
}
