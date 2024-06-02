package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.domain.vo.Name;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductService {
    private final InMemoryProductRepository productRepository;

    public ProductService(InMemoryProductRepository repository) {
        this.productRepository = repository;
    }

    public void register(Product product) {
        product.validateProperty();
        this.productRepository.save(product);
    }

    public void changeName(UUID productId, String name) throws Exception {
        // pseudocode
        // this.nameValidator.execute(name);

        Optional<Product> optionalProduct = this.productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            // Handle the case when the product is not found
            throw new Exception("Product not found with id: " + productId);
        }

        Product product = optionalProduct.get();
        Product changedProduct = new Product(productId, new Name(name), product.getPrice());
        this.productRepository.update(changedProduct);
    }

    public List<Product> getList() {
        return this.productRepository.findAll();
    }

    public Optional<Product> findById(UUID id) {
        return this.productRepository.findById(id);
    }
}
