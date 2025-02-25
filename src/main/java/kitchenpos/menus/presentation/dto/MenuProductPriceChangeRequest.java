package kitchenpos.menus.presentation.dto;

import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.event.ProductPriceChangedEvent;

public class MenuProductPriceChangeRequest {

    private ProductId id;

    private Price newPrice;

    public static MenuProductPriceChangeRequest from(ProductPriceChangedEvent event) {
        return new MenuProductPriceChangeRequest(event.getId(), event.getPrice());
    }

    public MenuProductPriceChangeRequest(ProductId id, Price newPrice) {
        this.id = id;
        this.newPrice = newPrice;
    }

    public ProductId getId() {
        return id;
    }

    public Price getPrice() {
        return newPrice;
    }
}
