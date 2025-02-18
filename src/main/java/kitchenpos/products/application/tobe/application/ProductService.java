package kitchenpos.products.application.tobe.application;


import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(final Product request) {
        final Price price = request.getPrice();
        final ProductName name = request.getName();

        final Product product = new Product(
                ProductId.generate(),
                name,
                price
        );

        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
