package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class ProductApplication {
    private final ProductRepository productRepository;

    public ProductApplication (final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductData registerNewProduct(String name, BigDecimal price){
        Product product = new Product(name, price);

        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setPrice(product.getPrice());

        productRepository.save(productData);

        return productData;
    }

    public List<ProductData> productList() {
        return productRepository.findAll();
    }

    public Optional<ProductData> findByProductId(Long productId) {
        return productRepository.findById(productId);
    }
}
