package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.ProductInfo;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductDomainFetchService implements ProductDomainService {

    private final ProductRepository productRepository;

    public ProductDomainFetchService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductInfo fetchProductInfo(UUID productId) {
        return productRepository.findById(productId)
                .map(product -> ProductInfo.is(product.getId(), product.getPrice()))
                .orElseThrow(() -> new NoSuchElementException("상품이 존재하지 않습니다."));
    }
}
