package kitchenpos.menus.tobe.menu.domain;

import java.util.List;

public interface Products {
    public List<ProductPriceDto> getProductPricesByProductIds(final List<Long> productIds);
}
