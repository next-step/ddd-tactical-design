package kitchenpos.menus.infra;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kitchenpos.menus.application.ProductApiService;
import kitchenpos.menus.application.dto.ProductDto;
import kitchenpos.menus.domain.Price;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

@Service
public class DefaultProductApiService implements ProductApiService {
    private final ProductRepository productRepository;

    public DefaultProductApiService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDto> findAllByIdIn(List<UUID> productIds) {
        List<Product> products = productRepository.findAllByIdIn(productIds);
        return products.stream()
                .map(product -> new ProductDto(
                                product.getId(),
                                new Price(product.getPrice().longValue())
                        )
                )
                .collect(Collectors.toList());
    }
}
