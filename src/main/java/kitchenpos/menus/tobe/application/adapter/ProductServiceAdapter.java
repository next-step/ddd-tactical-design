package kitchenpos.menus.tobe.application.adapter;

import java.util.List;
import java.util.UUID;

import kitchenpos.products.tobe.domain.Product;

public interface ProductServiceAdapter {
    List<Product> findAllByIdIn(List<UUID> ids);
}