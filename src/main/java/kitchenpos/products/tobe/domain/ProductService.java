package kitchenpos.products.tobe.domain;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        Products products = new Products(productRepository.findAll());
        return products.getProducts();
    }
}
