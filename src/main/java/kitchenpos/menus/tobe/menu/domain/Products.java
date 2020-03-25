package kitchenpos.menus.tobe.menu.domain;

import kitchenpos.products.tobe.domain.Product;

import java.util.List;

public interface Products {
    List<Product> getProductsByProductIds(final List<Long> productIds);
}
