package kitchenpos.products.application;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProfanityPolicy;
import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.application.dto.ProductResponse;
import kitchenpos.products.application.mapper.ProductMapper;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProfanityPolicy profanityPolicy;

    public ProductService(
            final ProductRepository productRepository,
            final ProfanityPolicy profanityPolicy) {
        this.productRepository = productRepository;
        this.profanityPolicy = profanityPolicy;
    }

    @Transactional
    public ProductResponse create(final ProductRequest request) {
        Product product = productRepository.save(ProductMapper.toEntity(request, profanityPolicy));
        return ProductMapper.toDto(product);
    }

    @Transactional
    public ProductResponse changePrice(final ProductId productId, BigDecimal price) {
        Product product = findById(productId);
        Price productPrice = new Price(price);

        product.changePrice(productPrice);
        productRepository.save(product);

        return ProductMapper.toDto(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        List<Product> products = productRepository.findAll();
        return ProductMapper.toDtos(products);
    }


    private Product findById(ProductId productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }


    @Transactional(readOnly = true)
    public ProductResponse findProductById(ProductId productId) {
        Product product = findById(productId);
        return ProductMapper.toDto(product);
    }
}
