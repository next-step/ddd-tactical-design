package kitchenpos.menus.tobe.domain.menu.dto;

import kitchenpos.menus.tobe.domain.menu.infra.MenuProductEntity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProductDto that = (MenuProductDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(productId, that.productId) &&
            Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, quantity);
    }
}
