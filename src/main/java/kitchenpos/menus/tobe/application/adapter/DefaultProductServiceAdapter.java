package kitchenpos.menus.tobe.application.adapter;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;

@Service
public class DefaultProductServiceAdapter implements ProductServiceAdapter {
    private final ProductService productService;

    public DefaultProductServiceAdapter(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> ids) {
        return productService.findAllByIdIn(ids);
    }
}
