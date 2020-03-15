package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductApplication {
    private final ProductRepository productRepository;

    public ProductApplication (final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductData registerNewProduct(String name, BigDecimal price){
        Product product = new Product(name, price);
        productRepository.save(product);
        ProductData productData = convertToProductData(product);

        return productData;
    }

    public static ProductData convertToProductData(Product product) {
        final ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setPrice(product.getPrice());
        return productData;
    }

    public List<ProductData> productList() {
        final List<ProductData> productDataList = productRepository
                .findAll()
                .stream()
                .map(product -> convertToProductData(product))
                .collect(Collectors.toList());
        return productDataList;
    }

    public Optional<ProductData> findByProductId(Long productId) {
        final ProductData productData = convertToProductData(productRepository
                .findById(productId).
                        get());
        return Optional.of(productData);
    }
}
