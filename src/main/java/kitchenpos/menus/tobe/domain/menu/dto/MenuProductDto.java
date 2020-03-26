package kitchenpos.menus.tobe.domain.menu.dto;

import kitchenpos.menus.tobe.domain.menu.infra.MenuProductEntity;

public class MenuProductDto {
    private Long id;
    private Long productId;
    private Long quantity;

    public MenuProductDto(MenuProductEntity menuProductEntity) {
        this.id = menuProductEntity.getId();
        this.productId = menuProductEntity.getProductId();
        this.quantity = menuProductEntity.getQuantity();
    }

    public Long getId (){
        return id;
    }

    public Long getProductId() {
        return productId;
    }

    public Long getQuantity (){
        return quantity;
    }
}
