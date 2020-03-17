package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.ProductPriceResponse;
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
    public List<ProductPriceResponse> findAllPrices(List<Long> ids) {
        return productRepository.findAllById(ids)
                .stream()
                .map(product -> new ProductPriceResponse(product.getId(), product.getPrice()))
                .collect(Collectors.toList());
    }
}
