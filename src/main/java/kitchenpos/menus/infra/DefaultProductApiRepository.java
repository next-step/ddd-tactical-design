package kitchenpos.menus.infra;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import kitchenpos.menus.domain.ProductApiRepository;
import kitchenpos.menus.domain.vo.ProductVo;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

@Service
public class DefaultProductApiRepository implements ProductApiRepository {
    private final ProductRepository productRepository;

    public DefaultProductApiRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductVo> findAllByIdIn(List<UUID> productIds) {
        List<Product> products = productRepository.findAllByIdIn(productIds);
        return products.stream()
                .map(product -> new ProductVo(
                                product.getId(),
                                product.getPrice().longValue()
                        )
                )
                .collect(Collectors.toList());
    }
}
