package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService {
    private final ProductRepositoryImpl productRepository;

    public ProductService(ProductRepositoryImpl repository) {
        this.productRepository = repository;
    }


    public Product register(DisplayedName name, Price price) {
        Product product = new Product(name, price);
        product.register(this.productRepository);
        return product;
    }

    public void changeName(UUID productId, String name) throws Exception {
        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            // Handle the case when the product is not found
            throw new Exception("Product not found with id: " + productId);
        }

        Product product = optionalProduct.get();
        Product changedProduct = new Product(productId, new DisplayedName(name), product.getPrice());

        this.productRepository.update(changedProduct);
    }

    public List<Product> getList() {
        return this.productRepository.findAll();
    }

    public Optional<Product> findById(UUID id) {
        return this.productRepository.findById(id);
    }
}
