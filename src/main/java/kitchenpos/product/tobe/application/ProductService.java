package kitchenpos.product.tobe.application;

import kitchenpos.menu.domain.MenuRepository;

import kitchenpos.common.tobe.Profanities;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;

import kitchenpos.product.tobe.domain.ProductValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final Profanities profanities;
    private final ProductValidator productValidator;

    public ProductService(
            final ProductRepository productRepository,
            final Profanities profanities,
            final ProductValidator productValidator
    ) {
        this.productRepository = productRepository;
        this.profanities = profanities;
        this.productValidator = productValidator;
    }

    @Transactional
    public Product create(final Product request) {
        return productRepository.save(request);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.updatePrice(request.getPrice(), productValidator);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
