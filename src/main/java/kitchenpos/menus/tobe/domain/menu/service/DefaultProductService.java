package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.domain.MenuProducts;
import kitchenpos.products.tobe.domain.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultProductService implements ProductService {

    private final ProductRepository productRepository;

    public DefaultProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public MenuProducts findAllPrices(List<Long> ids) {
        return new MenuProducts(productRepository.findAllById(ids)
                .stream()
                .map(product -> new MenuProduct(product.getId(), product.getPrice()))
                .collect(Collectors.toList()));
    }
}
