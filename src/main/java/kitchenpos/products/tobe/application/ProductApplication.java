package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.model.ProductData;
import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.List;

public class ProductApplication {
    private final ProductRepository productRepository;

    public ProductApplication (final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product RegisterNewProduct(String name, BigDecimal price){
        Product product = new Product(name, price);

        ProductData productData = new ProductData();
        productData.setId(product.getId());
        productData.setName(product.getName());
        productData.setPrice(product.getPrice());

        productRepository.save(productData);

        return product;
    }

    public List<ProductData> productList() {
        return productRepository.findAll();
    }
}
