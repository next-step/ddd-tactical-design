package kitchenpos.products.todo.application;

import kitchenpos.products.todo.domain.JpaProductRepository;
import kitchenpos.products.todo.domain.Product;
import kitchenpos.products.todo.domain.ProductRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final JpaProductRepository repository;

    public ProductService(final JpaProductRepository repository) {
        this.repository = repository;
    }

    public Product create(final ProductRequest request) {
        return repository.save(new Product(request));
    }

    public Product create(final Product product) {
        return repository.save(product);
    }

    public List<Product> list() {
        return repository.findAll();
    }
}
