package kitchenpos.menus.tobe.dto;

import java.util.List;
import java.util.UUID;

public class FilteredProductRequest {
    private List<UUID> productIds;

    protected FilteredProductRequest() {}

    public FilteredProductRequest(final List<UUID> productIds) {
        this.productIds = productIds;
    }

    public List<UUID> getProductIds() {
        return productIds;
    }
}
