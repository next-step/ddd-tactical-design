package kitchenpos.products.application.tobe.application;


import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional
    public Product changePrice(final ProductId productId, final Product request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        final Price newPrice = request.getPrice();
        product.changePrice(newPrice);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
