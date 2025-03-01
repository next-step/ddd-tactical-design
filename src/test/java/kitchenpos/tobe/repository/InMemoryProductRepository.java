package kitchenpos.tobe.repository;

import kitchenpos.tobe.product.domain.Product;
import kitchenpos.tobe.product.domain.ProductId;
import kitchenpos.tobe.product.domain.ProductRepository;

import java.util.*;

public class InMemoryProductRepository implements ProductRepository {

    private final Map<ProductId, Product> products = new HashMap<>();

    @Override
    public Product save(Product product) {
        Product newProduct = new Product(product.getName(), product.getPrice());
        products.put(newProduct.getId(), newProduct);
        return newProduct;
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    @Override
    public List<Product> findAllByIdIn(List<ProductId> ids) {
        return products.values()
                .stream()
                .filter(product -> ids.contains(product.getId()))
                .toList();
    }
}
