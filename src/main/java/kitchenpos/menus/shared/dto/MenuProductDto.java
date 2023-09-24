package kitchenpos.menus.shared.dto;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.products.shared.dto.response.ProductDto;

import java.math.BigDecimal;
import java.util.UUID;

public class MenuProductDto {

    private Long seq;
    private UUID productId;

    private ProductDto product;
    private long quantity;

    private BigDecimal menuProductPrice;

    public MenuProductDto(UUID productId, long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    protected MenuProductDto() {
    }
    public Long getSeq() {
        return seq;
    }

    public UUID getProductId() {
        return productId;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getMenuProductPrice() {
        return menuProductPrice;
    }

    public ProductDto getProduct() {
        return product;
    }


}
