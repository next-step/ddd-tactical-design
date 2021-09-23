package kitchenpos.menus.tobe.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class FilteredProductRequest {
    private List<UUID> productIds;

    protected FilteredProductRequest() {}

    public FilteredProductRequest(final List<UUID> productIds) {
        this.productIds = Collections.unmodifiableList(productIds);
    }

    public List<UUID> getProductIds() {
        return new ArrayList<>(productIds);
    }
}
