package kitchenpos.menus.tobe.domain.menu.dto;

import kitchenpos.common.PositiveNumber;

public class MenuProductDto {
    private PositiveNumber menuId;
    private PositiveNumber productId;
    private PositiveNumber quantity;

    public Long getProductId() {
        return productId.valueOf();
    }

    public Long getQuantity (){
        return quantity.valueOf();
    }
}
