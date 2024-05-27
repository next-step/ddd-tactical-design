package kitchenpos.products.tobe.domain;

import java.util.*;

public class InMemoryProductRepository {
    private final Map<UUID, Product> products = new HashMap<>();

    public Product save(final Product product) {
        products.put(product.getId(), product);
        return product;
    }

    public Optional<Product> findById(final UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public List<Product> findAllByIdIn(final List<UUID> ids) {
        return products.values()
                .stream()
                .filter(product -> ids.contains(product.getId()))
                .toList();
    }

    public void update(final Product newProduct) {
        Optional<Product> pd = this.findById(newProduct.getId());
        List<Product> pds = this.findAll();
        if(pd.isEmpty()){
            throw new NoSuchElementException("Product not found with id: " + newProduct.getId());
        }

        this.save(newProduct);
    }
}
