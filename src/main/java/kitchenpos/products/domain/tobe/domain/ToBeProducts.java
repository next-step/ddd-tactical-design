package kitchenpos.products.domain.tobe.domain;

import java.util.List;

public class ToBeProducts {
    private final List<ToBeProduct> products;

    public ToBeProducts(List<ToBeProduct> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("등록된 상품이 없습니다.");
        }
        this.products = products;
    }

}
